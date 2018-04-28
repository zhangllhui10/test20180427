package com.hui10.app.dao.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hui10.app.model.order.OrderInfoDto;
import com.poslot.model.lottery.Order;
import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.order.LotteryOrder;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-17 16:21
 **/
public interface LotteryOrderDao {
    /**
     * 插入订单
     * @param lotteryOrder
     * @return
     */
    int insert(LotteryOrder lotteryOrder);
    /**
     * 更新订单
     * @param lotteryOrder
     * @return
     */
    int update(LotteryOrder lotteryOrder);
    
    /**
     * 查询订单
     * @param orderid
     * @return
     */
    LotteryOrder queryByOrderId(String orderid);
    
    LotteryOrder queryByOrderIdUid(@Param("uid")String uid, @Param("orderid")String orderid);
    
    List<LotteryOrder> queryOrders(@Param("uid")String uid, @Param("orderid")String orderid, @Param("paystatus")Integer paystatus, @Param("lotterystatus")String lotterystatus, @Param("pagesize")Integer pagesize);

    /**
     * 查询前一天订单
     * @param preDate
     * @return
     */
    List<LotteryOrder> queryPreDayLotteryOrder(String preDate);
    
    int updateOrderStatusByPoslotNotify(@Param("issuenumber")String issuenumber, @Param("lotterycode")String lotterycode, 
    		@Param("resource")String resource, @Param("status")Integer status);

    /**
     * pc端获取订单总数
     * @param merchantno
     * @param begin
     * @param end
     * @param source
     * @param gamecode
     * @return
     */
    Integer queryOrderCountByPc(@Param("merchantno") String merchantno,@Param("begin") Date begin,@Param("end") Date end,@Param("source") String source, @Param("gamecode")String gamecode);

    /**
     * pc获取订单列表
     * @param merchantno
     * @param begin
     * @param end
     * @param source
     * @param gamecode
     * @param pagesize
     * @param pagestart
     * @return
     */
    List<OrderInfoDto> queryOrderListPc(@Param("merchantno") String merchantno, @Param("begin") Date begin, @Param("end") Date end, @Param("source") String source, @Param("gamecode")String gamecode, @Param("pagesize") Integer pagesize, @Param("pagestart") Integer pagestart);

    /**
     * 获取统计金额
     * @param merchantno
     * @param begin
     * @param end
     * @param source
     * @param gamecode
     * @return
     */
    Long queryOrderSumAmount(@Param("merchantno") String merchantno,@Param("begin") Date begin,@Param("end") Date end,@Param("source") String source, @Param("gamecode")String gamecode);
}

