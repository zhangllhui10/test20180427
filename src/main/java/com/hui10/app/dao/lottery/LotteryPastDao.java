package com.hui10.app.dao.lottery;

import java.util.List;

import com.hui10.app.model.lottery.LotteryPast;

/**
 * @ClassName: LotteryPastDao.java
 * @Description:
 * @author wengf
 * @date 2017年10月26日 上午11:34:59
 */
public interface LotteryPastDao {
	
	public List<LotteryPast> queryPast(LotteryPast past);
	
	public int insertLotterPast(LotteryPast past);

}
