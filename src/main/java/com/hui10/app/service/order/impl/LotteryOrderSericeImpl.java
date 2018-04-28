package com.hui10.app.service.order.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.encrypt.AesUtils;
import com.hui10.app.common.lottery.DoubleColorBall;
import com.hui10.app.common.lottery.LotteryIwtUtil;
import com.hui10.app.common.lottery.Md5Util;
import com.hui10.app.common.lottery.NumberUtils;
import com.hui10.app.common.lottery.dto.LotterySaleDto;
import com.hui10.app.common.utils.BetUtil;
import com.hui10.app.common.utils.FtpUtil;
import com.hui10.app.dao.merchant.MerchantInfoDao;
import com.hui10.app.dao.order.LotteryOrderDao;
import com.hui10.app.dao.order.OrderQueryDao;
import com.hui10.app.dao.order.TicketDao;
import com.hui10.app.model.enums.MessageTypeEnum;
import com.hui10.app.model.enums.PosLotOrderStatusEnum;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.order.LotteryOrderDetail;
import com.hui10.app.model.order.Ticket;
import com.hui10.app.model.order.enums.LotteryBetTypeEnum;
import com.hui10.app.model.order.enums.OrderStatusEnum;
import com.hui10.app.model.order.enums.OrderTypeEnum;
import com.hui10.app.model.order.enums.SourceEnum;
import com.hui10.app.model.user.UserInfo;
import com.hui10.app.service.message.MessageService;
import com.hui10.app.service.order.GivingRecordNotifyService;
import com.hui10.app.service.order.LotteryOrderSerice;
import com.hui10.app.service.order.OrderAsyncService;
import com.hui10.app.service.user.UserInfoService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;
import com.poslot.model.lottery.BetFactory;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-17 17:47
 **/
@Service
public class LotteryOrderSericeImpl implements LotteryOrderSerice {

    @Autowired
    private LotteryOrderDao lotteryOrderDao;
    @Autowired
    private OrderQueryDao orderQueryDao;

    @Autowired
    private LotteryIwtUtil lotteryIwtUtil;

    @Autowired
    private OrderAsyncService orderAsyncService;

    @Autowired
    private MerchantInfoDao merchantInfoDao;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private GivingRecordNotifyService givingRecordNotifyService;

    @Autowired
    private TicketDao ticketDao;
    
    @Autowired
    private MessageService messageService;

    /**
     * 固定投注站
     */
    private static String STATION_NO = PropertiesUtils.get("STATION_NO");
    private static String STATION_PROVINCE = PropertiesUtils.get("STATION_PROVINCE");

    /**
     * 加密算子
     */
    private static String UNIONPAY_MD5_PREFIX = PropertiesUtils.get("UNIONPAY_MD5_PREFIX");
    private static String UNIONPAY_MD5_SUFFIX = PropertiesUtils.get("UNIONPAY_MD5_SUFFIX");
    /**
     * 订单最大金额
     */
    private static int LOTTERY_BIG_AMOUNT = Integer.parseInt(PropertiesUtils.get("LOTTERY_BIG_AMOUNT"));

    private static int LOTTERY_BIG_MULTIPLE = Integer.parseInt(PropertiesUtils.get("LOTTERY_BIG_MULTIPLE"));


    private static final String PROMOTION_INFO_PRE = PropertiesUtils.get("PROMOTION_INFO_PRE");
    private static final String PROMOTION_INFO_NEXT = PropertiesUtils.get("PROMOTION_INFO_NEXT");


