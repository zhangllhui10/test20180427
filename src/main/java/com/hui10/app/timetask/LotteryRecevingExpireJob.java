package com.hui10.app.timetask;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hui10.api.timedtask.ISchedulerJob;
import com.hui10.app.service.marketing.GivingRecordService;

/**
 * 定时任务 更新过期未领取的彩票
 * @author chiheng
 *
 */
@Component
public class LotteryRecevingExpireJob implements ISchedulerJob{
	
	private static final Logger logger = Logger.getLogger(LotteryRecevingExpireJob.class);
	
	@Autowired
	private GivingRecordService givingRecordService;
	
	@Override
	public void execute(JobExecutionContext arg0) {
		logger.debug("====================定时任务lotteryRecevingExpireJob开始===================");
		
		int count = givingRecordService.updateExpireTickets();
		
		logger.debug("====================定时任务lotteryRecevingExpireJob结束, 更新过期数"+count+"===================");
	}

}
