package com.hui10.app.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.model.user.UserInfo;
import com.hui10.app.model.user.UserSimpleInfo;
import com.hui10.app.service.system.SystemService;
import com.hui10.app.service.user.UserInfoService;
import com.hui10.common.constants.ICommon;
import com.hui10.common.sso.SsoUtil;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.web.BaseController;
import com.hui10.enums.user.UserRegisterEnum;
import com.hui10.model.common.Result;

/**
 * @ClassName: UserInfoController.java
 * @Description:
 * @author zhangll
 * @date 2017年10月16日 下午6:33:40
 */
@Controller
public class UserInfoController extends BaseController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private SystemService systemService;

	@ResponseBody
	@RequestMapping(value = "/v*/user/registersms", method = RequestMethod.POST)
	public Result<String> registerSms(@RequestParam String phone, @RequestParam String identity, @RequestParam String code) {
		phone = phone.trim();
		Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		// 将短信发送到指定的手机
		systemService.checkCaptcha(identity, code);
		userInfoService.registersms(phone);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/v*/user/register", method = RequestMethod.POST)
	public Result<String> register(@RequestParam String smscode, @RequestParam String phone, @RequestParam String password, String inviter, String invitesrc,
			String inviteip, String regtype, String ip) {
		Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		validateStringParameterNotBlank(smscode, phone, password);
		Map<String, String> params = new HashMap<String, String>();
		params.put("regip", SsoUtil.IP);
		params.put("regposition", "北京");
		params.put("regtype", UserRegisterEnum.PHONE.getState());
		String accessToken = userInfoService.register(phone, password, smscode, inviter, invitesrc, inviteip, params);
		result.setResult(accessToken);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/v*/user/login", method = RequestMethod.POST)
	public Result<String> login(@RequestParam String phone, @RequestParam String password) {
		Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		String token = userInfoService.login(phone, password);
		result.setResult(token);
		return result;

	}

	@ResponseBody
	@RequestMapping(value = "/v*/user/logout", method = RequestMethod.POST)
	public Result<Boolean> logout(@RequestParam String token) {
		Result<Boolean> result = new Result<Boolean>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		userInfoService.logout(token);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/v*/user/password/modify/sendsms", method = RequestMethod.POST)
	public Result<Boolean> modifyPasswordSendSms(@RequestParam String token, @RequestParam String phone, @RequestParam String identity,
			@RequestParam String code) {
		Result<Boolean> result = new Result<Boolean>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		systemService.checkCaptcha(identity, code);
		String userInfo = checkHuicardUserTokenGetInfo(token);
		userInfoService.modifyPasswordSendSms(userInfo, phone);
		return result;
	}

	
	@RequestMapping(value = "/v*/user/password/modify/checkoldpwd", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> modifyPasswordCheckOldPwd(@RequestParam String token,@RequestParam String oldpassword) {
		Result<Boolean> r = new Result<Boolean>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		validateStringParameterNotBlank(oldpassword);
		String userinfo = this.checkHuicardUserTokenGetInfo(token);
		userInfoService.modifyPasswordCheckOldPwd(userinfo, oldpassword);
		return r;
	}
	
	
	@RequestMapping(value = "/v*/user/password/modifypwd", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> modifyPassword(@RequestParam String token, @RequestParam String phone, 
			@RequestParam String newpassword, @RequestParam String smscode) {
		Result<Boolean> r = new Result<Boolean>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		validateStringParameterNotBlank(phone, newpassword, smscode);
		String userinfo = this.checkHuicardUserTokenGetInfo(token);
		userInfoService.modifyPassword(token, userinfo,newpassword, smscode, phone);
		return r;
	}
	
	

	@RequestMapping(value = "/v*/user/password/forget/sendsms", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<String> forgetPasswordSendSms(@RequestParam String phone, @RequestParam String identity, @RequestParam String code) {
		Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		// 将短信发送到指定的手机
		systemService.checkCaptcha(identity, code);
		userInfoService.forgetPasswordSendSms(phone);
		return result;
	}

	@RequestMapping(value = "/v*/user/password/forget", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> forgetPassword(@RequestParam String phone, @RequestParam String smscode, @RequestParam String newpassword) {
		Result<Boolean> result = new Result<Boolean>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		validateStringParameterNotBlank(phone,smscode,newpassword);
		userInfoService.forgetPassword(phone, newpassword, smscode);
		return result;
	}

	@RequestMapping(value = "/v*/user/userinfo", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<UserSimpleInfo> queryUserInfo(@RequestParam String token) {
		Result<UserSimpleInfo> result = new Result<UserSimpleInfo>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		String uid = checkHuicardUserToken(token);
		result.setResult(userInfoService.queryUserSimpleInfo(uid));
		return result;
	}
	
	@RequestMapping(value = "/v*/user/update/userinfo", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> updateUserInfo(@RequestParam String token,UserSimpleInfo usinfo) {
		Result<Boolean> result = new Result<Boolean>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		String uid = checkHuicardUserToken(token);
		usinfo.setUid(uid);
		UserInfo user  = new UserInfo();
		BeanUtils.copyProperties(usinfo, user);
		userInfoService.updateUserInfo(user);
		return result;
	}

	@RequestMapping(value = "checktoken_test", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<String> checkToken_test(String token) {
		Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		result.setResult(checkHuicardUserTokenGetInfo(token));
		return result;
	}
	
	/**
	 * 动态验证码登录之前发验证码
	 * @param phone
	 * @param identity
	 * @param code
	 * @return
	 * @user zhangll
	 * @date 2017年12月4日 下午5:44:57
	 */
	@ResponseBody
	@RequestMapping(value = "/v*/user/login/sendsms", method = RequestMethod.POST)
	public Result<String> loginSendSms(@RequestParam String phone, @RequestParam String identity, @RequestParam String code) {
		phone = phone.trim();
		Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		systemService.checkCaptcha(identity, code);
		// 将短信发送到指定的手机
		userInfoService.loginSendSms(phone);
		return result;
	}
	/**
	 * 动态验证码登录
	 * @param phone
	 * @param smscode
	 * @return
	 * @user zhangll
	 * @date 2017年12月4日 下午5:44:57
	 */
	@ResponseBody
	@RequestMapping(value = "/v*/user/login/smscode", method = RequestMethod.POST)
	public Result<JSONObject> loginDynamiccode(@RequestParam String phone, @RequestParam String smscode) {
		Result<JSONObject> result = new Result<JSONObject>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		validateStringParameterNotBlank(phone,smscode);
		Map<String, String> params = new HashMap<String, String>();
		postSsoParams(params);	
		JSONObject token = userInfoService.login(phone, smscode,params);
		result.setResult(token);
		return result;

	}
	@ResponseBody
	@RequestMapping(value = "/v*/user/set/password/send/sms", method = RequestMethod.POST)
	public Result<Boolean> setPasswordSendSms(@RequestParam(required=true) String token,@RequestParam(required=true)String phone,@RequestParam String identity, @RequestParam String code){
		Result<Boolean> result  = new Result<Boolean>();
		String userInfo = checkHuicardUserTokenGetInfo(token);
		systemService.checkCaptcha(identity, code);
		userInfoService.setPasswordSendSms(userInfo, phone);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/v*/user/set/password", method = RequestMethod.POST)
	public Result<Boolean> setPassword(@RequestParam(required=true) String token,@RequestParam(required=true) String password,@RequestParam(required=true)String smscode){
		Result<Boolean> result  = new Result<Boolean>();
		String userinfo = checkHuicardUserTokenGetInfo(token);
		userInfoService.setPassword(userinfo,token, password,smscode);
		return result;
	}
	private void postSsoParams(Map<String, String> params) {
		params.put("regip", SsoUtil.IP);
		params.put("regposition", "北京");
		params.put("regtype", UserRegisterEnum.PHONE.getState());
	}
	
	@ResponseBody
	@RequestMapping(value = "/v*/user/change/phone/check/user", method = RequestMethod.POST)
	public Result<Boolean> changePhoneCheckUser(@RequestParam String token,String password){
		Result<Boolean> result = new Result<Boolean>(ICommon.SUCCESS,PropertiesUtils.get(ICommon.SUCCESS));
		String userinfo = this.checkHuicardUserTokenGetInfo(token);
		userInfoService.checkPassword(userinfo, password);
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/v*/user/change/phone/unbind/sendsms", method = RequestMethod.POST)
	public Result<Boolean> unbindPhoneSendSms(@RequestParam  String token,@RequestParam String phone, @RequestParam String identity, @RequestParam String code){
		Result<Boolean> result = new Result<Boolean>(ICommon.SUCCESS,PropertiesUtils.get(ICommon.SUCCESS));
		String userinfo = this.checkHuicardUserTokenGetInfo(token);
		systemService.checkCaptcha(identity, code);
		userInfoService.unbindPhoneSendSms(userinfo, phone);
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/v*/user/change/phone/unbind", method = RequestMethod.POST)
	public Result<String> unbindPhone(@RequestParam String token,String phone,String smscode){
		Result<String> result = new Result<String>(ICommon.SUCCESS,PropertiesUtils.get(ICommon.SUCCESS));
		String userinfo = this.checkHuicardUserTokenGetInfo(token);
		result.setResult(userInfoService.unbindPhone(userinfo, phone, smscode));
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/v*/user/change/phone/bind/sendsms", method = RequestMethod.POST)
	public Result<Boolean> bindPhoneSendSms(@RequestParam String token,String phone, @RequestParam String identity, @RequestParam String code){
		Result<Boolean> result = new Result<Boolean>(ICommon.SUCCESS,PropertiesUtils.get(ICommon.SUCCESS));
		systemService.checkCaptcha(identity, code);
		this.checkHuicardUserTokenGetInfo(token);
		userInfoService.bindPhoneSendSms(null, phone);
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/v*/user/change/phone/bind", method = RequestMethod.POST)
	public Result<Boolean> bindPhone(String token,String phone,@RequestParam String smscode,@RequestParam String tempcode){
		Result<Boolean> result = new Result<Boolean>(ICommon.SUCCESS,PropertiesUtils.get(ICommon.SUCCESS));
		String userinfo = this.checkHuicardUserTokenGetInfo(token);
		userInfoService.bindPhone(token, userinfo, phone, smscode, tempcode);
		return result;
	}
	
	
	
	
	
	
	
	

}
