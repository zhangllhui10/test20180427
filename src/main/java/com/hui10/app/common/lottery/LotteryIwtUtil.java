package com.hui10.app.common.lottery;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.lottery.dto.BetRequest;
import com.hui10.app.common.lottery.dto.LotterySaleDto;
import com.hui10.app.model.enums.PosLotOrderStatusEnum;
import com.hui10.app.model.order.Ticket;
import com.hui10.app.model.order.enums.OrderStatusEnum;
import com.hui10.common.cache.CacheManager;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 调用英泰接口工具
 *
 * @author yangcb
 * @create 2017-10-17 18:36
 **/
@Component
public class LotteryIwtUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 投注站操作令牌KEY
     */
    public static final String CHANNELTOKEN_KEY = "ACCESSTOKEN_%s_%s";

    /**
     * 售期
     */
    public static final String SALE_DATE = "SALE_DATE_%s";


    /**
     * 操作令牌超时或无效
     */
    private static final int ACCESSTOKEN_ERROR_CODE = 1001;


    @Autowired
    private CacheManager cacheManager;


    private static int TIMES = 3;

    /**
     * 查询当前在售彩票期
     *
     * @return
     */
    private List<LotterySaleDto> queryBet(String stationNo, String stationProvince, int times) {

        if (times > TIMES) {
            throw new CommonException(ICommon.QUERYONSALEISSUE_ERROR, PropertiesUtils.get(ICommon.QUERYONSALEISSUE_ERROR));
        }

        String transSerialNumber = UUIDUtil.getUUID();
        Map<String, Object> params = new HashMap<>();
        params.put("stationNo", stationNo);
        params.put("stationProvince", stationProvince);
        params.put("channelToken", getChannelTokenStr(stationNo, stationProvince));
        ArrayList<String> array = new ArrayList<>();
        params.put("gameCodeArray", array);
        String result = doPost(transSerialNumber, params, PropertiesUtils.get("lottery_url"), PropertiesUtils.get("lottery_queryonsaleissue_url"));
        logger.info("queryBet response result={}", result);
        JSONObject jsonObject = JSON.parseObject(result);
        int resultCode = jsonObject.getInteger("result");
        if (resultCode == 0) {
            JSONArray jsonArray = JSON.parseArray(jsonObject.getString("lottery"));
            List<LotterySaleDto> lotterySaleDtos = new ArrayList<>(jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                JSONObject obj = jsonArray.getJSONObject(i);
                String issuenumber = obj.getString("issueNumber");
                String gameCode = obj.getString("gameCode");
                Date lotterystarttime = new Date(obj.getLong("lotterystarttime"));
                Date lotteryendtime = new Date(obj.getLong("lotteryendtime"));
                //在原来的基础上减去10分钟
                if ("10001".equals(gameCode)) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(lotteryendtime);
                    calendar.add(Calendar.MINUTE, -10);
                    lotteryendtime = calendar.getTime();
                }

                LotterySaleDto lotterySaleDto = new LotterySaleDto();
                lotterySaleDto.setGamecode(gameCode);
                lotterySaleDto.setIssuenumber(issuenumber);
                lotterySaleDto.setLotteryendtime(lotteryendtime);
                lotterySaleDto.setLotterystarttime(lotterystarttime);
                lotterySaleDtos.add(lotterySaleDto);
            }
            cacheManager.put(String.format(SALE_DATE, stationProvince), lotterySaleDtos, 0);
            return lotterySaleDtos;
        } else if (resultCode == ACCESSTOKEN_ERROR_CODE) {
            times += 1;
            cacheManager.delete(String.format(CHANNELTOKEN_KEY, stationNo, stationProvince));
            return queryBet(stationNo, stationProvince, times);
        } else {
            throw new CommonException(ICommon.QUERYONSALEISSUE_ERROR, PropertiesUtils.get(ICommon.QUERYONSALEISSUE_ERROR));
        }

    }


    public List<LotterySaleDto> queryBet(String stationNo, String stationProvince) {
        return queryBet(stationNo, stationProvince, 0);
    }


    public synchronized List<LotterySaleDto> getLotterySaleDtoList(String stationNo, String stationProvince, String[] array) {
        List<LotterySaleDto> lotterySaleDtos = (List<LotterySaleDto>) cacheManager.get(String.format(SALE_DATE, stationProvince));

        lotterySaleDtos = checkSale(lotterySaleDtos, stationNo, stationProvince, array);

        if (lotterySaleDtos == null || lotterySaleDtos.isEmpty()) {
            lotterySaleDtos = checkSale(null, stationNo, stationProvince, array);
            if (lotterySaleDtos.isEmpty()) {
                throw new CommonException(ICommon.ONSALEISSUE_ERROR, PropertiesUtils.get(ICommon.ONSALEISSUE_ERROR));
            }
            checkTime(lotterySaleDtos, stationNo, stationProvince);
            return lotterySaleDtos;
        }
        checkTime(lotterySaleDtos, stationNo, stationProvince);
        return lotterySaleDtos;
    }

    /**
     * 校验时间是否期结
     *
     * @param lotterySaleDtos
     * @param stationNo
     * @param stationProvince
     */
    public void checkTime(List<LotterySaleDto> lotterySaleDtos, String stationNo, String stationProvince) {
        //验证售期是否过期，
        if (!lotterySaleDtos.isEmpty()) {
            Date now = new Date();
            for (LotterySaleDto lotterySale : lotterySaleDtos) {
                if (lotterySale.getLotterystarttime().getTime() > now.getTime() || lotterySale.getLotteryendtime().getTime() < now.getTime()) {
                    cacheManager.delete(String.format(SALE_DATE, stationProvince));
                    throw new CommonException(ICommon.ONSALEISSUE_ERROR, PropertiesUtils.get(ICommon.ONSALEISSUE_ERROR));
                }
            }
        }
    }

    /**
     * 校验售期是否结束
     *
     * @param lotterySaleDtos
     * @param stationNo
     * @param stationProvince
     * @param array
     * @return
     */
    private List<LotterySaleDto> checkSale(List<LotterySaleDto> lotterySaleDtos, String stationNo, String stationProvince, String[] array) {
        //验证售期是否过期，
        if (lotterySaleDtos != null && !lotterySaleDtos.isEmpty()) {
            Date now = new Date();
            for (LotterySaleDto lotterySale : lotterySaleDtos) {
                if (lotterySale.getLotterystarttime().getTime() > now.getTime() || lotterySale.getLotteryendtime().getTime() < now.getTime()) {
                    cacheManager.delete(String.format(SALE_DATE, stationProvince));
                    lotterySaleDtos = null;
                    break;
                }
            }
        }
        if (lotterySaleDtos == null || lotterySaleDtos.isEmpty()) {
            lotterySaleDtos = queryBet(stationNo, stationProvince);
            if (lotterySaleDtos == null) {
                throw new CommonException(ICommon.ONSALEISSUE_ERROR, PropertiesUtils.get(ICommon.ONSALEISSUE_ERROR));
            }
        }
        if (array == null || array.length == 0) {
            return queryBet(stationNo, stationProvince);
        }
        List<LotterySaleDto> saleDtos = new ArrayList<>(array.length);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < lotterySaleDtos.size(); j++) {
                LotterySaleDto lotterySaleDto = lotterySaleDtos.get(j);
                if (lotterySaleDto.getGamecode().equals(array[i])) {
                    saleDtos.add(lotterySaleDto);
                    break;
                }
            }
        }

        return saleDtos;
    }

    /**
     * @param falg     1-pos机选  2-app
     * @param gameCode
     * @return
     */
    public synchronized LotterySaleDto getLotterySaleDto(String stationno, String stationprovince, int falg, String gameCode) {

        return getLotterySaleDto(stationno, stationprovince, falg, gameCode, 0);
    }

    private LotterySaleDto getLotterySaleDto(String stationno, String stationprovince, int falg, String gameCode, int times) {

        if (times > TIMES) {
            throw new CommonException(ICommon.ONSALEISSUE_ERROR, PropertiesUtils.get(ICommon.ONSALEISSUE_ERROR));
        }

        List<LotterySaleDto> lotterySaleDtos = (List<LotterySaleDto>) cacheManager.get(String.format(SALE_DATE, stationprovince));
        if (lotterySaleDtos == null || lotterySaleDtos.isEmpty()) {
            lotterySaleDtos = queryBet(stationno, stationprovince);
            if (lotterySaleDtos == null) {
                throw new CommonException(ICommon.ONSALEISSUE_ERROR, PropertiesUtils.get(ICommon.ONSALEISSUE_ERROR));
            }
        }
        LotterySaleDto lotterySale = null;
        for (LotterySaleDto lotterySaleDto : lotterySaleDtos) {
            if (lotterySaleDto.getGamecode().equals(gameCode)) {
                lotterySale = lotterySaleDto;
                break;
            }
        }
        if (lotterySale == null) {
            throw new CommonException(ICommon.ONSALEISSUE_ERROR, PropertiesUtils.get(ICommon.ONSALEISSUE_ERROR));
        }
        Date now = new Date();
        if (falg == 1) {
            if (lotterySale.getLotteryendtime().getTime() < now.getTime()) {
                //销售截止或者还未开始销售
                cacheManager.delete(String.format(SALE_DATE, stationprovince));
                times += 1;
                return getLotterySaleDto(stationno, stationprovince, falg, gameCode, times);
            }
        } else {
            if (lotterySale.getLotterystarttime().getTime() > now.getTime() || lotterySale.getLotteryendtime().getTime() < now.getTime()) {
                cacheManager.delete(String.format(SALE_DATE, stationprovince));
                throw new CommonException(ICommon.LOTTERY_SALE_END, String.format(PropertiesUtils.get(ICommon.LOTTERY_SALE_END), lotterySale.getIssuenumber()));
            }
        }
        return lotterySale;
    }

    /**
     * 查询单个订单接⼝
     *
     * @param orderNo         宝乐彩订单号
     * @param stationNo       投注站编号
     * @param stationProvince 投注站省份
     * @return
     */
    public String queryBetOrderList(String orderNo, String stationNo, String stationProvince) {
        return queryBetOrderList(orderNo, stationNo, stationProvince, 0);
    }

    private String queryBetOrderList(String orderNo, String stationNo, String stationProvince, int times) {

        if (times > TIMES) {
            throw new CommonException(ICommon.ONSALEISSUE_ERROR, PropertiesUtils.get(ICommon.ONSALEISSUE_ERROR));
        }

        Map<String, Object> map = new HashMap<String, Object>(10);
        map.put("orderNo", orderNo);
        map.put("channelToken", getChannelTokenStr(stationNo, stationProvince));
        map.put("stationNo", stationNo);
        map.put("stationProvince", stationProvince);
        String transSerialNumber = UUIDUtil.getUUID();
        String result = doPost(transSerialNumber, map, PropertiesUtils.get("lottery_url"), PropertiesUtils.get("lottery_channelorderresult_url"));
        logger.info("queryBetOrderList response result={}", result);
        JSONObject jsonObject = JSON.parseObject(result);
        int resultCode = jsonObject.getInteger("result");
        if (resultCode == 0) {
            return result;
        } else if (resultCode == ACCESSTOKEN_ERROR_CODE) {
            cacheManager.delete(String.format(CHANNELTOKEN_KEY, stationNo, stationProvince));
            times += 1;
            return queryBetOrderList(orderNo, stationNo, stationProvince, times);
        } else {
            throw new CommonException(resultCode, jsonObject.getString("resultDesc"));
        }
    }

    /**
     * 投注订单
     *
     * @param stationNo
     * @param stationProvince
     * @param gameCode
     * @param issueNumber
     * @param betRequestList
     * @return
     */
    public Map<String, String> betOrder(String stationNo, String stationProvince, String gameCode, String issueNumber, List<BetRequest> betRequestList) {
        return betOrder(stationNo, stationProvince, gameCode, issueNumber, betRequestList, 0);
    }

    public Map<String, Object> betOrder(String stationNo, String userphone, String orderid, String stationProvince, String gameCode, String issueNumber, String betdetail) {
        return betOrder(stationNo, userphone, orderid, stationProvince, gameCode, issueNumber, betdetail, 0);
    }


    private Map<String, Object> betOrder(String stationNo, String userphone, String orderid, String stationProvince, String gameCode, String issueNumber, String betdetail, int times) {

        if (times > TIMES) {
            throw new CommonException(ICommon.BET_ORDER_ERROR, PropertiesUtils.get(ICommon.BET_ORDER_ERROR));
        }

        SortedMap<String, Object> map = new TreeMap<String, Object>();
        map.put("stationno", stationNo);
        map.put("stationProvince", stationProvince);
        map.put("gameCode", gameCode);
        map.put("issueno", issueNumber);
        map.put("betdetail", betdetail);
        map.put("huiorderid", orderid);
        map.put("userphone", userphone);

        String transSerialNumber = UUIDUtil.getUUID();
        String result = doPost(transSerialNumber, map, PropertiesUtils.get("lottery_url"), PropertiesUtils.get("lottery_channelbet_url"));
        /**
         * {"result":0,"resultDesc":"投注成功","signature":null,"transData":null,"orderNo":"15089878021627453839","orderAmount":200,"orderTime":1508987802162,"orderStatus":0,"userBalance":35000}
         *
         * userBalance:账户剩余金额
         *
         */
        logger.info("bet response result={}", result);


        JSONObject jsonObject = JSON.parseObject(result);
        int resultCode = jsonObject.getInteger("result");
        if (resultCode == 0) {
            Map<String, Object> resultMap = new HashedMap(10);
            switch (PosLotOrderStatusEnum.findByValue(jsonObject.getInteger("orderStatus"))) {
                case PAID:
                    resultMap.put("orderStatus", OrderStatusEnum.PAID.getState());
                    break;
                case ALLTICKET:
                    resultMap.put("orderStatus", OrderStatusEnum.PAID.getState());
                    break;
                case PARTTICKET:
                    resultMap.put("orderStatus", OrderStatusEnum.PARTTICKET.getState());
                    break;
                case TICKETFAIL:
                    resultMap.put("orderStatus", OrderStatusEnum.FAILBILL.getState());
                    break;
            }

            resultMap.put("orderNo", jsonObject.getString("orderNo"));
            resultMap.put("orderAmount", jsonObject.getString("orderAmount"));
            resultMap.put("orderTime", jsonObject.getString("orderTime"));
            resultMap.put("userBalance", jsonObject.getString("userBalance"));
            resultMap.put("betNumber", jsonObject.getIntValue("betNumber"));
            resultMap.put("ticketList", JSONObject.parseArray(jsonObject.getString("ticketList"), Ticket.class));
            return resultMap;
        } else if (resultCode == ACCESSTOKEN_ERROR_CODE) {
            times += 1;
            return betOrder(stationNo, userphone, orderid, stationProvince, gameCode, issueNumber, betdetail, times);
        } else {
            throw new CommonException(resultCode, jsonObject.getString("resultDesc"));
        }

    }


    private Map<String, String> betOrder(String stationNo, String stationProvince, String gameCode, String issueNumber, List<BetRequest> betRequestList, int times) {

        if (times > TIMES) {
            throw new CommonException(ICommon.BET_ORDER_ERROR, PropertiesUtils.get(ICommon.BET_ORDER_ERROR));
        }

        Map<String, Object> map = new HashMap<String, Object>(10);
        map.put("stationNo", stationNo);
        map.put("stationProvince", stationProvince);
        String token = getChannelTokenStr(stationNo, stationProvince);
        logger.info("宝乐彩请求token:{}", token);
        map.put("channelToken", token);
        map.put("gameCode", gameCode);
        map.put("issueNumber", issueNumber);
        map.put("betRequestArray", JSONArray.parseArray(JSON.toJSONString(betRequestList)));
        String transSerialNumber = UUIDUtil.getUUID();
        String result = doPost(transSerialNumber, map, PropertiesUtils.get("lottery_url"), PropertiesUtils.get("lottery_channelbet_url"));
        /**
         * {"result":0,"resultDesc":"投注成功","signature":null,"transData":null,"orderNo":"15089878021627453839","orderAmount":200,"orderTime":1508987802162,"orderStatus":0,"userBalance":35000}
         *
         * userBalance:账户剩余金额
         *
         */
        logger.info("bet response result={}", result);
        JSONObject jsonObject = JSON.parseObject(result);
        int resultCode = jsonObject.getInteger("result");
        if (resultCode == 0) {
            Map<String, String> resultMap = new HashedMap(10);
            resultMap.put("orderNo", jsonObject.getString("orderNo"));
            resultMap.put("orderAmount", jsonObject.getString("orderAmount"));
            resultMap.put("orderTime", jsonObject.getString("orderTime"));
            resultMap.put("orderStatus", jsonObject.getString("orderStatus"));
            resultMap.put("userBalance", jsonObject.getString("userBalance"));
            return resultMap;
        } else if (resultCode == ACCESSTOKEN_ERROR_CODE) {
            times += 1;
            logger.info("宝乐彩token失效:{}", token);
            cacheManager.delete(String.format(CHANNELTOKEN_KEY, stationNo, stationProvince));
            return betOrder(stationNo, stationProvince, gameCode, issueNumber, betRequestList, times);
        } else {
            //删除缓存2017-11-13 清除缓存售期
            cacheManager.delete(String.format(SALE_DATE, stationProvince));
            throw new CommonException(resultCode, jsonObject.getString("resultDesc"));
        }

    }

    /**
     * 获取token
     *
     * @param stationno
     * @param stationprovince
     * @return
     */
    public String getChannelTokenStr(String stationno, String stationprovince) {

        /**
         * 获取缓存中的ChannelToken 操作令牌
         */

        return "ChannelToken";

//        Object channelToken = cacheManager.get(String.format(CHANNELTOKEN_KEY, stationno, stationprovince));
//        if (channelToken != null && StringUtils.isNotBlank(channelToken.toString())) {
//            return channelToken.toString();
//        }
//        String transSerialNumber = UUIDUtil.getUUID();
//        Map<String, String> map = new HashMap<String, String>(2);
//        map.put("stationNo", stationno);
//        map.put("stationProvince", stationprovince);
//
//        String result = doPost(transSerialNumber, map, PropertiesUtils.get("lottery_url"), PropertiesUtils.get("lottery_getchanneltoken_url"));
//        logger.info("getchanneltoken response result={}", result);
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        int resultCode = jsonObject.getInteger("result");
//        if (resultCode != 0) {
//            throw new CommonException(resultCode, jsonObject.getString("resultDesc"));
//        }
//        cacheManager.put(String.format(CHANNELTOKEN_KEY, stationno, stationprovince), jsonObject.getString("channelToken"), 24 * 60 * 60);
//        return jsonObject.getString("channelToken");
    }


    private <T> String doPost(String transSerialNumber, T data, String prefixUrl, String suffixUrl) {
        HttpUtil httpUtil = HttpUtil.getInstance();
        return httpUtil.doPost(transSerialNumber, String.format(prefixUrl, suffixUrl), data);
    }

}
