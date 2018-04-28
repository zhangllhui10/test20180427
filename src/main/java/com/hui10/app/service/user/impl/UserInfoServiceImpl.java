package com.hui10.app.service.user.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.constants.SmsConstants;
import com.hui10.app.common.utils.SendMsgUtils;
import com.hui10.app.dao.user.UserInfoDao;
import com.hui10.app.model.enums.UserGenderEnum;
import com.hui10.app.model.enums.UserIsNewUserEnum;
import com.hui10.app.model.user.UserBankCard;
import com.hui10.app.model.user.UserInfo;
import com.hui10.app.model.user.UserSimpleInfo;
import com.hui10.app.service.user.UserInfoService;
import com.hui10.common.cache.RedisCacheManagerImpl;
import com.hui10.common.constants.Constants;
import com.hui10.common.sso.HttpUtils;
import com.hui10.common.sso.SsoUtil;
import com.hui10.common.utils.CommonUtils;
import com.hui10.common.utils.HuiUserCacheUtils;
import com.hui10.common.utils.MD5Util;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.enums.user.HuiUserSatusEnum;
import com.hui10.enums.user.UserInvoketypeEnum;
import com.hui10.enums.user.UserLoginTypeEnum;
import com.hui10.enums.user.UserRegisterEnum;
import com.hui10.exception.CommonException;

