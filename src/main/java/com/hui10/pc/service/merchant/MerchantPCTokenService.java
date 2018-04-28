package com.hui10.pc.service.merchant;

import java.util.List;

import com.hui10.app.model.merchant.MerchantInfo;


/**
 * @ClassName: MerchantPCTokenService.java
 * @Description:
 * @author zhangll
 * @date 2018年4月18日 下午1:17:24
 */
public interface MerchantPCTokenService {
	
	  String setToken(String phone,List<MerchantInfo> list);
	 
	  List<MerchantInfo> validateUserAppToken(String token);
	 
	  void deleteToken(String phone);

}
