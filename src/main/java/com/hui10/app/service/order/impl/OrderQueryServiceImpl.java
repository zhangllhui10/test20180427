package com.hui10.app.service.order.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.encrypt.AesUtils;
import com.hui10.app.common.lottery.HttpUtil;
import com.hui10.app.common.lottery.dto.LotterySaleDto;
import com.hui10.app.common.utils.StringFormat;
import com.hui10.app.dao.main.MainDao;
import com.hui10.app.dao.order.OrderQueryDao;
import com.hui10.app.model.enums.HomeLotteryStatusEnum;
import com.hui10.app.model.enums.LotteryStatusEnum;
import com.hui10.app.model.enums.PosLotOrderStatusEnum;
import com.hui10.app.model.enums.PrizeBonusStatusEnum;
import com.hui10.app.model.enums.PrizeLevelEnum;
import com.hui10.app.model.enums.PrizeWinStatusEnum;
import com.hui10.app.model.lottery.LotteryPast;
import com.hui10.app.model.main.HomeLottery;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.order.Ticket;
import com.hui10.app.model.order.enums.LotteryBetTypeEnum;
import com.hui10.app.model.order.enums.LotteryGameCodeEnum;
import com.hui10.app.model.order.enums.OrderListTypeEnum;
import com.hui10.app.model.order.enums.OrderStatusEnum;
import com.hui10.app.model.order.enums.OrderTypeEnum;
import com.hui10.app.model.user.BigprizeHandle;
import com.hui10.app.model.user.MediumHandle;
import com.hui10.app.model.user.WithdrawRecord;
import com.hui10.app.service.main.MainService;
import com.hui10.app.service.order.LotteryOrderSerice;
import com.hui10.app.service.order.OrderQueryService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2018年03月08日 11:28
 */
