package com.hui10.app.service.order;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.lottery.dto.LotterySaleDto;
import com.hui10.app.model.order.LotteryOrder;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-17 17:47
 **/
public interface LotteryOrderSerice {
    /**
     * 彩票下单接口
     *
     * @param authorization
     * @param paramsBody
     * @return
     */
    Map<String, Object> pickLotteryOrder(String authorization, String paramsBody);

    /**
     * 查找一条
     *
     * @param orderid
     * @return
     * @user wengf
     * @date 2017年10月18日 下午4:02:47
     */
    public LotteryOrder selectByOrderid(String orderid);


    /**
     * 订单投注
     *
     * @param authorization
     * @param paramsBody
     * @return
     */
    Map<String, Object> betOrder(String authorization, String paramsBody);

    /**
     * 完成订单查询
     *
     * @param authorization
     * @param paramsBody
     * @return
     */
    Map<String, Object> queryCompletionNotify(String authorization, String paramsBody);

    /**
     * App 下注订单
     *
     * @param issuenumber
     * @param gamecode
     * @param betrequestarray
     * @return
     */
    Map<String, Object> generateOrderByApp(String issuenumber, String gamecode, String betrequestarray, String uid);

    /**
     *
     * @param issuenumber
     * @param gamecode
     * @param betrequestarray
     * @param uid
     * @param source 订单来源
     * @return
     */
    Map<String, Object> generateOrder(String issuenumber, String gamecode, String betrequestarray, String uid, int source);

    /**
     * App扫描订单
     *
     * @param authorization
     * @param paramsBody
     * @return
     */
    Map<String, Object> checkOrderInfo(String authorization, String paramsBody);

    /**
     * 取消订单
     *
     * @param authorization
     * @param paramsBody
     * @return
     */
    Map<String, Object> cancelOrder(String authorization, String paramsBody);

    /**
     * 查询销售同期
     *
     * @return
     */
    List<LotterySaleDto> queryOnSaleIssue(String authorization, String params);

    /**
     * 查询单个订单投递是否成功
     *
     * @param orderid
     * @return
     */
    Map<String, Object> channelOrderResult(String orderid);

    /**
     * 查询当前在售彩票期
     *
     * @param gamecodes 多个彩种英文逗号分隔
     * @return
     */
    List<LotterySaleDto> queryOnSaleIssueByApp(String gamecodes);

    /**
     * 获取前一天的订单信息
     *
     * @return
     */
    List<LotteryOrder> queryPreDayLotteryOrder();

    /**
     * 生成对账单
     */
    void createLotteryOrderAccount();
    
    /**
     * 更新订单状态
     */ 
    void updateOrderStatus(JSONObject transData);

    /**
     * 同号投注下一期
     * @param uid
     * @param orderId
     * @return
     */
    Map<String, Object> sameNumberBetting(String uid, String orderId);
}
