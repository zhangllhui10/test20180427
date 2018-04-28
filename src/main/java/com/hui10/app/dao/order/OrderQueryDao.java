package com.hui10.app.dao.order;

import com.hui10.app.model.lottery.LotteryPast;
import com.hui10.app.model.main.HomeLottery;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.order.Ticket;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2018年03月08日 11:47
 */
public interface OrderQueryDao {

    LotteryPast queryRecentLotteryInfo(@Param("lotterytype") String lotterytype);

    List<HomeLottery> queryOrderList(@Param("uid") String uid, @Param("type") String type, @Param("orderid") String orderid, @Param("sysdate") Date sysDate);

    LotteryOrder queryOrderInfoByOrderId(@Param("orderid") String orderId);

    List<Ticket> queryTicketListByOrderId(@Param("orderid") String orderId);

    String queryWaitPayOrderId(@Param("uid") String uid, @Param("orderid") String orderId);
}
