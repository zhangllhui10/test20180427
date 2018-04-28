package com.hui10.app.timetask;

import com.hui10.api.timedtask.ISchedulerJob;
import com.hui10.app.service.order.LotteryOrderSerice;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-31 17:27
 **/
@Component
public class LotteryOrderDailyBalanceJob implements ISchedulerJob {
    @Autowired
    private LotteryOrderSerice lotteryOrderSerice;

    private static Logger LOG= LoggerFactory.getLogger(LotteryOrderDailyBalanceJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LOG.info("对账文件生成");
        lotteryOrderSerice.createLotteryOrderAccount();
        LOG.info("对账文件结束");

    }
}
