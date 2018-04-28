package com.hui10.app.controller.lottery;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.lottery.HttpResult;
import com.hui10.app.common.lottery.LotteryStewardUtils;
import com.hui10.app.common.lottery.Md5Util;
import com.hui10.app.common.utils.UnionPayUtils;
import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.lottery.LotteryInfo;
import com.hui10.app.model.lottery.LotteryPast;
import com.hui10.app.service.loterry.LotteryInfoService;
import com.hui10.app.service.loterry.LotteryPastService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.model.common.Result;

/**
 * @ClassName: LotteryController.java
 * @Description:
 * @author zhangll
 * @date 2017年11月15日 上午11:16:30
 */
@Controller
public class LotteryController extends BaseController {
	@Autowired
	LotteryInfoService lotteryInfoService;
	
	@Autowired
	private LotteryPastService lotteryPastService;

	/**
	 * 调用方：前置系统 
	 * 根据彩票编号查询彩票信息：编号，名称，价格
	 */
	@RequestMapping(value = "/lottery/info/query", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<Map<String, Object>> queryLotteryInfo(@RequestHeader(name = "Authorization") String authorization, @RequestBody String paramsBody) {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>(com.hui10.common.constants.ICommon.SUCCESS,
				PropertiesUtils.get(com.hui10.common.constants.ICommon.SUCCESS));
		if (StringUtils.isEmpty(authorization) || StringUtils.isEmpty(paramsBody)) {
			return new Result<>(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR), false);
		}
		// 校验正确性
		UnionPayUtils.check(authorization, paramsBody);
		@SuppressWarnings("unchecked")
		Map<String,String> lotteryInfo = JSONObject.parseObject(paramsBody, Map.class);
		result.setResult(lotteryInfoService.queryLotteryInfoByCode(lotteryInfo));
		return result;
	}
	
	
	@RequestMapping(value = "/lottery/info/add", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> addLotteryinfo(LotteryInfo lotteryInfo,@RequestParam String token) {
		Result<Boolean> result = new Result<Boolean>();
		String username = checkAdminToken(token);
		lotteryInfo.setOperator(username);
		lotteryInfoService.addOrUpdateLotteryInfo(lotteryInfo,true);
		return result;
	}
	
	@RequestMapping(value = "/lottery/info/edit", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> editLotteryinfo(LotteryInfo lotteryInfo,@RequestParam String token) {
		Result<Boolean> result = new Result<Boolean>();
		String username = checkAdminToken(token);
		lotteryInfo.setOperator(username);
		lotteryInfoService.addOrUpdateLotteryInfo(lotteryInfo,false);
		return result;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/lottery/issue/issuenotify", method = RequestMethod.POST)
	public HttpResult<String> savePast(@RequestBody String body) {
		
		HttpResult<String> result = new HttpResult<>();
		result.setSignature(Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(result), Constants.LOTTERY_MD5_SUFFIX));
		
		JSONObject jsonObject = LotteryStewardUtils.checkBody(body);
		JSONObject transData = JSONObject.parseObject(jsonObject.getString("transData"));
		LotteryPast lotteryPast = new LotteryPast();
		String specialNumber = transData.getString("specialnumber");
		if(StringUtils.isEmpty(specialNumber)) {
			lotteryPast.setLotterynumber(transData.getString("opennumber"));
		}else{
			lotteryPast.setLotterynumber(transData.getString("opennumber")+"|"+specialNumber);
		}
		lotteryPast.setIssuenumber(transData.getString("issueno"));
		lotteryPast.setLotterytype(transData.getString("lotterycode"));
		lotteryPast.setMoneypool(transData.getString("prizepool"));
		lotteryPast.setLotterytime(new Date(transData.getLongValue("enddate")));
		lotteryPastService.savePastLottery(lotteryPast);
		return result;
	}

}