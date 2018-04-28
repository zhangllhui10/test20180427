package com.hui10.app.common.web;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.hui10.app.model.merchant.MerchantInfo;
import com.hui10.common.constants.ICommon;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.exception.CommonException;
import com.hui10.pc.service.merchant.MerchantPCTokenService;

/**
 * @ClassName: BasePCController.java
 * @Description:
 * @author zhangll
 * @date 2018年4月18日 下午1:14:46
 */
public class BasePCController extends com.hui10.common.web.BaseController{
	@Autowired
	private MerchantPCTokenService merchantPCTokenService;
	

	public List<MerchantInfo> checkMerchantToken_list(String token){
		List<MerchantInfo> list = merchantPCTokenService.validateUserAppToken(token);
		return list;
	}
	
	public void checkMerchantToken(String token){
		merchantPCTokenService.validateUserAppToken(token);
	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String value) {
				try {
					if (value != null && !value.trim().equals("")) {
						setValue(new Date(Long.valueOf(value)));
					}else {
						setValue(null);
					}
				} catch (Exception e) {
					setValue(null);
					throw new CommonException(ICommon.DATE_FORMAT_ERROR, PropertiesUtils.get(ICommon.DATE_FORMAT_ERROR));
				}
			}
		});
	}
}
