package com.hui10.app.service.order;

import com.hui10.app.model.order.LotteryOrder;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-22 14:54
 **/
public interface OrderAsyncService {
    /**
     * 异步通知回调
     * @param lotteryOrder
     */
    void uplNotify(LotteryOrder lotteryOrder);

    void userNotify(String userphone,String orderId);

}
