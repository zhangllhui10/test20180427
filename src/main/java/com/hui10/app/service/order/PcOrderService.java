package com.hui10.app.service.order;

import com.hui10.app.model.order.OrderInfoDto;
import com.poslot.model.lottery.Order;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @email mudouyu@aliyun.com
 * @create 2018-04-17 15:17
 **/
public interface PcOrderService {
    Integer queryOrderCount(String merchantno, Date begin, Date end, String source, String gamecode);

    List<OrderInfoDto> queryOrderList(String merchantno, Date begin, Date end, Integer pagesize, Integer pageno, String source, String gamecode);

    Long queryOrderSumAmount(String merchantno, Date begin, Date end, String source, String gamecode);
}
