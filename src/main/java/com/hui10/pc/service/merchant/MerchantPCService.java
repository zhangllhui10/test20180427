package com.hui10.pc.service.merchant;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.model.merchant.MerchantInfo;

/**
 * @ClassName: MerchantPCService.java
 * @Description:
 * @author zhangll
 * @date 2018年4月18日 下午1:28:54
 */
public interface MerchantPCService {
	void loginSendSms(String phone);
	
	JSONObject login(String phone,String smscode);
	
	JSONObject login_info(String phone,String smscede);
	
	void logout(List<MerchantInfo> list);

}
