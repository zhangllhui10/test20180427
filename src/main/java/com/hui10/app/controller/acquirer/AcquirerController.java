package com.hui10.app.controller.acquirer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.acquirer.AcquirerInfo;
import com.hui10.app.service.acquirer.AcquirerInfoService;
import com.hui10.app.service.system.SystemService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.model.common.Result;


@Controller
public class AcquirerController extends BaseController{
	
	@Autowired
	private AcquirerInfoService acquirerInfoService;
	@Autowired
	private SystemService systemService;
	
	/**
	 * 调用方：前置系统
	 * 新增销售渠道入网申请
	 */
	@RequestMapping(value = "/acquirer/apply/add", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<String> createAcquirerApply(@RequestHeader(name = "Authorization") String authorization, @RequestBody String paramsBody) {
		Result<String> result = new Result<String>(com.hui10.common.constants.ICommon.SUCCESS, PropertiesUtils.get(com.hui10.common.constants.ICommon.SUCCESS));
		 if (StringUtils.isEmpty(authorization) || StringUtils.isEmpty(paramsBody)) {
	            return new Result<>(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR), false);
	        }
		 acquirerInfoService.createAcquirerApply(authorization, paramsBody);
		return result;
	}
	/*************** 收单机构 *******************/
	@RequestMapping(value="/acquirer/login",produces={ "application/json;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public Result<String> login(@RequestParam String acquirerno,@RequestParam String acquirername,@RequestParam String code,@RequestParam String identity){
		Result<String> result = new Result<String>(com.hui10.common.constants.ICommon.SUCCESS, PropertiesUtils.get(com.hui10.common.constants.ICommon.SUCCESS));
		systemService.checkCaptcha(identity, code);
		result.setResult(acquirerInfoService.login(acquirerno, acquirername));
		return result;
	}
	
	@RequestMapping(value="/acquirer/checktokenacq",produces={ "application/json;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public Result<JSONObject> checkTokenAcquirerInfo(@RequestParam String token){
		Result<JSONObject> result = new Result<JSONObject>(com.hui10.common.constants.ICommon.SUCCESS, PropertiesUtils.get(com.hui10.common.constants.ICommon.SUCCESS));
		AcquirerInfo info = acquirerInfoService.checkTokenAcquire(token);
		JSONObject object = new JSONObject();
		object.put("acquirerno", info.getAcquirerno());
		object.put("acquirername", info.getAcquirername());
		object.put("id", info.getId());
		result.setResult(object);
		return result;
	}
	
	@RequestMapping(value="/acquirer/logout",produces={ "application/json;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public Result<String> logout(@RequestParam String token){
		Result<String> result = new Result<String>(com.hui10.common.constants.ICommon.SUCCESS, PropertiesUtils.get(com.hui10.common.constants.ICommon.SUCCESS));
		acquirerInfoService.logout(token);
		return result;
	}
	
}
