package com.hui10.app.user;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.TestBase;
import com.hui10.app.model.user.UserInfo;
import com.hui10.app.model.user.UserSimpleInfo;
import com.hui10.app.service.user.UserInfoService;
import com.hui10.common.sso.SsoUtil;
import com.hui10.enums.user.UserRegisterEnum;

/**
 * @ClassName: UserInfoTest.java
 * @Description:
 * @author zhangll
 * @date 2017年10月25日 下午2:40:08
 */
public class UserInfoTest extends TestBase {
	@Autowired
	private UserInfoService userInfoService;

	@Test
	public void queryUserSimpleInfo_test001() {
		String uid = "2017062916243269423810061";
		UserSimpleInfo userSimpleInfo = userInfoService.queryUserSimpleInfo(uid);
		JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(userSimpleInfo));
		System.out.println("*********************" + object.toString());
	}

	@Test
	public void queryUserSimpleInfo_test002() {
		String uid = "2017101718263827203419891";
		UserSimpleInfo userSimpleInfo = userInfoService.queryUserSimpleInfo(uid);
		JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(userSimpleInfo));
		System.out.println("*********************" + object.toString());
	}

	@Test
	public void queryUserSimpleInfo_test003() {
		String uid = "2017101718243815912818296";
		UserSimpleInfo userSimpleInfo = userInfoService.queryUserSimpleInfo(uid);
		JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(userSimpleInfo));
		System.out.println("*********************" + object.toString());
	}

	@Test
	public void modifyPassword_test001() {
		String token = "Eom8gn6mjgcuA+8z3jbiTTN2vJFE3p2B4S8DotM+qKhGKfqdK7qnMjS3BLxqLolkCBz3A0xdXjbjA+fOOr9a+g==";
		String userinfo = "{\"result\":{\"invokeType\":1,\"salt\":\"\",\"loginType\":0,\"openid\":\"\",\"pwdcomplexity\":\"\",\"phoneId\":\"\",\"recommendUser\":\"\",\"usertype\":\"\",\"updateTime\":0,\"userName\":\"15300178296\",\"realname\":\"\",\"random\":\"\",\"uid\":\"2017102516555929979318296\",\"password\":\"9b9e11d96dca043402c892b2ba09f558\",\"registType\":0,\"createTime\":0,\"phone\":\"15300178296\",\"platformUserId\":0,\"gameUserId\":\"2017102516555929979318296\",\"id\":1010530,\"email\":\"\",\"status\":1},\"success\":true,\"em\":\"\",\"ec\":200}";
		userInfoService.modifyPassword(token, userinfo, "a1234567", "a123456", null, "15300178296");
	}

	@Test
	public void registersms_test001() {
		String phone = "15300178296";
		userInfoService.registersms(phone);

	}

	@Test
	public void getUserUidByPhone_test001() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("regip", SsoUtil.IP);
		params.put("regposition", "北京");
		params.put("regtype", UserRegisterEnum.PHONE.getState());
		String phone = "15300178296";
		String uid = userInfoService.betOrderGetUserUidByPhone(phone, params);
		System.out.println("***********" + uid);
	}

	@Test
	public void queryUserInfoByUid_test001() {

		com.hui10.app.model.user.UserInfo userInfo = userInfoService.queryUserInfoByUid("dd");
		System.out.println("*************" + userInfo.getPhone());
	}

	@Test
	public void queryUserInfoByUid_test002() {

		com.hui10.app.model.user.UserInfo userInfo = userInfoService.queryUserInfoByUid("2017022112515770143918301");
		System.out.println("*************" + userInfo.getPhone());
	}

	@Test
	public void registerAndLogin_test() {
		String phone = "15300171196";
		Map<String, String> params = new HashMap<String, String>();
		params.put("regip", SsoUtil.IP);
		params.put("regposition", "北京");
		params.put("regtype", UserRegisterEnum.PHONE.getState());
		JSONObject object = userInfoService.registerAndLogin(phone, params, "11");
		System.out.println("********************:" + object.toJSONString());

	}

	@Test
	public void updateUserInfo_test() {
		UserInfo usinfo = new UserInfo();
		usinfo.setUid("2017022114053825057118632");
		usinfo.setNickname("11111😂");
		userInfoService.updateUserInfo(usinfo);
	}
	//手机号在sso和汇彩宝
	@Test
	public void login_sms(){
		String phone = "15300178296";
		String smscode = "111111";
		System.out.println("----------用户成功登陆-----------------"+phone+" "+JSONObject.toJSONString(userInfoService.login(phone, smscode, null)));
	}
	
	@Test
	public void login_sms_test2(){
		String phone = "15300178297";
		String smscode = "111111";
		System.out.println("----------用户不存在-----------------"+phone+" "+JSONObject.toJSONString(userInfoService.login(phone, smscode, null)));
	}
	
	@Test
	public void login_sms_test3(){
		Map<String, String> params = new HashMap<String,String>();
		params.put("regip", SsoUtil.IP);
		params.put("regposition", "北京");
		params.put("regtype", UserRegisterEnum.PHONE.getState());
		String phone = "17000000113";
		String smscode = "111111";
		System.out.println("----------第一次注册汇彩宝，密码存在-----------------"+phone+" "+JSONObject.toJSONString(userInfoService.login(phone, smscode, params)));
	}
	
	@Test
	public void login_sms_test4(){
		Map<String, String> params = new HashMap<String,String>();
		params.put("regip", SsoUtil.IP);
		params.put("regposition", "北京");
		params.put("regtype", UserRegisterEnum.PHONE.getState());
		String phone = "17888888888";
		String smscode = "111111";
		System.out.println("------密码不存在--------"+phone+" "+JSONObject.toJSONString(userInfoService.login(phone, smscode, params)));
	}
	
	@Test
	public void setPassword_test(){
		String token = "frPdMrjYjfCPPl8YftKZDKMW0y7rSdVLzmc1XdlhstSkelX5xTeXsVr94rPO1XlhG1TW0oVyaSemCN8tOnlVFQ==";
		String password = "a123456";
		userInfoService.setPassword("",token, password,"");
	}
	
	
	//手机号密码登录
	@Test
	public void login_test() {
		String phone = "17888888888";
		String password = "a123456";
		System.out.println("---登录成功----" + phone + " ," + password + " ," + " ,token = " + userInfoService.login(phone, password));
	}
	
	
	
}
