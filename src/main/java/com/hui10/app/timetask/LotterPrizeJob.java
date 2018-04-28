package com.hui10.app.timetask;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hui10.api.timedtask.ISchedulerJob;
import com.hui10.app.service.prize.LotteryPrizeService;

/**
 * @ClassName: LotterPrizeJob.java
 * @Description:
 * @author zhangll
 * @date 2017年11月10日 下午3:34:50
 */
@Component
public class LotterPrizeJob implements ISchedulerJob {
	@Autowired
	private LotteryPrizeService lotteryPrizeService;
	 private static Logger logger= LoggerFactory.getLogger(LotterPrizeJob.class);
	@Override
	public void execute(JobExecutionContext context) {
		logger.debug("*********start download file**************");
		lotteryPrizeService.winPrizeNotifyJob();
	}

}