@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    @Autowired
    private LotteryOrderSerice lotteryOrderSerice;

    @Autowired
    private MainService mainService;

    @Autowired
    private OrderQueryDao orderQueryDao;

    @Autowired
    private MainDao mainDao;

    @Override
    public JSONObject queryOrderList(String uid, String type, String orderId) {
        OrderListTypeEnum orderListTypeEnum = OrderListTypeEnum.getEnum(type);
        if (null == orderListTypeEnum) {
            throw new CommonException(ICommon.QUERY_TYPE_ERROR, PropertiesUtils.get(ICommon.QUERY_TYPE_ERROR));
        }

        boolean winPrizeFlag = false; // 是否有中奖未领取订单
        Date sysDate = new Date();

        // 订单列表信息
        List<HomeLottery> lotteryList = orderQueryDao.queryOrderList(uid, type, orderId, sysDate);
        for (HomeLottery homeLottery : lotteryList) {
            homeLottery.setCodedetail(mainService.getFirstCodetail(homeLottery.getRemark()));
            // 过期（已超过投注截至时间的未支付订单）
            if (OrderTypeEnum.ORDINARY.getCode().equals(homeLottery.getOrdertype())
            		&&sysDate.after(homeLottery.getLotterytime())
                    && homeLottery.getStatus() == OrderStatusEnum.NOPAY.getState()) {
                homeLottery.setStatus(HomeLotteryStatusEnum.EXPIRED.getState());
                continue;
            }
            mainService.resetOrderStatus(homeLottery, new Date(), winPrizeFlag);
        }

        String lastOrderId = null;
        if (null != lotteryList && !lotteryList.isEmpty()) {
            lastOrderId = lotteryList.get(lotteryList.size() - 1).getOrderid();
        }

        JSONObject cardJson = mainService.getUserLastUsedCard(uid, winPrizeFlag);

        JSONObject resultJson = new JSONObject();
        resultJson.put("lotterylist", lotteryList);
        resultJson.put("lastorderid", lastOrderId);
        resultJson.put("bankcard", cardJson);
        return resultJson;
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public JSONObject getSaleIssueLottery(String lotteryType) {
        JSONObject result = new JSONObject();

        try {

            List<LotterySaleDto> lotterySaleDtoList = lotteryOrderSerice.queryOnSaleIssueByApp(lotteryType);
            for (LotterySaleDto lotterySaleDto : lotterySaleDtoList) {
                result.put("nextlotteryendtime", lotterySaleDto.getLotteryendtime());
                break;
            }

        } catch (CommonException e) {
            if (e.getCode() == ICommon.ONSALEISSUE_ERROR) {
                result.put("nextlotteryendtime", null);
            } else {
                throw e;
            }
        }

        LotteryPast lotteryPast = orderQueryDao.queryRecentLotteryInfo(LotteryGameCodeEnum.DCB.getState());
        result.put("issuenumber", lotteryPast.getIssuenumber());
        result.put("lotterytype", lotteryPast.getLotterytype());
        result.put("codedetail", lotteryPast.getLotterynumber());
        result.put("lotteryendtime", lotteryPast.getLotteryendtime());
        return result;

    }

    @Override
    public JSONObject queryOrderDetail(String uid, String orderId) {

        LotteryOrder lotteryOrder = orderQueryDao.queryOrderInfoByOrderId(orderId);
        if (null == lotteryOrder) {
            throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
        }

        if (!uid.equals(lotteryOrder.getUid())) {
            throw new CommonException(ICommon.NOTRIGHT_VIEW_ORDER, PropertiesUtils.get(ICommon.NOTRIGHT_VIEW_ORDER));
        }

        boolean winPrizeFlag = false; // 是否有中奖未领取订单

        if (lotteryOrder.getWinstatus() == Integer.parseInt(PrizeWinStatusEnum.WIN.getCode())
                && lotteryOrder.getBonusstatus() == Integer.parseInt(PrizeBonusStatusEnum.NOT_DRAW.getCode())) {
            winPrizeFlag = true;
        }

        List<Ticket> orderDetailList = orderQueryDao.queryTicketListByOrderId(lotteryOrder.getOrderid());
        List<Map<String, Object>> successList = new ArrayList<Map<String, Object>>();
        for (Ticket ticket : orderDetailList) {
            switch (PosLotOrderStatusEnum.findByValue(ticket.getStatus())) {
                case ALLTICKET:
                    successList.addAll(getSingleCodetail(ticket.getBetdetail()));
                    break;
            }
        }

        Date systemDate = new Date();
        /**
         * 过期订单注码显示订单投注信息
         */
        if (OrderTypeEnum.ORDINARY.getCode().equals(lotteryOrder.getOrdertype())
                && systemDate.after(lotteryOrder.getLotterytime())
                && lotteryOrder.getStatus() == OrderStatusEnum.NOPAY.getState()) {
            successList.addAll(getCodetailList(lotteryOrder.getRemark()));
        }

        /**
         * 如果是已领取的赠送订单并且未出票
         * 不展示注码
         */
        if (OrderTypeEnum.GIVING.getCode().equals(lotteryOrder.getOrdertype())
                && lotteryOrder.getStatus() == OrderStatusEnum.NOPAY.getState()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("codedetail", "*,*,*,*,*,*|*");
            map.put("multiple", "1");
            successList.add(map);
        }


        JSONObject cardJson = mainService.getUserLastUsedCard(uid, winPrizeFlag);

        JSONObject result = new JSONObject();
        result.put("orderid", lotteryOrder.getOrderid());
        result.put("lotterytype", lotteryOrder.getGamecode());
        result.put("issuenumber", lotteryOrder.getIssuenumber());
        result.put("ordertype", lotteryOrder.getOrdertype());
        result.put("betnumber", lotteryOrder.getBetnumber());
        result.put("merchantname", lotteryOrder.getMerchantname());
        result.put("stationno", lotteryOrder.getStationno());
        result.put("source", lotteryOrder.getSource());
        result.put("paytime", lotteryOrder.getPaytime());
        result.put("opentime", lotteryOrder.getLotterytime());
        result.put("winprize", lotteryOrder.getWinprize());
        result.put("sendprize", lotteryOrder.getSendprize());
        result.put("bonusstatus", lotteryOrder.getBonusstatus());
        result.put("prizelevel", lotteryOrder.getPrizelevel());
        result.put("remark", lotteryOrder.getRemark());
        result.put("successcode", successList);
        result.put("bankcard", cardJson);
        result.put("bankname", null);
        result.put("bankcardno", null);
        result.put("name", null);
        result.put("applytime", null);
        result.put("sendprizetime", null);
        result.put("reason", null);
        resetOrderStatus(lotteryOrder, systemDate, result);// 重置订单状态
        return result;

    }

    private void resetOrderStatus(LotteryOrder lotteryOrder, Date systemDate, JSONObject result) {

        // 出票失败
        if (lotteryOrder.getStatus() == OrderStatusEnum.FAILBILL.getState()) {
            result.put("status", HomeLotteryStatusEnum.TICKET_FAIL.getState());
        }

        // 取消订单
        if (lotteryOrder.getStatus() == OrderStatusEnum.CANCEL.getState()) {
            result.put("status", HomeLotteryStatusEnum.CANCLE.getState());
        }

        // 已过期
        if (OrderTypeEnum.ORDINARY.getCode().equals(lotteryOrder.getOrdertype())
                && systemDate.after(lotteryOrder.getLotterytime())
                && lotteryOrder.getStatus() == OrderStatusEnum.NOPAY.getState()) {
            result.put("status", HomeLotteryStatusEnum.EXPIRED.getState());
            return;
        }

        // 待支付
        if (OrderTypeEnum.ORDINARY.getCode().equals(lotteryOrder.getOrdertype())
                && lotteryOrder.getLotterytime().after(systemDate)
                && lotteryOrder.getStatus() == OrderStatusEnum.NOPAY.getState()) {
            result.put("status", HomeLotteryStatusEnum.WAIT_PAY.getState());
            return;
        }

        // 待开奖（出票成功并且未开奖）
        if (lotteryOrder.getStatus() == OrderStatusEnum.PAID.getState()
                && lotteryOrder.getLotterystatus().equals(LotteryStatusEnum.NO_OPEN.getCode())) {
            result.put("status", HomeLotteryStatusEnum.WAIT_OPEN.getState());
            return;
        }

        // 已中奖未领取奖金
        if (lotteryOrder.getWinstatus() == Integer.parseInt(PrizeWinStatusEnum.WIN.getCode())
                && lotteryOrder.getBonusstatus() == Integer.parseInt(PrizeBonusStatusEnum.NOT_DRAW.getCode())) {
            result.put("status", HomeLotteryStatusEnum.WINNING.getState());
            return;
        }

        // 已中奖已领取奖金
        if (lotteryOrder.getWinstatus() == Integer.parseInt(PrizeWinStatusEnum.WIN.getCode())
                && lotteryOrder.getBonusstatus() != Integer.parseInt(PrizeBonusStatusEnum.NOT_DRAW.getCode())) {
            result.put("status", HomeLotteryStatusEnum.WINNING.getState());
            // 中中等奖
            if (PrizeLevelEnum.MEDIUM.getCode().equals(lotteryOrder.getPrizelevel())) {
                MediumHandle mediumHandle = mainDao.queryWinOrderMedium(lotteryOrder.getOrderid());
                if (null == mediumHandle) return;
                result.put("bankname", mediumHandle.getBankname());
                result.put("bankcardno", StringFormat.formatCardNumber(mediumHandle.getBankno()));
                result.put("name", StringFormat.formatName(mediumHandle.getName()));
                result.put("applytime", mediumHandle.getCreatetime());
                result.put("sendprizetime", mediumHandle.getUpdatetime());
                if (PrizeBonusStatusEnum.CER_CHECK_FAIL.getCode().equals(Integer.toString(lotteryOrder.getBonusstatus()))) {
                    result.put("reason", Constants.AUDIT_FAIL_MESSAGE.replace("\n", System.getProperty("line.separator")));
                } else if (PrizeBonusStatusEnum.DRAW_ERROR.getCode().equals(Integer.toString(lotteryOrder.getBonusstatus()))) {
                    result.put("reason", Constants.SEND_PRIZE_FAIL_MESSAGE.replace("\n", System.getProperty("line.separator")));
                }
                return;
            } else if (PrizeLevelEnum.SMALL.getCode().equals(lotteryOrder.getPrizelevel())) { // 中小奖
                WithdrawRecord withdrawRecord = mainDao.querySmallBouns(lotteryOrder.getOrderid());
                if (null == withdrawRecord) return;
                result.put("bankname", withdrawRecord.getPayeebank());
                result.put("bankcardno", StringFormat.formatCardNumber(withdrawRecord.getPayeeno()));
                result.put("name", StringFormat.formatName(withdrawRecord.getPayeename()));
                result.put("applytime", withdrawRecord.getCreatetime());
                result.put("sendprizetime", withdrawRecord.getUpdatetime());
                result.put("reason", null);
                return;
            } else if (PrizeLevelEnum.BIG.getCode().equals(lotteryOrder.getPrizelevel())) { // 中大奖
                BigprizeHandle bigprizeHandle = mainDao.queryWinOrderBigPrize(lotteryOrder.getOrderid());
                if (null == bigprizeHandle) return;
                result.put("bankname", null);
                result.put("bankcardno", null);
                result.put("name", StringFormat.formatName(bigprizeHandle.getWinnername()));
                result.put("applytime", bigprizeHandle.getCreatetime());
                result.put("sendprizetime", bigprizeHandle.getHandletime());
                result.put("reason", null);
            }
        }
        // 未中奖
        if (lotteryOrder.getStatus() == OrderStatusEnum.PAID.getState()
                && lotteryOrder.getLotterystatus().equals(LotteryStatusEnum.HAVE_OPE.getCode())
                && lotteryOrder.getWinstatus() == Integer.parseInt(PrizeWinStatusEnum.NOT_WIN.getCode())) {
            result.put("status", HomeLotteryStatusEnum.NOT_WINNING.getState());
            return;
        }


    }

    @Override
    public String queryOrderStatus(String uid, String orderId) {
        LotteryOrder lotteryOrder = orderQueryDao.queryOrderInfoByOrderId(orderId);
        if (null == lotteryOrder) {
            throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
        }
        if (!uid.equals(lotteryOrder.getUid())) {
            throw new CommonException(ICommon.NOTRIGHT_VIEW_ORDER, PropertiesUtils.get(ICommon.NOTRIGHT_VIEW_ORDER));
        }
        return String.valueOf(lotteryOrder.getStatus());
    }

    @Override
    public JSONObject queryOrderInfo(String uid, String orderId) {
        LotteryOrder lotteryOrder = orderQueryDao.queryOrderInfoByOrderId(orderId);
        if (null == lotteryOrder) {
            throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
        }
        if (!uid.equals(lotteryOrder.getUid())) {
            throw new CommonException(ICommon.NOTRIGHT_VIEW_ORDER, PropertiesUtils.get(ICommon.NOTRIGHT_VIEW_ORDER));
        }
        if (OrderStatusEnum.NOPAY.getState() != lotteryOrder.getStatus()) {
            throw new CommonException(ICommon.ORDER_PAY_OR_CANCLE, PropertiesUtils.get(ICommon.ORDER_PAY_OR_CANCLE));
        }
        List<Map<String, Object>> codeDetailList = new ArrayList<Map<String, Object>>();
        codeDetailList.addAll(getCodetailList(lotteryOrder.getRemark()));
        // 下一笔待支付订单
        String waitPayOrderId = orderQueryDao.queryWaitPayOrderId(uid, orderId);
        // 增加二维码地址
        String encryptid = AesUtils.encrypt(lotteryOrder.getOrderid(), PropertiesUtils.get("aes_key"));

        JSONObject resultJson = new JSONObject();
        resultJson.put("lotterytype", lotteryOrder.getGamecode());
        resultJson.put("issuenumber", lotteryOrder.getIssuenumber());
        resultJson.put("lotterytime", lotteryOrder.getLotterytime());
        resultJson.put("betnumber", lotteryOrder.getBetnumber());
        resultJson.put("waitpayorderid", waitPayOrderId);
        resultJson.put("codelist", codeDetailList);
        resultJson.put("qrcodeurl", String.format(PropertiesUtils.get("UNIONPAY.QR.CODE"), encryptid));
        return resultJson;
    }

    @Override
    public boolean printBigPrizeLottery(String uid, String orderId, String wifimac) {
        LotteryOrder lotteryOrder = orderQueryDao.queryOrderInfoByOrderId(orderId);
        if (null == lotteryOrder) {
            throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
        }
        if (!uid.equals(lotteryOrder.getUid())) { // 打印人与下单人是否为同一个人
            throw new CommonException(ICommon.ORDER_NOT_ALLOW, PropertiesUtils.get(ICommon.ORDER_NOT_ALLOW));
        }
        if (!PrizeWinStatusEnum.WIN.getCode().equals(Integer.toString(lotteryOrder.getWinstatus()))) { // 是否中奖
            throw new CommonException(ICommon.LOTTERY_NOT_WINNING, PropertiesUtils.get(ICommon.LOTTERY_NOT_WINNING));
        }
        if (!PrizeLevelEnum.BIG.getCode().equals(lotteryOrder.getPrizelevel())) { // 是否是大奖
            throw new CommonException(ICommon.LOTTERY_NOT_BIGPRIZE, PropertiesUtils.get(ICommon.LOTTERY_NOT_BIGPRIZE));
        }

        Map<String, Object> map = new HashMap<String, Object>(10);
        map.put("orderid", lotteryOrder.getOrderno());
        map.put("wifimac", wifimac);

        String transSerialNumber = UUIDUtil.getUUID();
        String prefixUrl = PropertiesUtils.get("lottery_url");
        String suffixUrl = PropertiesUtils.get("lottery_print_url");
        HttpUtil httpUtil = HttpUtil.getInstance();
        // 请求宝乐彩打印彩票
        String response = httpUtil.doPost(transSerialNumber, String.format(prefixUrl, suffixUrl), map);

        JSONObject jsonObject = JSON.parseObject(response);
        int ec = jsonObject.getInteger("ec");
        String em = jsonObject.getString("em");
        if (200 != ec) {
            throw new CommonException(ec, em);
        }
        return jsonObject.getBoolean("result");
    }

    private List<Map<String, Object>> getCodetailList(String remark) {
        JSONArray jsonArray = JSONArray.parseArray(remark);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i=0; i<jsonArray.size(); i++) {
            JSONObject job = jsonArray.getJSONObject(i);
            Map<String, Object> map = new HashMap<String, Object>();
            if (LotteryBetTypeEnum.DANTUO.getState().equals(job.getString("bettype"))) {
                String redfix = job.getString("redfix");
                String reddrag = job.getString("reddrag");
                String blue = job.getString("blue");
                map.put("codedetail", redfix + "|" + reddrag + "|" + blue);
                map.put("multiple", job.getIntValue("multiple"));
                list.add(map);
            } else if (LotteryBetTypeEnum.SINGLE.getState().equals(job.getString("bettype"))) {
                String red = job.getString("red");
                String blue = job.getString("blue");
                map.put("codedetail", red + "|" + blue);
                map.put("multiple", job.getIntValue("multiple"));
                list.add(map);
            }
        }
        return list;
    }

    private List<Map<String, Object>> getSingleCodetail(String codetail) {
        
    	List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
     	JSONArray ticketJsonArray = JSON.parseArray(codetail);
     	for(int i=0;i<ticketJsonArray.size();i++){
     		String code = null;
     		JSONObject ticketJson = ticketJsonArray.getJSONObject(i); ;
     		if (LotteryBetTypeEnum.DANTUO.getState().equals(ticketJson.getString("bettype"))) {
     			String redfix = ticketJson.getString("redfix");
     			String reddrag = ticketJson.getString("reddrag");
     			String blue = ticketJson.getString("blue");
     			code = redfix + "|" + reddrag + "|" + blue;
     		} else if (LotteryBetTypeEnum.SINGLE.getState().equals(ticketJson.getString("bettype"))) {
     			String red = ticketJson.getString("red");
     			String blue = ticketJson.getString("blue");
     			code = red + "|" + blue;
     		}
     		Map<String, Object> map = new HashMap<String, Object>();
     		map.put("codedetail", code);
     		map.put("multiple", ticketJson.getString("multiple"));
     		ret.add(map);
     	}
     	
        return ret;
    }
}