/**
 * @ClassName: UserInfoServiceImpl.java
 * @Description:
 * @author zhangll
 * @date 2017年10月17日 上午9:52:56
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoDao userInfoDao;
	private static String MODIFY_PASSWORD_SENDSMS = "modifyPasswordSendSms";
	private static String LOGIN_NO_PASSWORD_SENDSMS = "loginNoPasswordSendSms";
	public static String PASSWORD_IS_NULL = "1";
	private static String USER_NOT_EXIST = "1020101003";
	private static String UNBIND_PHONE_SMS = "unbindphonesms";
	private static String BIND_PHONE_SMS = "bindphonesms";
	private static String UNBIND_PHONE_TEMP_CODE = "unbindphonetempcode";
	private static Integer UNBIND_PHONE_TEMP_CODE_EXPIRED = 30 * 60;
	
	@Autowired
	private RedisCacheManagerImpl redisCacheManagerImpl;
	private static Logger LOG = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	@Override
	public void registersms(String phone) {
		// 将短信发送到指定的手机
		SsoUtil.getregistercode(phone.trim(), SsoUtil.SMS_TYPE_REGISTER.toString(), UserInvoketypeEnum.HUI_APP.getState());
	}

	@Override
	public String register(String phone, String password, String smscode, String inviter, String invitesrc, String inviteip, Map<String, String> params) {

		if (StringUtils.isBlank(smscode)) {
			throw new CommonException(ICommon.PARAMETER_IS_NULL, PropertiesUtils.get(ICommon.PARAMETER_IS_NULL, "smscode"));
		}
		if (StringUtils.isBlank(phone)) {
			throw new CommonException(ICommon.PARAMETER_IS_NULL, PropertiesUtils.get(ICommon.PARAMETER_IS_NULL, "phone"));
		}
		if (StringUtils.isBlank(password)) {
			throw new CommonException(ICommon.PARAMETER_IS_NULL, PropertiesUtils.get(ICommon.PARAMETER_IS_NULL, "password"));
		}
		String testSms = smscode == null ? null : smscode;
		if (UserRegisterEnum.getInfo(params.get("regtype")) == null) {
			throw new CommonException(ICommon.REGIST_TYPE_IS_ERROR, PropertiesUtils.get(ICommon.REGIST_TYPE_IS_ERROR));
		}
		params.put("invoketype", UserInvoketypeEnum.HUI_APP.getState());
		String user = userInfoDao.queryUserByPhone(phone,null);
		if(user != null){
			throw new CommonException(ICommon.USER_EXIST, PropertiesUtils.get(ICommon.USER_EXIST));	
		}
		String uid = SsoUtil.generateUid(phone, SsoUtil.REGISTER_TYPE, UserInvoketypeEnum.HUI_APP.getState(), MD5Util.encode(phone, Constants.hui_sso_secrest));
		if (StringUtils.isBlank(uid)) {
			throw new CommonException(ICommon.UID_USER_IS_NULL_CODE, PropertiesUtils.get(ICommon.UID_USER_IS_NULL_CODE));
		} else {
			Date jointime = new Date();
			if (userInfoDao.insertUserInfo(uid, phone, jointime, Constants.NICKNAME, params.get("regtype"), params.get("regip"),
					params.get("regposition"),null) > 0) {
			} else {
				throw new CommonException(ICommon.SAVE_USER_IS_ERROR, PropertiesUtils.get(ICommon.SAVE_USER_IS_ERROR));
			}
		}
		// 调用sso接口，返回token
		params.put("uid", uid);
		String token = SsoUtil.getregistertokenFromSso(phone, password, testSms, inviter, params);
		return token;
	}

	@Override
	public String login(String phone, String password) {
		checkPhone(phone);
		if (StringUtils.isBlank(password)) {
			throw new CommonException(ICommon.PARAMETER_IS_NULL, PropertiesUtils.get(ICommon.PARAMETER_IS_NULL, "password"));
		}
		String acceptToken = SsoUtil.login(phone, password, UserLoginTypeEnum.HUI_APP.getState(), UserInvoketypeEnum.HUI_APP.getState(), "", "");
		String uid = SsoUtil.checkToken(acceptToken);
		Map<String,String> user = userInfoDao.isExistUserByUid(uid);
		if (user == null) {
			Date jointime = new Date();
			if (userInfoDao.insertUserInfo(uid, phone, jointime, Constants.NICKNAME, UserRegisterEnum.PHONE.getState(), SsoUtil.IP, "北京", null) > 0) {
			} else {
				throw new CommonException(ICommon.SAVE_USER_IS_ERROR, PropertiesUtils.get(ICommon.SAVE_USER_IS_ERROR));
			}
		}
		return acceptToken;
	}

	@Override
	public void logout(String token) {
		SsoUtil.logout(token);
			}

	@Override
	public void modifyPassword(String token, String userinfo, String oldpassword, String newpassword, String smscode, String phone) {
		// 验证密码的规则
		JSONObject object = getUserInfo(userinfo);
		String uid = object.getString("uid");
		
		String phone_from_token = object.getString("phone");
		checkPhone(phone_from_token, phone);
		SendMsgUtils.checkCode(smscode, MODIFY_PASSWORD_SENDSMS + phone);
		if (!HuiUserCacheUtils.getPasswordWrongNumber(HuiUserCacheUtils.MODIFY_PASSWORD + uid)) {
			throw new CommonException(ICommon.OLDPASSWORD_WRONG_NUMBER, PropertiesUtils.get(ICommon.OLDPASSWORD_WRONG_NUMBER, HuiUserCacheUtils.LOCK_TIME));
		}
		chePassword(newpassword);
		String password_from_sso = object.getString("password");
		String md5old = MD5Util.encode(oldpassword, MD5Util.getSaltByUid(uid));
		if (md5old.equals(password_from_sso)) {

			// 调用SSO接口，修改登录用户的密码，如果返回值为true
			SsoUtil.modifyUserInfoSso("", "", HuiUserSatusEnum.NORMAL.getState(), newpassword, token, "");

		} else {
			Integer number = HuiUserCacheUtils.putPasswordWrongNumber(HuiUserCacheUtils.MODIFY_PASSWORD + uid, HuiUserCacheUtils.LOGIN_NUM);
			if (number > 0) {
				throw new CommonException(ICommon.OLD_IS_ERROR_CODE, PropertiesUtils.get(ICommon.OLD_IS_ERROR_CODE) + ",还剩" + number + "次机会");
			} else {
				throw new CommonException(ICommon.OLDPASSWORD_WRONG_NUMBER, PropertiesUtils.get(ICommon.OLDPASSWORD_WRONG_NUMBER, HuiUserCacheUtils.LOCK_TIME));
			}
		}
	}

	@Override
	public void forgetPassword(String phone, String newpassword, String smsCode) {
		checkPhone(phone);
		chePassword(newpassword);
		// 调用SSO接口
		SsoUtil.changeUserPassword(phone, smsCode, newpassword, UserInvoketypeEnum.HUI_APP.getState());
		HuiUserCacheUtils.removePasswordWrongNumber(HuiUserCacheUtils.LOGINNUM_STRING + phone);
		// }

	}

	@Override
	public void modifyPasswordSendSms(String userInfo, String phone) {
		sendSmsTo_OldUser(userInfo, phone, MODIFY_PASSWORD_SENDSMS);
	}

	@Override
	public UserSimpleInfo queryUserSimpleInfo(String uid) {
		UserSimpleInfo user = userInfoDao.queryUserSimpleInfo(uid);
		if (null != user) {
			user.setPhone(CommonUtils.phoneReg(user.getPhone()));
			if(StringUtils.isBlank(user.getNickname()))
			{
				user.setNickname(CommonUtils.phoneReg(user.getPhone()));
			}else{
				user.setNickname(strConvertToEmoji(user.getNickname()));
			}
			for (UserBankCard c : user.getCardlist()) {
				c.setReservephone(CommonUtils.phoneReg(c.getReservephone()));
			}
		} else {
			throw new CommonException(ICommon.UID_USER_IS_NULL_CODE, PropertiesUtils.get(ICommon.UID_USER_IS_NULL_CODE));
		}
		return user;
	}

	@Override
	public void forgetPasswordSendSms(String phone) {
		SsoUtil.getregistercode(phone, SsoUtil.SMS_TYPE_PASSWORD_FIND.toString(), SsoUtil.LOGIN_TYPE);
	}

	@Override
	public JSONObject login(String phone, String smscode, Map<String, String> params) {
		JSONObject result = new JSONObject();
		checkPhone(phone);
		SendMsgUtils.checkCode(smscode, LOGIN_NO_PASSWORD_SENDSMS + phone);
		result = postSsoPhoneIsRegist(phone, null);
		Map<String, String> map = userInfoDao.isExistUserByUid(result.getString("uid"));
		if (null == map) {
			// TODO 再用手机号查一遍
			// 用户已在SSO，但是没在汇彩宝
			userInfoDao.insertUserInfo(result.getString("uid"), phone, new Date(), Constants.NICKNAME, params.get("regtype"), params.get("regip"),
					params.get("regposition"), null);
		} else {
			if (!phone.equals(map.get("phone"))) {
				LOG.debug("sso的手机号和汇彩宝的手机号不一致");
			}
		}

		return result;
	}

	@Override
	public String betOrderGetUserUidByPhone(String phone, Map<String, String> params) {
		if (null == params) {
			params = new HashMap<String, String>();
			params.put("regip", SsoUtil.IP);
			params.put("regposition", "北京");
			params.put("regtype", UserRegisterEnum.PHONE.getState());
		}

		String uid = null;
		checkPhone(phone);
		try {
			String local_uid = userInfoDao.queryUserByPhone(phone, HuiUserSatusEnum.NORMAL.getState());
			if (StringUtils.isBlank(local_uid)) {
				String token = SsoUtil.login(phone, "", UserLoginTypeEnum.HUI_APP.getState(), UserInvoketypeEnum.HUI_APP.getState(), "", "", PASSWORD_IS_NULL);
				if (!StringUtils.isBlank(token)) {
					uid = SsoUtil.retrieveuid(phone, uid);
					userInfoDao.insertUserInfo(uid, phone, new Date(), Constants.NICKNAME, params.get("regtype"), params.get("regip"),
							params.get("regposition"), null);
				}

			} else {

				uid = local_uid;
			}
		} catch (Exception e) {
			CommonException ce = (CommonException) e;
			if (Integer.parseInt(USER_NOT_EXIST) == ce.getCode()) {
				uid = SsoUtil.generateUid(phone, SsoUtil.REGISTER_TYPE, UserInvoketypeEnum.HUI_APP.getState(),
						MD5Util.encode(phone, Constants.hui_sso_secrest));
				userInfoDao.insertUserInfo(uid, phone, new Date(), Constants.NICKNAME, params.get("regtype"), params.get("regip"), params.get("regposition"),
						null);
				uid = SsoUtil.retrieveuid(phone, uid);
			} else {
				throw new CommonException(ICommon.USER_INFO_FAILURE, PropertiesUtils.get(ICommon.USER_INFO_FAILURE));
			}
		}
		return uid;
	}

	@Override
	public void loginSendSms(String phone) {
		checkPhone(phone);
		try {
			String token = SsoUtil.login(phone, "", UserLoginTypeEnum.HUI_APP.getState(), UserInvoketypeEnum.HUI_APP.getState(), "", "", PASSWORD_IS_NULL);
			if (StringUtils.isBlank(token)) {
				throw new CommonException(ICommon.UID_USER_IS_NULL_CODE, PropertiesUtils.get(ICommon.UID_USER_IS_NULL_CODE));
			}
		} catch (Exception e) {

			if (e instanceof CommonException) {
				CommonException ce = (CommonException) e;
				// 用户不存在
				if (Integer.parseInt(USER_NOT_EXIST) == ce.getCode()) {
					throw new CommonException(ICommon.UID_USER_IS_NULL_CODE, PropertiesUtils.get(ICommon.UID_USER_IS_NULL_CODE));
				} else {
					LOG.error("login_PasswordIsNull , message is " + e.getMessage());
				}
			} else {
				LOG.error("login_PasswordIsNull , message is " + e.getMessage());
			}

		}
		int codeNum = Integer.parseInt(PropertiesUtils.get("sms.code.number"));
		SendMsgUtils.sendSMS(codeNum, phone, SmsConstants.COMMON_CODE, LOGIN_NO_PASSWORD_SENDSMS + phone);
	}

	@Override
	public void setPassword(String userinfo,String token, String password,String smscode) {
		JSONObject user = getUserInfo(userinfo);
		SendMsgUtils.checkCode(smscode,  "setpassword" + user.getString("phone"));
		chePassword(password);
		SsoUtil.modifyUserInfoSso(null, null, null, password, token, null);
	}
	/**
	 * 验证密码格式
	 * @param password
	 * @user zhangll
	 * @date 2018年3月28日 下午3:42:49
	 */
	private void chePassword(String password){
		if (!StringUtils.checkPassword(password)) {
			throw new CommonException(ICommon.PASSWORD_ISNOT_FORMAT, PropertiesUtils.get(ICommon.PASSWORD_ISNOT_FORMAT));
		}
	}

	@Override
	public UserInfo queryUserInfoByUid(String uid) {
		UserInfo userInfo = userInfoDao.queryUserInfoByUid(uid);
		if (null == userInfo) {
			throw new CommonException(ICommon.UID_USER_IS_NULL_CODE, PropertiesUtils.get(ICommon.UID_USER_IS_NULL_CODE));
		}
		return userInfo;
	}

	@Override
	public JSONObject registerAndLogin(String phone, Map<String, String> params,String platfrom) {
		String token = null;
		JSONObject result = new JSONObject();
		try {
			token = SsoUtil.login(phone, "", UserLoginTypeEnum.HUI_APP.getState(), UserInvoketypeEnum.HUI_APP.getState(), "", "", PASSWORD_IS_NULL);
			if (!StringUtils.isBlank(token)){
				String user = userInfoDao.queryUserByPhone(phone, null);
				if (StringUtils.isBlank(user)) {
					String uid = SsoUtil.checkToken(token);
					userInfoDao.insertUserInfo(uid, phone, new Date(), Constants.NICKNAME, params.get("regtype"), params.get("regip"), params.get("regposition"),platfrom);
				}
			}
			result.put("isnewuser",UserIsNewUserEnum.IS_NOT_NEW.getCode());
			result.put("token", token);
		} catch (Exception e) {
			if (e instanceof CommonException) {
				CommonException ce = (CommonException) e;
				if (Integer.parseInt(USER_NOT_EXIST) == ce.getCode()) {

					String uid = SsoUtil.generateUid(phone, SsoUtil.REGISTER_TYPE, UserInvoketypeEnum.HUI_APP.getState(),
							MD5Util.encode(phone, Constants.hui_sso_secrest));
					userInfoDao.insertUserInfo(uid, phone, new Date(), Constants.NICKNAME, params.get("regtype"), params.get("regip"),
							params.get("regposition"), platfrom);
					params.put("invoketype", UserInvoketypeEnum.HUI_APP.getState());
					params.put("uid", uid);
					String password = StringUtils.genRandomPassword(6);
					token = SsoUtil.getregistertokenFromSso(phone, password, null, null, params);
					result.put("isnewuser", UserIsNewUserEnum.IS_NEW.getCode());
					result.put("token", token);
				}
			}
		}
		
		return result;
	}
	
	

	@Override
	public void updateUserInfo(UserInfo user) {
		LOG.debug("nickname:"+user.getName());
		if(user.getSex() != null){
			if (null == UserGenderEnum.getByCode(user.getSex().toString())) {
				throw new CommonException(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR));
			}	
		}
		if(StringUtils.isNotBlank(user.getNickname())){
		user.setNickname(emojiConvertToStr(user.getNickname()));
		}
		userInfoDao.updateUserInfosByUid(user);

	}

	@Override
	public void checkPassword(String userinfo, String password) {
		JSONObject user = getUserInfo(userinfo);
		String password_encode = MD5Util.encode(password, MD5Util.getSaltByUid(user.getString("uid")));
		if (!password_encode.equals(user.getString("password"))) {
			// 密码不正确
			throw new CommonException(ICommon.OLD_IS_ERROR_CODE, PropertiesUtils.get(ICommon.OLD_IS_ERROR_CODE));
		}
	}
	
	private void sendSmsTo_OldUser(String userInfo, String phone, String remarks) {
		checkPhone(phone);
		JSONObject user = getUserInfo(userInfo);
		checkPhone(user.getString("phone"), phone);

		int codeNum = Integer.parseInt(PropertiesUtils.get("sms.code.number"));
		SendMsgUtils.sendSMS(codeNum, phone, SmsConstants.COMMON_CODE, remarks + phone);
	}

	@Override
	public void unbindPhoneSendSms(String userinfo, String phone) {
		sendSmsTo_OldUser(userinfo, phone, UNBIND_PHONE_SMS);
	}

	@Override
	public String unbindPhone(String userinfo, String phone, String smscode) {
		JSONObject user = getUserInfo(userinfo);
		checkPhone(user.getString("phone"), phone);
		SendMsgUtils.checkCode(smscode, UNBIND_PHONE_SMS + phone);
		String tempcode = tempcode(20);
		redisCacheManagerImpl.put(UNBIND_PHONE_TEMP_CODE + phone, tempcode, UNBIND_PHONE_TEMP_CODE_EXPIRED);
		return tempcode;
	}

	@Override
	public void bindPhoneSendSms(String uid, String phone) {
		bindPhoneCheckPhone(phone);
		int codeNum = Integer.parseInt(PropertiesUtils.get("sms.code.number"));
		SendMsgUtils.sendSMS(codeNum, phone, SmsConstants.COMMON_CODE, BIND_PHONE_SMS + phone);
	}

	@Override
	public void bindPhone(String token,String userinfo, String phone, String smscode, String tempcode) {
		JSONObject user = getUserInfo(userinfo);
		if (null == redisCacheManagerImpl.get(UNBIND_PHONE_TEMP_CODE + user.getString("phone"))
				|| !tempcode.equals(redisCacheManagerImpl.get(UNBIND_PHONE_TEMP_CODE + user.getString("phone")))) {
			throw new CommonException(ICommon.USER_CHANGE_PHONE_FAILURE, PropertiesUtils.get(ICommon.USER_CHANGE_PHONE_FAILURE));
		}
		SendMsgUtils.checkCode(smscode,BIND_PHONE_SMS + phone);
		bindPhoneCheckPhone(phone);
		UserInfo usinfo = new UserInfo();
		usinfo.setUid(user.getString("uid"));
		usinfo.setPhone(phone);
		userInfoDao.updateUserInfosByUid(usinfo);
		SsoUtil.modifyUserInfoSso(null, phone, null, null, token, null);
	}
	
	private String tempcode(int length) {
		String str = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int ran = random.nextInt(str.length());
			sb.append(str.charAt(ran));
		}
		sb.append(System.currentTimeMillis());
		return sb.toString().substring(10);
	}
	
	private void bindPhoneCheckPhone(String phone){
		checkPhone(phone);
		if (null != userInfoDao.queryUserByPhone(phone, null)) {
			throw new CommonException(ICommon.USER_EXIST, PropertiesUtils.get(ICommon.USER_EXIST));
		}
	}

	@Override
	public void modifyPassword(String token, String userinfo, String newpassword, String smscode, String phone) {
		// 验证密码的规则
		JSONObject u = getUserInfo(userinfo);;
		String phone_from_token = u.getString("phone");
		checkPhone(phone_from_token, phone);
		SendMsgUtils.checkCode(smscode, MODIFY_PASSWORD_SENDSMS + phone);

		chePassword(newpassword);
		// 调用SSO接口，修改登录用户的密码，如果返回值为true
		SsoUtil.modifyUserInfoSso("", "", HuiUserSatusEnum.NORMAL.getState(), newpassword, token, "");

	}

	@Override
	public void modifyPasswordCheckOldPwd(String userinfo, String oldpassword) {
		JSONObject user = getUserInfo(userinfo);;
		String password_encode = MD5Util.encode(oldpassword, MD5Util.getSaltByUid(user.getString("uid")));
		if (!password_encode.equals(user.getString("password"))) {
			// 密码不正确
			throw new CommonException(ICommon.OLD_IS_ERROR_CODE, PropertiesUtils.get(ICommon.OLD_IS_ERROR_CODE));
		}
		//TODO  
		/*{
			Integer number = HuiUserCacheUtils.putPasswordWrongNumber(HuiUserCacheUtils.MODIFY_PASSWORD + uid, HuiUserCacheUtils.LOGIN_NUM);
			if (number > 0) {
				throw new CommonException(ICommon.OLD_IS_ERROR_CODE, PropertiesUtils.get(ICommon.OLD_IS_ERROR_CODE) + ",还剩" + number + "次机会");
			} else {
				throw new CommonException(ICommon.OLDPASSWORD_WRONG_NUMBER, PropertiesUtils.get(ICommon.OLDPASSWORD_WRONG_NUMBER, HuiUserCacheUtils.LOCK_TIME));
			}
		}
		if (!HuiUserCacheUtils.getPasswordWrongNumber(HuiUserCacheUtils.MODIFY_PASSWORD + uid)) {
		throw new CommonException(ICommon.OLDPASSWORD_WRONG_NUMBER, PropertiesUtils.get(ICommon.OLDPASSWORD_WRONG_NUMBER, HuiUserCacheUtils.LOCK_TIME));
	}*/
	}

	private static String emojiConvertToStr(String str) {
		String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				matcher.appendReplacement(sb, "[[" + URLEncoder.encode(matcher.group(1), "UTF-8") + "]]");
			} catch (UnsupportedEncodingException e) {
				LOG.error("emojiConvertToStr error", e);
			}
		}
		matcher.appendTail(sb);
		LOG.debug("emojiConvertToStr:Convert " + str + " to " + sb.toString() + ", len：" + sb.length());
		return sb.toString();
	}

	private static String strConvertToEmoji(String str) {
		String patternString = "\\[\\[(.*?)\\]\\]";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				matcher.appendReplacement(sb, URLDecoder.decode(matcher.group(1), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				LOG.error("strConvertToEmoji error", e);
			}
		}
		matcher.appendTail(sb);
		LOG.debug("strConvertToEmoji:Convert " + str + " to " + sb.toString());
		return sb.toString();
	}
	
	private void checkPhone(String phone) {
		if (!StringUtils.isPhone(phone)) {
			throw new CommonException(ICommon.VALIDATE_PHONE_FORMAT_FAIL, PropertiesUtils.get(ICommon.VALIDATE_PHONE_FORMAT_FAIL));
		}}
	
	
	private  JSONObject postSsoPhoneIsRegist(final String username, final String password) {
		JSONObject result = null;
		Map<String, String> maplist = new HashMap<String, String>();
		maplist.put("username", username);
		maplist.put("password", password);
		maplist.put("invoketype", "1");
		maplist.put("secret", MD5Util.encode(username.trim() + PropertiesUtils.get("hui.sso.secret").trim()));
		String response_result = null;
		try {
			response_result = HttpUtils.post(SsoUtil.sso_url + PropertiesUtils.get("phoneisregist"), maplist);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommonException(ICommon.CONNECT_SSO_IS_ERROR, PropertiesUtils.get(ICommon.CONNECT_SSO_IS_ERROR));
		}
		if (StringUtils.isBlank(response_result)) {
			throw new CommonException(ICommon.CONNECT_SSO_IS_ERROR, PropertiesUtils.get(ICommon.CONNECT_SSO_IS_ERROR));
		} else {
			JSONObject res_result = JSONObject.parseObject(response_result);
			if (!res_result.getBoolean("success")) {
				throw new CommonException(res_result.getIntValue("ec"), res_result.getString("em"));
			} else {
				result = res_result.getJSONObject("result");
			}

		}

		return result;
	}

	@Override
	public void setPasswordSendSms(String userInfo, String phone) {
		JSONObject user = getUserInfo(userInfo);
		checkPhone(user.getString("phone"), phone);
		int codeNum = Integer.parseInt(PropertiesUtils.get("sms.code.number"));
		SendMsgUtils.sendSMS(codeNum, phone, SmsConstants.COMMON_CODE, "setpassword" + phone);
	}
	
	private JSONObject getUserInfo(String userInfo) {
		return JSONObject.parseObject(userInfo).getJSONObject("result");

	}
	
	private void checkPhone(String phone_from_checktoken,String phone){
		if (!phone_from_checktoken.equals(phone)) {
			throw new CommonException(ICommon.USER_PHONE_MISMATCHING, PropertiesUtils.get(ICommon.USER_PHONE_MISMATCHING));
		}
	}

}
