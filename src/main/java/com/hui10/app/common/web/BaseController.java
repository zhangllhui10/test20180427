package com.hui10.app.common.web;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSONObject;
import com.hui10.common.constants.ICommon;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.exception.CommonException;

/**
 * @ClassName: BaseController.java
 * @Description:
 * @author wengf
 * @date 2016年11月3日 下午4:58:03
 */
public abstract class BaseController extends com.hui10.common.web.BaseController {

	@Override
	public String checkToken(String accesstoken) {
		
		return super.checkHuiAndGameToken(accesstoken);
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

	@Override
	protected JSONObject getJSONObject(String accesstoken) {
		return super.getJSONObject(accesstoken);
	}

}

