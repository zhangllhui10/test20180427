package com.hui10.h5.controller.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.web.BaseController;
import com.hui10.common.constants.ICommon;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.h5.service.main.MainH5Service;
import com.hui10.model.common.Result;

/**
 * @ClassName: MainH5Controller.java
 * @Description:
 * @author zhangll
 * @date 2018年1月23日 上午11:24:56
 */
@RestController
public class MainH5Controller extends BaseController {
	@Autowired
	private MainH5Service mainH5Service;

	@RequestMapping(value = "/h5/home/homepage", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public Result<JSONObject> homepage(@RequestParam String token) {
		Result<JSONObject> result = new Result<JSONObject>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		String uid = this.checkHuicardUserToken(token);
		result.setResult(mainH5Service.homePage(uid));
		return result;
	}

}
