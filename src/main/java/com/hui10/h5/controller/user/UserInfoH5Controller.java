package com.hui10.h5.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hui10.app.common.web.BaseController;
import com.hui10.common.constants.ICommon;
import com.hui10.common.sso.SsoUtil;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.enums.user.UserRegisterEnum;
import com.hui10.h5.service.user.UserInfoH5Service;
import com.hui10.model.common.Result;

/**
 * @ClassName: UserInfoController.java
 * @Description:
 * @author zhangll
 * @date 2018年1月22日 下午2:08:42
 */
@RestController
public class UserInfoH5Controller extends BaseController {
	@Autowired
	private UserInfoH5Service userInfoH5Service;
	
	@RequestMapping(value="/h5/user/login",produces = { "application/json;charset=UTF-8" },method=RequestMethod.POST)
	public Result<String> login(@RequestParam String code) {
		Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		Map<String, String> params = new HashMap<String, String>();
		params.put("regip", SsoUtil.IP);
		params.put("regposition", "北京");
		params.put("regtype", UserRegisterEnum.PHONE.getState());
		result.setResult(userInfoH5Service.login(code, params));
		return result;
	}

}
