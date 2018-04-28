package com.hui10.app.service.loterry;

import java.util.List;

import com.hui10.app.model.lottery.LotteryPast;

/**
 * @ClassName: LoterryPastService.java
 * @Description:
 * @author wengf
 * @date 2017年10月27日 上午10:24:52
 */
public interface LotteryPastService {
	
	public int savePastLottery(LotteryPast lotteryPast);
	
	public List<LotteryPast> queryPast(LotteryPast lotteryPast);
	
	public int savePastLottery();

}
