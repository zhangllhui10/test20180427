package com.hui10.app.model.main;

import java.util.List;

import com.hui10.app.model.lottery.LotteryOpenVo;
import com.hui10.app.model.lottery.LotteryPast;

/**
 * @ClassName: HomePage.java
 * @Description:
 * @author wengf
 * @date 2017年10月27日 下午2:20:58
 */
public class HomePage {
	
	/**
	 * 往期开奖
	 */
	private List<LotteryPast> lotteryPast;
	
	private List<LotteryOpenVo> lotteryOpenVos;
	
	/**
	 * 中奖订单
	 */
	private int prizeCount;

	public List<LotteryPast> getLotteryPast() {
		return lotteryPast;
	}

	public void setLotteryPast(List<LotteryPast> lotteryPast) {
		this.lotteryPast = lotteryPast;
	}

	public int getPrizeCount() {
		return prizeCount;
	}

	public void setPrizeCount(int prizeCount) {
		this.prizeCount = prizeCount;
	}

	public List<LotteryOpenVo> getLotteryOpenVos() {
		return lotteryOpenVos;
	}

	public void setLotteryOpenVos(List<LotteryOpenVo> lotteryOpenVos) {
		this.lotteryOpenVos = lotteryOpenVos;
	}
	
	

}
