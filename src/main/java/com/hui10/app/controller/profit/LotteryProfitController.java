package com.hui10.app.controller.profit;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hui10.app.common.web.BaseController;
import com.hui10.app.service.profit.LotteryProfitService;

/**
 * @ClassName: LotteryProfitController.java
 * @Description:
 * @author zhangll
 * @date 2017年10月22日 下午12:31:49
 */
@Controller
public class LotteryProfitController extends BaseController {
	
	@Autowired
	private LotteryProfitService lotteryProfitService;
	/**
	 * 调用方：支付平台
	 * @param body
	 * @return
	 * @user zhangll
	 * @date 2017年10月24日 下午4:25:33
	 */
	@RequestMapping(value = { "profit/queryprofitrate" }, produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> queryProfitRate(@RequestBody String body) {
		return lotteryProfitService.queryProfitRate(body);
	}
	
	/**
	 * 调用方：前置系统
	 * @return
	 * @user zhangll
	 * @date 2017年10月24日 下午5:03:49
	 */
	public  String checkNotify(){
		
		return null;
	}
}
