package com.hui10.app.controller.order;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.order.OrderAccountBalanceDetailVo;
import com.hui10.app.model.order.OrderAccountBalanceInfoVo;
import com.hui10.app.service.order.HuiOrderBalanceService;
import com.hui10.model.common.Result;

@Controller
public class HuiOrderBalanceController extends BaseController{
	
	@Autowired
	private HuiOrderBalanceService huiOrderBalanceService;
	
	/**
	 * 查询对账信息
	 */
	@RequestMapping(value = "/v*/order/account/balance", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<OrderAccountBalanceInfoVo> queryAccountBalanceInfo(@RequestParam String token, String status, 
			String starttime, String endtime, Integer pageno, Integer pagesize) {
		this.checkAdminToken(token);
		Result<OrderAccountBalanceInfoVo> result = new Result<OrderAccountBalanceInfoVo>();
		result.setResult(huiOrderBalanceService.queryAccountBalanceInfo(status, starttime, endtime, pageno, pagesize));
		return result;
	}
	
	/**
	 * 查询对账信息详情
	 */
	@RequestMapping(value = "/v*/order/account/balance/detail", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<OrderAccountBalanceDetailVo> queryAccountBalanceDetail(@RequestParam String token, @RequestParam String accountid,
			Integer pageno, Integer pagesize) {
		this.checkAdminToken(token);
		Result<OrderAccountBalanceDetailVo> result = new Result<OrderAccountBalanceDetailVo>();
		result.setResult(huiOrderBalanceService.queryAccountBalanceDetails(accountid, pageno, pagesize));
		return result;
	}
	
	/**
	 * 查询对账信息详情
	 * 
	 */
	@RequestMapping(value = "/v*/order/account/balance/download", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView downloadFile(@RequestParam String token, @RequestParam String accountid, HttpServletResponse response) {
		this.checkAdminToken(token);
		huiOrderBalanceService.downloadFile(accountid, response);
		return null;
	}
}
