package com.hui10.app.service.order.impl;

import com.hui10.app.dao.order.LotteryOrderDao;
import com.hui10.app.model.order.OrderInfoDto;
import com.hui10.app.service.order.PcOrderService;
import com.poslot.model.lottery.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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
@Service
public class PcOrderServiceImpl implements PcOrderService {

    @Autowired
    private LotteryOrderDao lotteryOrderDao;


    @Override
    public Integer queryOrderCount(String merchantno, Date begin, Date end, String source, String gamecode) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        end=calendar.getTime();
        return lotteryOrderDao.queryOrderCountByPc(merchantno,begin,end,source,gamecode);
    }

    @Override
    public List<OrderInfoDto> queryOrderList(String merchantno, Date begin, Date end, Integer pagesize, Integer pageno, String source, String gamecode) {

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        end=calendar.getTime();
        Integer pagestart=(pageno-1)*pagesize;

        return lotteryOrderDao.queryOrderListPc(merchantno,begin,end,source,gamecode,pagesize,pagestart);
    }

    @Override
    public Long queryOrderSumAmount(String merchantno, Date begin, Date end, String source, String gamecode) {

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        end=calendar.getTime();

        return lotteryOrderDao.queryOrderSumAmount(merchantno,begin,end,source,gamecode);
    }
}
