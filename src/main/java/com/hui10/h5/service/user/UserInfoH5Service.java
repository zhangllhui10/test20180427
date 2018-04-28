package com.hui10.h5.service.user;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: UserInfoService.java
 * @Description:
 * @author zhangll
 * @date 2018年1月22日 下午2:09:35
 */
public interface UserInfoH5Service {
	
	public JSONObject backendToken();
	
	public JSONObject accessToken(String backendToken,String code);
	
	public JSONObject userMobile(String accessToken,String openId,String backendToken);
	
	public String login(String code, Map<String, String> params);

}
