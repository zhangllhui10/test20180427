package com.hui10.pc.service.merchant.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.constants.SmsConstants;
import com.hui10.app.common.utils.SendMsgUtils;
import com.hui10.app.model.merchant.MerchantInfo;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.exception.CommonException;
import com.hui10.pc.dao.merchant.MerchantPCDao;
import com.hui10.pc.service.merchant.MerchantPCService;
import com.hui10.pc.service.merchant.MerchantPCTokenService;

/**
 * @ClassName: MerchantPCServiceImpl.java
 * @Description:
 * @author zhangll
 * @date 2018年4月18日 下午1:32:07
 */
@Service
public class MerchantPCServiceImpl implements MerchantPCService {
	@Autowired
	private MerchantPCDao merchantPCDao;
	@Autowired
	private MerchantPCTokenService merchantPCTokenService;
	
	private String LOGIN_SMS = "merchant_login_";

	@Override
	public void loginSendSms(String phone) {
		checkPhone(phone);
		MerchantInfo info = new MerchantInfo();
		info.setContactnumber(phone);
		checkInfoExist(info);
		int codeNum = Integer.parseInt(PropertiesUtils.get("sms.code.number"));
		SendMsgUtils.sendSMS(codeNum, phone, SmsConstants.COMMON_CODE, LOGIN_SMS + phone);
	}

	 

	@Override
	public JSONObject login(String phone, String smscode) {
		JSONObject result = new JSONObject();
		checkPhone(phone);
		SendMsgUtils.checkCode(smscode, LOGIN_SMS + phone);
		MerchantInfo info = new MerchantInfo();
		info.setContactnumber(phone);
		List<MerchantInfo> list = checkInfoExist(info);
		String token = merchantPCTokenService.setToken(phone,list);
		result.put("token", token);
		result.put("list", list);
		result.put("phone", phone);
		return result;
	}

	@Override
	public JSONObject login_info(String phone, String smscede) {
		
		return null;
	}

	
	private void checkPhone(String phone) {
		if (!StringUtils.isPhone(phone)) {
			throw new CommonException(ICommon.VALIDATE_PHONE_FORMAT_FAIL, PropertiesUtils.get(ICommon.VALIDATE_PHONE_FORMAT_FAIL));
		}}
	
	private List<MerchantInfo> checkInfoExist(MerchantInfo info){
		List<MerchantInfo> list = merchantPCDao.getMerchantInfoList(info);
		if(list.isEmpty()){
			throw new CommonException(ICommon.MERCHANT_LIST_NOT_EXIST, "商户不存在或可能被停用");
		}
		return list;
	}



	@Override
	public void logout(List<MerchantInfo> list) {
		MerchantInfo info = list.get(0);
		String phone = info.getContactnumber();
		merchantPCTokenService.deleteToken(phone);
	}
	
}
