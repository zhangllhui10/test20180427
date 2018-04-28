package com.hui10.app.controller.marketing;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.lottery.HttpResult;
import com.hui10.app.common.lottery.LotteryStewardUtils;
import com.hui10.app.common.lottery.Md5Util;
import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.marketing.LotteryGivingRecordInfo;
import com.hui10.app.model.marketing.LotteryReceiveRecordVo;
import com.hui10.app.service.marketing.ReceiveLotteryService;
import com.hui10.model.common.Result;

/**
 * @ClassName: ReceiveLotteryController.java
 * @Description:
 * @author huangrui
 * @date 2018年3月8日 10:16:09
 */
@Controller
public class ReceiveLotteryController extends BaseController {
	
	@Autowired
	private ReceiveLotteryService receiveLotteryService;
	
	/**
     * 领取彩票
     * @param id 增彩记录ID
     */
	@RequestMapping(value="/v*/marketing/lottery/receive",method=RequestMethod.POST,produces={ "application/json;charset=UTF-8" })
	@ResponseBody
	public Result<String> receiveLottery(@RequestParam String token, @RequestParam String id){
		String uid = checkHuicardUserToken(token);
		Result<String> result = new Result<String>();
		result.setResult(receiveLotteryService.receiveLottery(id, uid));
		return result;
	}
	
	/**
	 * 未领取的彩票
	 * @param token
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/v*/marketing/lottery/notreceived",method=RequestMethod.POST,produces={ "application/json;charset=UTF-8" })
	@ResponseBody
	public Result<List<LotteryGivingRecordInfo>> findNotReceivedLotteries(@RequestParam String token){
		String uid = checkHuicardUserToken(token);
		Result<List<LotteryGivingRecordInfo>> result = new Result<List<LotteryGivingRecordInfo>>();
		result.setResult(receiveLotteryService.findNotReceivedLotteries(uid));
		return result;
	} 
	
	@RequestMapping(value="/v*/marketing/lottery/situation",method=RequestMethod.POST,produces={ "application/json;charset=UTF-8" })
	@ResponseBody
	public Result<Map<String, Object>> findPoolParams(){
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		result.setResult(receiveLotteryService.findMarketingPoolAndCarousel());
		return result;
	} 
	
	/**
	 * 查询已领取的彩票
	 */
	@RequestMapping(value="/v*/marketing/lottery/received",method=RequestMethod.POST,produces={ "application/json;charset=UTF-8" })
	@ResponseBody
	public Result<LotteryReceiveRecordVo> findReceivedLotteries(@RequestParam String token, Integer pageno, Integer pagesize, String winstatus){
		String uid = checkHuicardUserToken(token);
		Result<LotteryReceiveRecordVo> result = new Result<LotteryReceiveRecordVo>();
		result.setResult(receiveLotteryService.findReceivedLotteries(uid, pageno, pagesize, winstatus));
		return result;
	}
	
	/**
	 * 调用方：新宝乐彩
	 * 更新投注订单状态
	 */
	@RequestMapping(value="/api/v*/bet/result/notify",method=RequestMethod.POST,produces={ "application/json;charset=UTF-8" })
	@ResponseBody
	public HttpResult<String> updateOrderStatus(@RequestBody String body){
		HttpResult<String> result = new HttpResult<>();
		JSONObject jsonObject = LotteryStewardUtils.checkBody(body);
		JSONObject transData = JSONObject.parseObject(jsonObject.getString("transData"));
		receiveLotteryService.updateOrderStatus(transData);
		result.setSignature(Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(result), Constants.LOTTERY_MD5_SUFFIX));
		return result;
	}
}
