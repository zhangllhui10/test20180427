package com.hui10.app.service.loterry.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hui10.app.dao.lottery.LotteryPastDao;
import com.hui10.app.model.lottery.LotteryPast;
import com.hui10.app.service.loterry.LotteryPastService;
import com.hui10.common.utils.uuid.UUIDUtil;

/**
 * @ClassName: LotteryPastServiceImpl.java
 * @Description:
 * @author wengf
 * @date 2017年10月27日 上午10:26:37
 */
@Service
public class LotteryPastServiceImpl implements LotteryPastService{
	
	@Autowired
	private LotteryPastDao lotteryPastDao;

	@Override
	public int savePastLottery(LotteryPast lotteryPast) {
		lotteryPast.setCreatetime(new Date());
		lotteryPast.setId(UUIDUtil.getUUID());
		return lotteryPastDao.insertLotterPast(lotteryPast);
	}

	@Override
	public List<LotteryPast> queryPast(LotteryPast lotteryPast) {
		
		return lotteryPastDao.queryPast(lotteryPast);
	}

	@Override
	public int savePastLottery() {
		return 1;
	}

}
