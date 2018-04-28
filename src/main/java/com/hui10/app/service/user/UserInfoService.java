package com.hui10.app.service.user;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.model.user.UserInfo;
import com.hui10.app.model.user.UserSimpleInfo;

/**
 * @ClassName: UserInfoService.java
 * @Description:
 * @author zhangll
 * @date 2017年10月17日 上午9:50:53
 */
public interface UserInfoService {
	
	public void registersms(String phone);
	public String register(String phone, String password, String smscode, String inviter, String invitesrc, String inviteip, Map<String, String> params);

	public String login(String phone, String password);
	
	public void logout(String token);
	/**
	 * 修改密码
	 * @param token
	 * @param userinfo
	 * @param oldpassword
	 * @param newpassword
	 * @param smscode
	 * @param phone
	 * @user zhangll
	 * @date 2017年10月25日 下午3:11:38
	 */
	public void modifyPassword(String token, String userinfo, String oldpassword, String newpassword,String smscode,String phone);
	/**
	 * 找回密码-发验证码
	 * @param phone
	 * @user zhangll
	 * @date 2017年10月25日 下午3:18:13
	 */
	public void forgetPasswordSendSms(String phone);
	
	/**
	 * 找回密码
	 * @param phone
	 * @param newpassword
	 * @param smsCode
	 * @user zhangll
	 * @date 2017年10月25日 下午3:11:32
	 */
	public void forgetPassword(String phone, String newpassword, String smsCode);
	/**
	 * 修改密码-发验证码
	 * @param userInfo
	 * @param phone
	 * @user zhangll
	 * @date 2017年10月25日 下午3:11:18
	 */
	public void modifyPasswordSendSms(String userInfo,String phone);
	/**
	 * 查询用户信息-个人中心
	 * @param uid
	 * @return
	 * @user zhangll
	 * @date 2017年10月25日 下午3:10:59
	 */
	public UserSimpleInfo queryUserSimpleInfo(String uid);
	/**
	 * 动态码登录前，发验证码
	 * @param phone
	 * @user zhangll
	 * @date 2017年12月5日 下午12:51:07
	 */
	public void loginSendSms(String phone);
	/**
	 * 无密码注册
	 * @param phone
	 * @param smscode
	 * @param params
	 * @return
	 * @user zhangll
	 * @date 2017年12月4日 下午3:20:29
	 */
	public JSONObject login(String phone,String smscode,Map<String, String> params);
	/**
	 * 内部方法，根据phone提供uid
	 * @param phone
	 * @param params
	 * @return
	 * @user zhangll
	 * @date 2017年12月4日 下午3:31:51
	 */
	public String betOrderGetUserUidByPhone(String phone,Map<String, String> params);
	/**
	 * 设置密码
	 * @param token
	 * @param password
	 * @user zhangll
	 * @date 2017年12月5日 下午3:06:51
	 */
	public void setPassword(String userinfo,String token,String password,String smscode);
	/**
	 * 根据uid查询用户基本信息
	 * @param uid
	 * @return
	 * @user zhangll
	 * @date 2017年12月11日 下午3:12:53
	 */
	public UserInfo queryUserInfoByUid(String uid);
	
	
	
	public JSONObject registerAndLogin(String phone,Map<String,String> params,String platfrom);
	
	public void updateUserInfo(UserInfo usinfo);
	
	/******** 更换手机 *****************/
	//1
	public void checkPassword(String userinfo,String password);
	//2
	public void unbindPhoneSendSms(String userinfo,String phone);
	/** 3
	 * @param userinfo
	 * @param phone
	 * @param smscode
	 * @return 返回临时码 tempcode
	 * @user zhangll
	 * @date 2018年3月2日 下午1:15:08
	 */
	public String unbindPhone(String userinfo,String phone,String smscode);
	//4
	public void bindPhoneSendSms(String uid,String phone);
	//5
	public void bindPhone(String token,String userinfo,String phone,String smscode,String tempcode);
	
	public void modifyPassword(String token, String userinfo,String newpassword,String smscode,String phone);
	
	public void modifyPasswordCheckOldPwd(String userinfo,String oldpassword);
	
	public void setPasswordSendSms(String userInfo ,String phone);
	
	
}