    private Logger logger = LoggerFactory.getLogger(getClass());
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public Map<String, Object> pickLotteryOrder(String authorization, String paramsBody) {
        //1、校验正确性
        check(authorization, paramsBody);

        //2、解析响应体
        Map map = JSON.parseObject(paramsBody, Map.class);
        checkMap(map);
        String merchantno = map.get("merchantno").toString();
        String spid = map.get("spid").toString();
        String serialno = map.get("serialno").toString();
        String acquirerno = map.get("acquirerno").toString();
        String userphone = map.get("userphone").toString();
        String gamecode = map.get("gamecode").toString();
        int lotterybetsize = Integer.parseInt(map.get("lotterybetsize").toString());

        if (StringUtils.isBlank(userphone)) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "userphone"));
        }

        if (StringUtils.isBlank(gamecode)) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "gamecode"));
        }


        Map<String, String> resultStationMap = getStationMsg(merchantno);
        String stationno = resultStationMap.get("stationno");
        String stationprovince = resultStationMap.get("stationprovince");
        String channelmercid = resultStationMap.get("channelmercid");

        LotterySaleDto lotterySaleDto = lotteryIwtUtil.getLotterySaleDto(stationno, stationprovince, 1, gamecode);
        int orderamount = 0;
        String orderid = UUIDUtil.getUUID();
        LotteryOrder lotteryOrder = new LotteryOrder();
        lotteryOrder.setOrderid(orderid);
        lotteryOrder.setStationno(stationno);
        lotteryOrder.setStationprovince(stationprovince);
        lotteryOrder.setMerchantno(merchantno);
        lotteryOrder.setSerialno(serialno);
        lotteryOrder.setAcquirerno(acquirerno);
        lotteryOrder.setChannelmercid(channelmercid);
        lotteryOrder.setIssuenumber(lotterySaleDto.getIssuenumber());
        lotteryOrder.setGamecode(gamecode);
        lotteryOrder.setSpid(spid);
        lotteryOrder.setCreatedate(new Date());
        lotteryOrder.setSource(SourceEnum.POS.getState());
        lotteryOrder.setLotterytime(lotterySaleDto.getLotteryendtime());
        lotteryOrder.setUserphone(userphone);

        List<Map<String, Object>> codeDetailList = new ArrayList<>(lotterybetsize);

        int betNumber = 0;// 总注数

        for (int i = 0; i < lotterybetsize; i++) {
            int multiple = 1;
            //暂时只有双色球
            int amount = 200 * multiple;
            orderamount += amount;
            betNumber = betNumber + multiple;
            //随机选号码
            String codedetail = randomCode();

            String[] arrayCode = codedetail.split("\\|");
            Map<String, Object> codeMap = new HashMap<>();
            codeMap.put("lotterycode", gamecode);
            codeMap.put("bettype", LotteryBetTypeEnum.SINGLE.getState());
            codeMap.put("multiple", 1);
            codeMap.put("red", arrayCode[0]);
            codeMap.put("blue", arrayCode[1]);
            codeDetailList.add(codeMap);
        }


        if (orderamount > LOTTERY_BIG_AMOUNT) {
            throw new CommonException(ICommon.LOTTERY_BIG_AMOUNT_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_BIG_AMOUNT_ERROR), orderamount / 100));
        }

        lotteryOrder.setOrderamount(orderamount);
        lotteryOrder.setBetnumber(betNumber);
        lotteryOrder.setRemark(JSON.toJSONString(codeDetailList));
        lotteryOrder.setOrdertype(OrderTypeEnum.ORDINARY.getCode());

        /**
         * 插入订单
         */
        lotteryOrderDao.insert(lotteryOrder);

        /**
         * 异步通知 2017-12-11
         */
        orderAsyncService.userNotify(userphone, orderid);

        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("orderid", orderid);
        result.put("orderamount", orderamount);
        result.put("spid", Constants.HUI10_SPID);
        result.put("tipsInfo1", PropertiesUtils.get("TIPSINFO1"));
		result.put("tipsInfo2", PropertiesUtils.get("TIPSINFO2"));
        return result;
    }

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    public Map<String, Object> betOrder(String authorization, String paramsBody) {
        //1、校验正确性
        check(authorization, paramsBody);
        //2、解析响应体
        Map map = JSON.parseObject(paramsBody, Map.class);
        checkMap(map);

        String merchantno = map.get("merchantno").toString();
        String orderid = map.get("orderid").toString();
        String outtradeno = map.get("outtradeno").toString();

        if (StringUtils.isBlank(orderid)) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "orderid"));
        }


        if (StringUtils.isBlank(outtradeno)) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "outtradeno"));
        }
        String spid = map.get("spid").toString();
        Map<String, String> resultStationMap = getStationMsg(merchantno);
        String stationno = resultStationMap.get("stationno");
        String stationprovince = resultStationMap.get("stationprovince");
        String channelmercid = resultStationMap.get("channelmercid");
        String serialno = map.get("serialno").toString();
        if (map.get("paytime") == null) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "outtradeno"));
        }
        String paytime = map.get("paytime").toString();
        int payAmount = Integer.parseInt(map.get("payamount").toString());

        LotteryOrder lotteryOrder = lotteryOrderDao.queryByOrderId(orderid);


        if (null == lotteryOrder) {
            throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
        }

        //2017-12-11 只验证POS生成的订单
        if (SourceEnum.POS.getState() == lotteryOrder.getSource()) {
            try {
                //用户注册校验获取uid  2017-12-05
                userInfoService.betOrderGetUserUidByPhone(lotteryOrder.getUserphone(), null);
            } catch (Exception e) {
                logger.error("调用注册或者用户绑定订单出错:{}", e.getMessage());
                throw new CommonException(900, "请求出票失败");
            }
        }

        if (OrderStatusEnum.NOPAY.getState() != lotteryOrder.getStatus()) {
            throw new CommonException(ICommon.ORDER_ALREADY_PROCESSED, PropertiesUtils.get(ICommon.ORDER_ALREADY_PROCESSED));
        }

        if (payAmount != lotteryOrder.getOrderamount()) {
            throw new CommonException(ICommon.ORDER_AMOUNT_ERROR, PropertiesUtils.get(ICommon.ORDER_AMOUNT_ERROR));
        }
        if (!merchantno.equals(lotteryOrder.getMerchantno())
                || !spid.equals(lotteryOrder.getSpid())
                || !stationno.equals(lotteryOrder.getStationno())
                || !stationprovince.equals(lotteryOrder.getStationprovince())
                ) {
            throw new CommonException(ICommon.ORDER_NOT_ALLOW, PropertiesUtils.get(ICommon.ORDER_NOT_ALLOW));
        }

        //判断当前彩票是否期结
        Date now = new Date();
        if (lotteryOrder.getLotterytime().getTime() < now.getTime()) {
            throw new CommonException(ICommon.LOTTERY_SALE_END, String.format(lotteryOrder.getIssuenumber(), PropertiesUtils.get(ICommon.LOTTERY_SALE_END)));
        }


        Map<String, Object> result = null;
        try {

            result = lotteryIwtUtil.betOrder(stationno, lotteryOrder.getUserphone(), orderid, stationprovince, lotteryOrder.getGamecode(), lotteryOrder.getIssuenumber(), lotteryOrder.getRemark());

        } catch (Exception e) {

            logger.error("投注订单出错:{}", e.getMessage());
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
            TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
            try {
                //逻辑代码，可以写上你的逻辑处理代码
                LotteryOrder order = new LotteryOrder();
                order.setOrderid(lotteryOrder.getOrderid());
                order.setUpdatedate(new Date());
                order.setOuttradeno(outtradeno);
                order.setStatus(OrderStatusEnum.FAILBILL.getState());
                order.setPaytime(new Date(Long.parseLong(paytime)));
                /***
                 * 更新
                 */
                lotteryOrderDao.update(order);
                transactionManager.commit(status);
            } catch (Exception e1) {
                transactionManager.rollback(status);
            }
            throw new CommonException(900, "请求出票失败");
        }

        List<Ticket> ticketList = (List<Ticket>) result.get("ticketList");

        int amount = Integer.parseInt(result.get("orderAmount").toString());
        if (lotteryOrder.getOrderamount() != amount) {
            logger.error("订单金额异常 订单号{}, 订单金额：{}，实际金额 ：{}", orderid, lotteryOrder.getOrderamount(), amount);
        }

        Integer orderStatus = Integer.parseInt(result.get("orderStatus").toString());
        LotteryOrder order = new LotteryOrder();
        order.setOrderid(lotteryOrder.getOrderid());
        String orderNo = result.get("orderNo").toString();
        order.setOrderno(orderNo);
        order.setActualamount(amount);
        order.setUpdatedate(new Date());
        order.setOuttradeno(outtradeno);
        order.setBetnumber(Integer.parseInt(result.get("betNumber").toString()));
        Date orderTime = new Date(Long.parseLong(result.get("orderTime").toString()));
        order.setOrdertime(orderTime);
        order.setStatus(orderStatus);
        order.setPaytime(new Date(Long.parseLong(paytime)));

        boolean isAllTicketSuccess = true; // 是否全部出票成功
        List<String> array = new ArrayList<>();
        int size = ticketList.size();

        if (null != orderStatus && orderStatus == OrderStatusEnum.FAILBILL.getState()) {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
            TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
            try {
                /***
                 * 更新订单信息
                 */
                lotteryOrderDao.update(order);

                for (int i = 0; i < size; i++) {
                    Ticket ticket = ticketList.get(i);
                    ticket.setOrderno(lotteryOrder.getOrderid());
                    ticket.setBuid(lotteryOrder.getUid());
                }

                /**
                 * 保存出票信息
                 */
                ticketDao.batchInsertTicket(ticketList);
                transactionManager.commit(status);
            } catch (Exception e1) {
                logger.error("commit error {}", e1.getMessage());
                transactionManager.rollback(status);
            }
            throw new CommonException(900, "请求出票失败");
        } else {
            /**
             * pos机小票显示注码
             */
            for (int i = 0; i < size; i++) {
                Ticket ticket = ticketList.get(i);
                ticket.setOrderno(lotteryOrder.getOrderid());
                ticket.setBuid(lotteryOrder.getUid());

                if (PosLotOrderStatusEnum.ALLTICKET.getValue() != ticket.getStatus()) {
                    isAllTicketSuccess = false;
                }

                array.addAll(getCodeDetail(ticket));
            }

            size = array.size();

            /***
             * 更新订单信息
             */
            lotteryOrderDao.update(order);
            /**
             * 保存出票信息
             */
            ticketDao.batchInsertTicket(ticketList);
        }

        /**
         * 全部出票成功赠送彩票
         */
        if (isAllTicketSuccess) {
        		givingRecordNotifyService.notifyGivingRecord(lotteryOrder);
        }

        copyProperty(lotteryOrder, order);
        orderAsyncService.uplNotify(lotteryOrder);

        Map<String, Object> resultMap = new HashMap<>(8);
        resultMap.put("orderid", orderid);
        resultMap.put("codedetails", array);
        resultMap.put("orderamount", lotteryOrder.getOrderamount());
        resultMap.put("issuenumber", lotteryOrder.getIssuenumber());
        resultMap.put("gamecode", lotteryOrder.getGamecode());
        resultMap.put("ordertime", orderTime.getTime());
        resultMap.put("lotteryopentime", sdf.format(lotteryOrder.getLotterytime()));
        resultMap.put("lotterybetsize", size);
        resultMap.put("spid", Constants.HUI10_SPID);
        resultMap.put("userphone", changePhone(lotteryOrder.getUserphone()));
        //增加二维码地址
        String encryptid = AesUtils.encrypt(orderid, PropertiesUtils.get("aes_key"));
        resultMap.put("qrcodeurl", PropertiesUtils.get("hui10.lottery.order.qrcodeurl") + encryptid);
        resultMap.put("promotionInfo1", PROMOTION_INFO_PRE.replace("\n", System.getProperty("line.separator")));
        resultMap.put("promotionInfo2", PROMOTION_INFO_NEXT.replace("\n", System.getProperty("line.separator")));
        return resultMap;
    }

    private List<String> getCodeDetail(Ticket ticket) {
    	ArrayList<String> ret = new ArrayList<String>();
    	JSONArray ticketJsonArray = JSON.parseArray(ticket.getBetdetail());
    	for(int i=0;i<ticketJsonArray.size();i++){
    		String code = null;
    		JSONObject ticketJson = ticketJsonArray.getJSONObject(i); ;
    		if (LotteryBetTypeEnum.DANTUO.getState().equals(ticket.getBettype())) {
    			String redfix = ticketJson.getString("redfix");
    			String reddrag = ticketJson.getString("reddrag");
    			String blue = ticketJson.getString("blue");
    			code = redfix + "|" + reddrag + "|" + blue;
    		} else if (LotteryBetTypeEnum.SINGLE.getState().equals(ticket.getBettype())) {
    			String red = ticketJson.getString("red");
    			String blue = ticketJson.getString("blue");
    			code = red + "|" + blue;
    		}
    		ret.add(code.replace(",", " ").replace("|", " | "));
    	}
        return ret;
    }

    private void copyProperty(LotteryOrder lotteryOrder, LotteryOrder order) {
        lotteryOrder.setPaytime(order.getPaytime());
        lotteryOrder.setStatus(order.getStatus());
        lotteryOrder.setOuttradeno(order.getOuttradeno());
        lotteryOrder.setOrderno(order.getOrderno());
        lotteryOrder.setOrdertime(order.getOrdertime());
    }

    private String changePhone(String phone) {
        String pre = phone.substring(0, 3);
        String end = phone.substring(8, 11);
        return pre + "*****" + end;
    }


    @Override
    public Map<String, Object> queryCompletionNotify(String authorization, String paramsBody) {
        //1、校验正确性
        check(authorization, paramsBody);
        //2、解析响应体
        Map map = JSON.parseObject(paramsBody, Map.class);
        checkMap(map);
        String merchantno = map.get("merchantno").toString();
        if (map.get("orderid") == null) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "orderid"));
        }
        String orderid = map.get("orderid").toString();
        String spid = map.get("spid").toString();
        Map<String, String> resultStationMap = getStationMsg(merchantno);
        String stationno = resultStationMap.get("stationno");
        String stationprovince = resultStationMap.get("stationprovince");
        String channelmercid = resultStationMap.get("channelmercid");

        String serialno = map.get("serialno").toString();
        LotteryOrder lotteryOrder = lotteryOrderDao.queryByOrderId(orderid);
        if (null == lotteryOrder) {
            throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
        }
        if (OrderStatusEnum.PAID.getState() != lotteryOrder.getStatus()) {
            throw new CommonException(ICommon.ORDER_NOPAY_OR_CANCLE, PropertiesUtils.get(ICommon.ORDER_NOPAY_OR_CANCLE));
        }

        if (!merchantno.equals(lotteryOrder.getMerchantno())
                || !spid.equals(lotteryOrder.getSpid())
                || !stationno.equals(lotteryOrder.getStationno())
                || !stationprovince.equals(lotteryOrder.getStationprovince())) {
            throw new CommonException(ICommon.ORDER_NOT_ALLOW, PropertiesUtils.get(ICommon.ORDER_NOT_ALLOW));

        }

        List<Ticket> lotteryOrderTickets = orderQueryDao.queryTicketListByOrderId(lotteryOrder.getOrderid());
        List<String> array = new ArrayList<>();
        
        for (Ticket ticket : lotteryOrderTickets) {
            array.addAll(getCodeDetail(ticket));
        }
        int size = array.size();

        Map<String, Object> resultMap = new HashMap<>(8);
        resultMap.put("orderid", orderid);
        resultMap.put("codedetails", array);
        resultMap.put("orderid", orderid);
        resultMap.put("orderamount", lotteryOrder.getOrderamount());
        resultMap.put("issuenumber", lotteryOrder.getIssuenumber());
        resultMap.put("gamecode", lotteryOrder.getGamecode());
        resultMap.put("ordertime", lotteryOrder.getOrdertime().getTime());
        resultMap.put("lotteryopentime", sdf.format(lotteryOrder.getLotterytime()));
        resultMap.put("lotterybetsize", size);
        resultMap.put("userphone", changePhone(lotteryOrder.getUserphone()));
        resultMap.put("spid", Constants.HUI10_SPID);
        //增加二维码地址
        String encryptid = AesUtils.encrypt(orderid, PropertiesUtils.get("aes_key"));
        resultMap.put("qrcodeurl", PropertiesUtils.get("hui10.lottery.order.qrcodeurl") + encryptid);
        resultMap.put("promotionInfo1", PROMOTION_INFO_PRE.replace("\n", System.getProperty("line.separator")));
        resultMap.put("promotionInfo2", PROMOTION_INFO_NEXT.replace("\n", System.getProperty("line.separator")));
        return resultMap;
    }


    @Override
    public Map<String, Object> cancelOrder(String authorization, String paramsBody) {
        //1、校验正确性
        check(authorization, paramsBody);
        //2、解析响应体
        Map map = JSON.parseObject(paramsBody, Map.class);
        checkMap(map);

        String merchantno = map.get("merchantno").toString();
        if (map.get("orderid") == null) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "orderid"));
        }
        String orderid = map.get("orderid").toString();
        String spid = map.get("spid").toString();
        Map<String, String> resultStationMap = getStationMsg(merchantno);
        String stationno = resultStationMap.get("stationno");
        String stationprovince = resultStationMap.get("stationprovince");
        String channelmercid = resultStationMap.get("channelmercid");
        String serialno = map.get("serialno").toString();
        LotteryOrder lotteryOrder = lotteryOrderDao.queryByOrderId(orderid);
        if (null == lotteryOrder) {
            throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
        }

        if (SourceEnum.POS.getState() != lotteryOrder.getSource()) {
            throw new CommonException(ICommon.ORDER_NOT_POS, PropertiesUtils.get(ICommon.ORDER_NOT_POS));
        }

        if (OrderStatusEnum.NOPAY.getState() != lotteryOrder.getStatus()) {
            throw new CommonException(ICommon.ORDER_PAY_OR_CANCLE, PropertiesUtils.get(ICommon.ORDER_PAY_OR_CANCLE));
        }


        if (!merchantno.equals(lotteryOrder.getMerchantno())
                || !spid.equals(lotteryOrder.getSpid())
                || !stationno.equals(lotteryOrder.getStationno())
                || !stationprovince.equals(lotteryOrder.getStationprovince())) {
            throw new CommonException(ICommon.ORDER_NOT_ALLOW, PropertiesUtils.get(ICommon.ORDER_NOT_ALLOW));
        }

        LotteryOrder order = new LotteryOrder();
        order.setOrderid(orderid);
        order.setUpdatedate(new Date());
        order.setStatus(OrderStatusEnum.CANCEL.getState());
        lotteryOrderDao.update(order);
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("orderid", orderid);
        resultMap.put("time", System.currentTimeMillis());
        resultMap.put("spid", Constants.HUI10_SPID);
        return resultMap;
    }

    @Override
    public List<LotterySaleDto> queryOnSaleIssue(String authorization, String params) {

        check(authorization, params);
        Map map = JSON.parseObject(params, Map.class);

        if (map.get("gamecodes") == null) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "gamecodes"));
        }
        List list = JSON.parseArray(map.get("gamecodes").toString());

        if (map.get("merchantno") == null) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "merchantno"));
        }
        String merchantno = map.get("merchantno").toString();
        Map<String, String> resultStationMap = getStationMsg(merchantno);
        String stationno = resultStationMap.get("stationno");
        String stationprovince = resultStationMap.get("stationprovince");
 
        if (list != null && !list.isEmpty()) {
            String[] array = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                array[i] = list.get(i).toString();
            }
            return lotteryIwtUtil.getLotterySaleDtoList(stationno, stationprovince, array);
        }
        return lotteryIwtUtil.getLotterySaleDtoList(stationno, stationprovince, null);
    }

    @Override
    public List<LotterySaleDto> queryOnSaleIssueByApp(String gamecodes) {

        String stationNo = STATION_NO;
        String stationProvince = STATION_PROVINCE;
        if (StringUtils.isEmpty(gamecodes)) {
            return lotteryIwtUtil.getLotterySaleDtoList(stationNo, stationProvince, null);
        }
        return lotteryIwtUtil.getLotterySaleDtoList(stationNo, stationProvince, gamecodes.split(","));


    }

    @Override
    public List<LotteryOrder> queryPreDayLotteryOrder() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String preDate = sdf.format(date);
        return lotteryOrderDao.queryPreDayLotteryOrder(preDate);
    }

    @Override
    public void createLotteryOrderAccount() {
        List<LotteryOrder> list = this.queryPreDayLotteryOrder();
        creatCSV(list);
    }


    private void creatCSV(List<LotteryOrder> lotteryOrders) {
        long startTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        String fileName = Constants.FTP_UNION_FILE_NAME + simpleDateFormat.format(date) + ".csv";
        String path = Constants.UNION_LOCAL_PATH;
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(path + fileName);
        try {
            OutputStream out = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.append("orderid,stationprovince,stationno,orderamount,outtradeno,merchantno,orderno,issuenumber,ordertime,gamecode,paytime,status\n");
            if (lotteryOrders != null && !lotteryOrders.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (LotteryOrder lotteryOrder : lotteryOrders) {
                    StringBuilder sb = new StringBuilder(128);
                    sb.append(lotteryOrder.getOrderid());
                    sb.append(",");
                    sb.append(lotteryOrder.getStationprovince());
                    sb.append(",");
                    sb.append(lotteryOrder.getStationno());
                    sb.append(",");
                    sb.append(lotteryOrder.getOrderamount());
                    sb.append(",");
                    sb.append(lotteryOrder.getOuttradeno());
                    sb.append(",");
                    sb.append(lotteryOrder.getMerchantno());
                    sb.append(",");
                    sb.append(lotteryOrder.getOrderno());
                    sb.append(",");
                    sb.append(lotteryOrder.getIssuenumber());
                    sb.append(",");
                    sb.append(sdf.format(lotteryOrder.getOrdertime()));
                    sb.append(",");
                    sb.append(lotteryOrder.getGamecode());
                    sb.append(",");
                    sb.append(sdf.format(lotteryOrder.getPaytime()));
                    sb.append(",");
                    sb.append(lotteryOrder.getStatus());
                    sb.append("\n");
                    writer.append(sb.toString());
                }
            }
            writer.flush();
            writer.close();
            FtpUtil.sshSftp(Constants.FTP_UNION_HOST, Constants.FTP_UNION_USERNAME, Constants.FTP_UNION_PASSWORD, Constants.FTP_UNION_PORT, Constants.FTP_UNION_REMOTEPATH, path + fileName);
        } catch (FileNotFoundException e) {
            logger.error("ftp 上传文件失败", e);
        } catch (IOException e) {
            logger.error("ftp 上传文件失败", e);
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }


    @Override
    public Map<String, Object> channelOrderResult(String orderid) {
        LotteryOrder lotteryOrder = lotteryOrderDao.queryByOrderId(orderid);
        if (null == lotteryOrder) {
            throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
        }
        String result = lotteryIwtUtil.queryBetOrderList(lotteryOrder.getOrderno(), lotteryOrder.getStationno(), lotteryOrder.getStationprovince());
        JSONObject obj = JSON.parseObject(result);
        int num = obj.getIntValue("result");
        if (num != 0) {
            throw new CommonException(num, obj.getString("resultDesc"));
        }
        Map<String, Object> resultMap = new HashMap<>(5);
        resultMap.put("orderAmount", obj.getIntValue("orderAmount"));
        resultMap.put("orderNo", obj.getString("orderNo"));
        resultMap.put("orderTime", obj.getLongValue("orderTime"));
        resultMap.put("orderStatus", obj.getIntValue("orderStatus"));
        resultMap.put("orderArray", obj.getJSONArray("orderArray"));
        return resultMap;
    }


    @Override
    public Map<String, Object> generateOrderByApp(String issuenumber, String gamecode, String betrequestarray, String uid) {
        return generateOrder(issuenumber, gamecode, betrequestarray, uid, SourceEnum.APP.getState());
    }

    @Override
    public Map<String, Object> generateOrder(String issuenumber, String gamecode, String betrequestarray, String uid, int source) {
        JSONArray jsonBetArray = JSON.parseArray(betrequestarray);
        if (jsonBetArray.isEmpty()) {
            throw new CommonException(ICommon.BET_NUMBER_ERROR, PropertiesUtils.get(ICommon.BET_NUMBER_ERROR));
        }

        String stationNo = STATION_NO;
        String stationProvince = STATION_PROVINCE;
        LotterySaleDto lotterySaleDto = lotteryIwtUtil.getLotterySaleDto(stationNo, stationProvince, 2, gamecode);
        if (!lotterySaleDto.getIssuenumber().equals(issuenumber)) {
            throw new CommonException(ICommon.LOTTERY_SALE_END, String.format(PropertiesUtils.get(ICommon.LOTTERY_SALE_END), issuenumber));
        }

        String orderid = UUIDUtil.getUUID();
        /**
         * 增加手机号
         */
        UserInfo userInfo = userInfoService.queryUserInfoByUid(uid);
        String userphone = userInfo.getPhone();

        LotteryOrder lotteryOrder = new LotteryOrder();
        lotteryOrder.setOrderid(orderid);
        lotteryOrder.setIssuenumber(issuenumber);
        lotteryOrder.setGamecode(gamecode);
        lotteryOrder.setCreatedate(new Date());
        lotteryOrder.setSource(source);
        lotteryOrder.setUserphone(userphone);
        lotteryOrder.setLotterytime(lotterySaleDto.getLotteryendtime());
        lotteryOrder.setUid(uid);

        long orderamount = 0;
        for (int i = 0; i < jsonBetArray.size(); i++) {

            JSONObject jsonObject = jsonBetArray.getJSONObject(i);
            String betType = jsonObject.getString("bettype");

            String codeDetail = null;
            if (LotteryBetTypeEnum.DANTUO.getState().equals(betType)) {
                String redfix = jsonObject.getString("redfix");
                String reddrag = jsonObject.getString("reddrag");
                String blue = jsonObject.getString("blue");
                codeDetail = redfix + "|" + reddrag + "|" + blue;
            } else if (LotteryBetTypeEnum.SINGLE.getState().equals(betType)) {
                String red = jsonObject.getString("red");
                String blue = jsonObject.getString("blue");
                codeDetail = red + "|" + blue;
            } else {

            }

            int multiple = jsonObject.getIntValue("multiple");

            //判断是否超出最大倍数
            if (multiple > LOTTERY_BIG_MULTIPLE) {
                throw new CommonException(ICommon.LOTTERY_BIG_MULTIPLE, PropertiesUtils.get(ICommon.LOTTERY_BIG_MULTIPLE));
            }
            if (multiple < 1) {
                throw new CommonException(ICommon.LOTTERY_MIN_MULTIPLE, PropertiesUtils.get(ICommon.LOTTERY_MIN_MULTIPLE));
            }


            int size = checkCodeDetail(betType, codeDetail);

            /**
             * 暂时只有双色球
             */
            long amount = 200 * multiple * size;
            orderamount += amount;
        }

        if (orderamount > LOTTERY_BIG_AMOUNT) {
            throw new CommonException(ICommon.LOTTERY_BIG_AMOUNT_ERROR, PropertiesUtils.get(ICommon.LOTTERY_BIG_AMOUNT_ERROR));
        }

        int betNumber = BetUtil.getBetNumber(BetFactory.getBet(betrequestarray)); // 总注数
        lotteryOrder.setOrderamount(Integer.parseInt(String.valueOf(orderamount)));
        lotteryOrder.setRemark(betrequestarray);
        lotteryOrder.setBetnumber(betNumber);
        lotteryOrder.setOrdertype(OrderTypeEnum.ORDINARY.getCode());

        /**
         * 插入订单
         */
        lotteryOrderDao.insert(lotteryOrder);

        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("orderid", orderid);
        return resultMap;
    }

    /**
     * 校验投注是否合法
     *
     * @param betType
     * @param code
     * @return
     */
    private int checkCodeDetail(String betType, String code) {

        String[] array = code.split("\\|");

        String redCode = array[0];

        String[] redNumbers = redCode.split(",");
        int red = redNumbers.length;
        // 判断红球号码是否有效
        int[] invalidNumbers = NumberUtils.checkInvalidNumbers(convertArray(redNumbers), DoubleColorBall.CANDIDATE_NUMBERS_RED);
        if (invalidNumbers.length > 0) {
            throw new CommonException(999, "双色球投注：红球号码中下列号码为无效号码，"
                    + NumberUtils.unionNumbers(invalidNumbers, ","));
        }
        // 判断红球号码是否有重复
        int[] repeatNumbers = NumberUtils.checkRepeatNumbers(invalidNumbers);
        if (repeatNumbers != null && repeatNumbers.length > 0) {
            throw new CommonException(999, "双色球投注：红球号码中出现重复，下列号码重复，"
                    + NumberUtils.unionNumbers(repeatNumbers, ","));
        }

        int size = 1;
        if (LotteryBetTypeEnum.DANTUO.getState().equals(betType)) {
            if (red > 5 || red < 1) {
                throw new CommonException(999, "双色球胆拖投注：红球号码必须是1-5个");
            }
            String[] tuoArray = array[1].split(",");
            int tuoCount = tuoArray.length;
            for (int j = 0; j < tuoCount; j++) {
                for (int m = 0; m < redNumbers.length; m++) {
                    if (tuoArray[j].equals(redNumbers[m])) {
                        throw new CommonException(999, "双色球投注,胆拖有重复！");
                    }
                }
            }
            if (red + tuoCount < 6) {
                throw new CommonException(999, "请至少选择6个红球（胆码+拖码）！");
            }
            String[] blueNumbers = array[2].split(",");
            int blueCount = blueNumbers.length;
            if (blueCount < 1 || blueCount > 8) {
                throw new CommonException(999, "双色球胆拖投注：蓝球号码必须是1-8个");
            }
            int[] invalidBlueNumbers = NumberUtils.checkInvalidNumbers(convertArray(blueNumbers), DoubleColorBall.CANDIDATE_NUMBERS_BLUE);
            if (invalidBlueNumbers.length > 0) {
                throw new CommonException(999, "双色球投注：蓝球号码中下列号码为无效号码，"
                        + NumberUtils.unionNumbers(invalidNumbers, ","));
            }

            if (tuoCount == 1) {
                size = count(6, blueCount);
            } else {
                size = calculateDantuoAmount(red, tuoCount, blueCount);
            }

        } else if (LotteryBetTypeEnum.MULTIPLE.getState().equals(betType)) {

            if (red < 6 || red > 20) {
                throw new CommonException(999, "双色球投注：红球号码必须是6-20个");
            }
            int blue = array[1].split(",").length;

            if (blue < 1) {
                throw new CommonException(999, "双色球复式投注：蓝球号码必须是1-16个");
            }
            int[] invalidBlueNumbers = NumberUtils.checkInvalidNumbers(convertArray(array[1].split(",")), DoubleColorBall.CANDIDATE_NUMBERS_BLUE);
            if (invalidBlueNumbers.length > 0) {
                throw new CommonException(999, "双色球投注：蓝球号码中下列号码为无效号码，"
                        + NumberUtils.unionNumbers(invalidNumbers, ","));
            }
            size = count(red, blue);
        } else {
            if (red != 6) {
                throw new CommonException(999, "双色球：红球号码必须是6个");
            }

            if (array.length < 2) {
                throw new CommonException(999, "双色球：请正确选择投注号码");
            }

            String[] blueCodeArray = array[1].split(",");
            if (blueCodeArray.length != 1) {
                throw new CommonException(999, "双色球：蓝球号码必须是1个");
            }
            int[] invalidBlueNumbers = NumberUtils.checkInvalidNumbers(convertArray(blueCodeArray), DoubleColorBall.CANDIDATE_NUMBERS_BLUE);
            if (invalidBlueNumbers.length > 0) {
                throw new CommonException(999, "双色球投注：蓝球号码中下列号码为无效号码，"
                        + NumberUtils.unionNumbers(invalidNumbers, ","));
            }
        }
        return size;
    }


    private int[] convertArray(String[] numbers) {

        int[] array = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            array[i] = Integer.parseInt(numbers[i]);
        }
        return array;
    }


    @Override
    public Map<String, Object> checkOrderInfo(String authorization, String paramsBody) {

        //1、校验正确性
        check(authorization, paramsBody);
        //2、解析响应体
        Map map = JSON.parseObject(paramsBody, Map.class);

        checkMap(map);

        String merchantno = map.get("merchantno").toString();
        if (map.get("orderid") == null) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "orderid"));
        }
        String orderid = map.get("orderid").toString();
        String spid = map.get("spid").toString();

        Map<String, String> resultStationMap = getStationMsg(merchantno);
        String stationno = resultStationMap.get("stationno");
        String stationprovince = resultStationMap.get("stationprovince");
        String channelmercid = resultStationMap.get("channelmercid");
        String serialno = map.get("serialno").toString();
        String acquirerno = map.get("acquirerno").toString();


        LotteryOrder lotteryOrder = lotteryOrderDao.queryByOrderId(orderid);
        if (null == lotteryOrder) {
            throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
        }
        if (OrderStatusEnum.NOPAY.getState() != lotteryOrder.getStatus()) {
            throw new CommonException(ICommon.ORDER_PAY_OR_CANCLE, PropertiesUtils.get(ICommon.ORDER_PAY_OR_CANCLE));
        }
        Date now = new Date();
        long nowTime = now.getTime();
        //2017-11-10 扫描结期
        if (lotteryOrder.getLotterytime().getTime() < nowTime) {
            throw new CommonException(ICommon.LOTTERY_SALE_END, String.format(PropertiesUtils.get(ICommon.LOTTERY_SALE_END), lotteryOrder.getIssuenumber()));
        }
        LotteryOrderDetail lotteryOrderDetail = new LotteryOrderDetail();
        lotteryOrderDetail.setOrderid(orderid);
        /**
         * 验证当前是否过期
         */
        lotteryIwtUtil.getLotterySaleDto(stationno, stationprovince, 2, lotteryOrder.getGamecode());

        LotteryOrder order = new LotteryOrder();
        order.setOrderid(lotteryOrder.getOrderid());
        order.setMerchantno(merchantno);
        order.setSpid(spid);
        order.setStationno(stationno);
        order.setStationprovince(stationprovince);
        order.setSerialno(serialno);
        order.setAcquirerno(acquirerno);
        order.setChannelmercid(channelmercid);
        lotteryOrderDao.update(order);
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("orderamount", lotteryOrder.getOrderamount());
        resultMap.put("orderid", lotteryOrder.getOrderid());
        resultMap.put("lotterybetsize", lotteryOrder.getBetnumber());
        resultMap.put("issuenumber", lotteryOrder.getIssuenumber());
        resultMap.put("gamecode", lotteryOrder.getGamecode());
        resultMap.put("spid", Constants.HUI10_SPID);
        resultMap.put("userphone", changePhone(lotteryOrder.getUserphone()));
        resultMap.put("tipsInfo1", PropertiesUtils.get("TIPSINFO1"));
        resultMap.put("tipsInfo2", PropertiesUtils.get("TIPSINFO2"));
        resultMap.put("phone", lotteryOrder.getUserphone());
        return resultMap;
    }

    /**
     * {
     * <p>
     * <p>
     * <p>
     * }
     *
     * @param merchantno
     * @return
     */
    private Map<String, String> getStationMsg(String merchantno) {

        Map<String, String> resultMap = merchantInfoDao.queryMerchantInfoAndHschannel(merchantno);
        if (null == resultMap || resultMap.isEmpty()) {
            throw new CommonException(ICommon.MERCHANT_NOT_EXIST, PropertiesUtils.get(ICommon.MERCHANT_NOT_EXIST));
        }
        String stationno = resultMap.get("stationno");
        String stationprovince = resultMap.get("stationprovince");
        String channelmercid = resultMap.get("channelmercid");
        if (stationno == null || stationprovince == null || channelmercid == null) {
            throw new CommonException(ICommon.MERCHANT_NOT_EXIST, PropertiesUtils.get(ICommon.MERCHANT_NOT_EXIST));
        }
        return resultMap;

    }

    private void checkMap(Map map) {
        if (map.get("merchantno") == null) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "merchantno"));
        }
        if (map.get("spid") == null) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "spid"));
        }
        if (!Constants.HUI10_SPID.equals(map.get("spid").toString())) {
            throw new CommonException(ICommon.LOTTERY_SPID_ERROR, PropertiesUtils.get(ICommon.LOTTERY_SPID_ERROR));
        }
        if (map.get("serialno") == null) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "serialno"));
        }
        if (map.get("acquirerno") == null) {
            throw new CommonException(ICommon.LOTTERY_PARAMS_ERROR, String.format(PropertiesUtils.get(ICommon.LOTTERY_PARAMS_ERROR), "acquirerno"));
        }

    }


    private String randomCode() {
        int[] a = new int[1];
        //初始化红球数组
        for (int i = 0; i < a.length; i++) {
            a[i] = -1;
        }
        int[] d = new int[6];
        //初始化蓝球数组
        for (int i = 0; i < d.length; i++) {
            d[i] = -1;
        }
        StringBuilder sb = new StringBuilder();

        //记录红球不重复的数字
        int bi = 0;
        while (bi < a.length) {
            boolean fal = true;
            Random r = new Random();
            int sui = r.nextInt(16) + 1;
            for (int i = 0; i < a.length; i++) {
                if (sui == a[i]) {
                    fal = false;
                    break;
                }
            }
            if (fal) {
                a[bi] = sui;
                bi++;
            }
        }
        Arrays.sort(a);
        //记录蓝球不重复的数字
        int di = 0;
        while (di < d.length) {
            boolean fal = true;
            Random r = new Random();
            int sui = r.nextInt(33) + 1;
            for (int i = 0; i < d.length; i++) {
                if (sui == d[i]) {
                    fal = false;
                    break;
                }
            }
            if (fal) {
                d[di] = sui;
                di++;
            }
        }


        Arrays.sort(d);

        for (int i = 0; i < d.length; i++) {
            if (d[i] < 10) {
                sb.append("0").append(d[i]);
            } else {
                sb.append(d[i]);
            }
            if (i < d.length - 1) {
                sb.append(",");
            }
        }
        sb.append("|");
        int max = 10;
        if (a[0] < max) {
            sb.append("0").append(a[0]);
        } else {
            sb.append(a[0]);
        }
        return sb.toString();
    }

    /**
     * 校验数据
     *
     * @param auth
     * @param body
     */
    private void check(String auth, String body) {
        if (!logger.isDebugEnabled()) {
            String sig = Md5Util.getSignature(UNIONPAY_MD5_PREFIX, body, UNIONPAY_MD5_SUFFIX);
            if (!auth.equals(sig)) {
                throw new CommonException(ICommon.PARAM_SIG_ERROE, PropertiesUtils.get(ICommon.PARAM_SIG_ERROE));
            }

        }
    }


    @Override
    public LotteryOrder selectByOrderid(String orderid) {
        LotteryOrder order = lotteryOrderDao.queryByOrderId(orderid);
        if (null == order) {
            throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
        }
        return order;
    }


    /***
     * 计算胆拖总注数
     */

    private int calculateDantuoAmount(int danCount, int tuoCount, int blueCount) {
        if (danCount < 1 || tuoCount < 2 || blueCount < 1) {
            return 1;
        }

        return numerator(tuoCount, tuoCount, 6 - danCount) / fib(6 - danCount) * blueCount;
    }

    private int numerator(int m, int m2, int n) {
        if (m < (m2 - n + 1)) {  // m*(m-1)*(m-2)*(m-3)*(m-n+1)
            return 1;
        }
        return m * numerator(m - 1, m2, n);
    }


    private int fib(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * fib(n - 1);

    }


    private int count(int redBall, int blueBall) {
        int p = 1;
        int c = 1;
        for (int i = 1; i <= 6; i++) {
            c = c * i;
        }
        for (int j = (redBall - 5); j <= redBall; j++) {
            p = p * j;
        }
        return p / c * blueBall;
    }


	@Override
	public void updateOrderStatus(JSONObject transData) {
		
		//期号
		String issuenumber = transData.getString("issueno");
		//彩种
		String lotterycode = transData.getString("lotterycode");
		//宝乐彩营销活动ID
		String resource = transData.getString("promotionid");
		//投注结果
		int status = Integer.parseInt(transData.getString("status"));
		
		if(status == 0){
			status = OrderStatusEnum.FAILBILL.getState();
		}else{
			status = OrderStatusEnum.PAID.getState();
			//添加投注通知推送消息
			messageService.addPushMessage(null, null, MessageTypeEnum.BET_NOTIFY.getCode());
		}
		lotteryOrderDao.updateOrderStatusByPoslotNotify(issuenumber, lotterycode, resource, status);
    }

    @Override
    public Map<String, Object> sameNumberBetting(String uid, String orderId) {
        LotteryOrder lotteryOrder = lotteryOrderDao.queryByOrderId(orderId);
        if (null == lotteryOrder) {
            throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
        }
        if (!uid.equals(lotteryOrder.getUid())) {
            throw new CommonException(ICommon.NOTRIGHT_VIEW_ORDER, PropertiesUtils.get(ICommon.NOTRIGHT_VIEW_ORDER));
        }
        String stationNo = STATION_NO;
        String stationProvince = STATION_PROVINCE;
        LotterySaleDto lotterySaleDto = lotteryIwtUtil.getLotterySaleDto(stationNo, stationProvince, 2, lotteryOrder.getGamecode());
        return generateOrderByApp(lotterySaleDto.getIssuenumber(), lotteryOrder.getGamecode(), lotteryOrder.getRemark(), lotteryOrder.getUid());
    }
}
