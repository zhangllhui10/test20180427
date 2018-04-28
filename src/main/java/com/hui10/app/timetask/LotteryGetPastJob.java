package com.hui10.app.timetask;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hui10.api.timedtask.ISchedulerJob;
import com.hui10.app.service.loterry.LotteryPastService;

/**
 * @ClassName: LotteryPastJob.java
 * @Description:
 * @author wengf
 * @date 2017年10月27日 上午10:31:04
 */
@Component
public class LotteryGetPastJob implements ISchedulerJob {
	
	@Autowired
	private LotteryPastService lotteryPastService;

	@Override
	public void execute(JobExecutionContext context) {

	}

}
