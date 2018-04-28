package com.hui10.app.timetask;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hui10.api.timedtask.ISchedulerJob;
import com.hui10.app.service.order.HuiOrderBalanceService;

@Component
public class HuiOrderDailyBalanceJob implements ISchedulerJob{
	
	private static final Logger logger = Logger.getLogger(HuiOrderDailyBalanceJob.class);

	@Autowired
	private HuiOrderBalanceService huiOrderBalanceService;
	@Override
	public void execute(JobExecutionContext context) {
		logger.info("==============定时任务获取宝乐彩hui10对账文件开始=========");
		huiOrderBalanceService.accountBalance();
		logger.info("==============定时任务获取宝乐彩hui10对账文件结束=========");
	}

}
