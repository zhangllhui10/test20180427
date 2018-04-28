package com.hui10.app.dao.order;

import com.hui10.app.model.order.LotteryOrderDetail;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-17 16:21
 **/
public interface LotteryOrderDetailDao {
    /**
     * 插入订单详情
     * @param lotteryOrderDetail
     */
    int insert(LotteryOrderDetail lotteryOrderDetail);

    /**
     * 更新宝乐彩订单号
     * @param lotteryOrderDetail
     * @return
     */
    int updateOrderNoByOrderId(LotteryOrderDetail lotteryOrderDetail);

    /**
     * 批量插入订单详情
     * @param lotteryOrderDetails
     */
    void insertBatch(List<LotteryOrderDetail> lotteryOrderDetails);

    /**
     * 订单ID 查询订单详情
     * @param orderid
     * @return
     */
    List<LotteryOrderDetail> queryOrderDetailByOrderId(String orderid);


    /**
     * 查询订单注数
     * @param lotteryOrderDetail
     * @return
     */
    int getLotteryOrderDetailCount(LotteryOrderDetail lotteryOrderDetail);

    /**
     * 批量更新订单详情
     * @param list
     * @return
     */
    int updateOrderDetailForBatch(List<LotteryOrderDetail> list);
}
