package com.hui10.app.controller.prize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.exception.ResultException;
import com.hui10.app.common.lottery.HttpResult;
import com.hui10.app.common.lottery.LotteryStewardUtils;
import com.hui10.app.common.lottery.Md5Util;
import com.hui10.app.model.lottery.LotteryInfo;
import com.hui10.app.service.loterry.LotteryInfoService;
import com.hui10.app.service.prize.LotteryPrizeAsyncService;
import com.hui10.common.web.BaseController;

/**
 * @ClassName: PrizeController.java
 * @Description:彩票开奖
 * @author zhangll
 * @date 2017年10月18日 上午10:10:41
 */
@Controller
public class LotteryPrizeController extends BaseController {
	@Autowired
	private LotteryPrizeAsyncService lotteryPrizeAsyncService;
	@Autowired
	private LotteryInfoService lotteryInfoService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "api/v1/lotterychannel/opennotify", "api/v1/lotteryChannel/openNotify" }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public HttpResult<String> winPrizeNotify(@RequestBody String body) {
		HttpResult<String> result = new HttpResult<>();
		try {

			JSONObject jsonObject = LotteryStewardUtils.checkBody(body);
			JSONObject transData = JSONObject.parseObject(jsonObject.getString("transData"));
			LotteryInfo info = lotteryInfoService.lotteryInfoInsert(transData);
			if (null != info) {
				lotteryPrizeAsyncService.winPrizeNotify(transData);
				result.setSignature(Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(result), Constants.LOTTERY_MD5_SUFFIX));
				return result;
			} else {
				result.setSignature(Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(result), Constants.LOTTERY_MD5_SUFFIX));
				return result;
			}

		} catch (ResultException resultException) {
			return resultException.getHttpResult();
		} catch (Exception e) {
			return getHttpResult();
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/api/v1/lotteryChannel/marketprized", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public HttpResult<String> winPrizeNotifyMarketprized(@RequestBody String body) {
		HttpResult<String> result = new HttpResult<>();
		try {

			JSONObject jsonObject = LotteryStewardUtils.checkBody(body);
			JSONObject transData = JSONObject.parseObject(jsonObject.getString("transData"));
			LotteryInfo info = lotteryInfoService.lotteryInfoInsertMarket(transData);
			if (null != info) {
				lotteryPrizeAsyncService.winPrizeNotifyMarket(transData);
				result.setSignature(Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(result), Constants.LOTTERY_MD5_SUFFIX));
				return result;
			} else {
				result.setSignature(Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(result), Constants.LOTTERY_MD5_SUFFIX));
				return result;
			}

		} catch (ResultException resultException) {
			return resultException.getHttpResult();
		} catch (Exception e) {
			return getHttpResult();
		}
	}

	private HttpResult<String> getHttpResult() {
		HttpResult<String> httpResult = new HttpResult<String>();
		httpResult.setResult("999");
		httpResult.setResultDesc("未知错误");
		httpResult.setTransData("null");
		httpResult.setSignature(Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(httpResult), Constants.LOTTERY_MD5_SUFFIX));
		return httpResult;
	}
}
