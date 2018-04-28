package com.hui10.app.service.prize;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.lottery.HttpResult;

/**
 * @ClassName: LotteryPrizeAsyncService.java
 * @Description:
 * @author zhangll
 * @date 2017年11月29日 下午2:26:26
 */
public interface LotteryPrizeAsyncService {
	
	
	public HttpResult<String> winPrizeNotify(JSONObject transData);
	
	public HttpResult<String> winPrizeNotifyMarket(JSONObject transData);
}
