package com.hui10.pc.controller.merchant;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.web.BasePCController;
import com.hui10.app.model.merchant.MerchantInfo;
import com.hui10.app.service.system.SystemService;
import com.hui10.model.common.Result;
import com.hui10.pc.service.merchant.MerchantPCService;

/**
 * @ClassName: MerchantController.java
 * @Description:
 * @author zhangll
 * @date 2018年4月17日 下午7:28:16
 */
@RestController
public class MerchantPcController extends BasePCController {
	@Autowired
	private MerchantPCService merchantPCService;

	@Autowired
	private SystemService systemService;

	@RequestMapping(value = "/pc/merchant/login/send/sms")
	public Result<Boolean> loginSendSms(@RequestParam String phone, @RequestParam String identity, @RequestParam String code) {
		validateStringParameterNotBlank(phone);
		systemService.checkCaptcha(identity, code);
		Result<Boolean> result = new Result<Boolean>();
		merchantPCService.loginSendSms(phone);
		return result;
	}

	@RequestMapping(value = "/pc/merchant/login")
	public Result<JSONObject> login(@RequestParam String phone, @RequestParam String smscode) {
		Result<JSONObject> result = new Result<JSONObject>();
		validateStringParameterNotBlank(phone, smscode);
		result.setResult(merchantPCService.login(phone, smscode));
		return result;
	}

	@RequestMapping(value = "/pc/merchant/querylist", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<JSONObject> queryMerchantList(@RequestParam String token) {
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject objecct_result = new JSONObject();
		List<MerchantInfo> list = this.checkMerchantToken_list(token);
		objecct_result.put("list", list);
		if (!list.isEmpty()) {
			String phone = list.get(0).getContactnumber();
			objecct_result.put("phone", phone);
		}
		result.setResult(objecct_result);
		return result;
	}

	@RequestMapping(value = "/pc/merchant/logout")
	public Result<JSONObject> logout(@RequestParam String token) {
		Result<JSONObject> result = new Result<JSONObject>();
		List<MerchantInfo> list = checkMerchantToken_list(token);
		merchantPCService.logout(list);
		return result;
	}

}
