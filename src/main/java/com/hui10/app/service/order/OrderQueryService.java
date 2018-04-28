package com.hui10.app.service.order;

import com.alibaba.fastjson.JSONObject;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2018年03月08日 11:23
 */
public interface OrderQueryService {

    /**
     * 订单列表头信息
     * @param lotteryType
     * @return
     */
    JSONObject getSaleIssueLottery(String lotteryType);

    /**
     * 订单列表
     * @param uid
     * @param type
     * @param orderId
     * @return
     */
    JSONObject queryOrderList(String uid, String type, String orderId);

    /**
     * 订单详情
     * @param uid
     * @param orderId
     * @return
     */
    JSONObject queryOrderDetail(String uid, String orderId);

    /**
     * 订单状态
     * @param uid
     * @param orderId
     * @return
     */
    String queryOrderStatus(String uid, String orderId);

    /**
     * 订单信息
     * @param uid
     * @param orderId
     * @return
     */
    JSONObject queryOrderInfo(String uid, String orderId);

    /**
     * 打印大奖彩票
     * @param uid
     * @param orderId
     * @param wifimac
     * @return
     */
    boolean printBigPrizeLottery(String uid, String orderId, String wifimac);
}
