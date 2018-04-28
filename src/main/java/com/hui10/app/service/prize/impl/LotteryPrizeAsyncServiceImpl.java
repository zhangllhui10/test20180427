package com.hui10.app.service.prize.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.lottery.HttpResult;
import com.hui10.app.common.utils.UnionPayUtils;
import com.hui10.app.service.prize.LotteryPrizeAsyncService;
import com.hui10.app.service.prize.LotteryPrizeService;

/**
 * @ClassName: LotteryPrizeAsyncServiceImpl.java
 * @Description:
 * @author zhangll
 * @date 2017年11月29日 下午2:39:38
 */
@Service
public class LotteryPrizeAsyncServiceImpl implements LotteryPrizeAsyncService {
	@Autowired
	private LotteryPrizeService lotteryPrizeService;

	@Override
	@Async
	public HttpResult<String> winPrizeNotify(JSONObject transData) {
		Map<String, Object> map = UnionPayUtils.downloadedFile(transData);

		return lotteryPrizeService.dealWinPrizeFile(map);
	}

	@Override
	@Async
	public HttpResult<String> winPrizeNotifyMarket(JSONObject transData) {
		Map<String, Object> map = UnionPayUtils.downloadedFileMarket(transData);
		return lotteryPrizeService.dealWinPrizeFileMarket(map);
	}

}
