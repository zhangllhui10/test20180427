package com.hui10.app.timetask;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hui10.api.timedtask.ISchedulerJob;
import com.hui10.app.service.user.WithDrawService;

/**
 * @ClassName: LotteryOrderJob.java
 * @Description:
 * @author wengf
 * @date 2017年10月25日 下午4:15:02
 */
@Component
public class LotteryGetPrizeJob implements ISchedulerJob {
	
	@Autowired
	private WithDrawService withDrawService;

	@Override
	public void execute(JobExecutionContext context) {
		
		withDrawService.timetaskQueryExtractStatus();
	}

}
