package com.hui10.app.controller.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.notice.SystemNoticeInfo;
import com.hui10.app.service.notice.SystemNoticeService;
import com.hui10.model.common.Result;

/** 
 * @ClassName: SystemNoticeController.java
 * @Description:
 * @author huangrui
 * @date 2018年4月17日  15:56:30
 */
@Controller
public class SystemNoticeController extends BaseController {
	@Autowired
	private SystemNoticeService systemNoticeService;

	@RequestMapping(value = "/system/notice/add", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addSystemNotice(@RequestParam String token, SystemNoticeInfo systemNoticeInfo) {		
		String username = checkAdminToken(token);
		Result<String> result = new Result<String>();
		result.setResult(systemNoticeService.addSystemNotice(systemNoticeInfo, username));
		return result;
	}
	
	@RequestMapping(value = "/system/notice/modify", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<String> modifySystemNotice(@RequestParam String token, SystemNoticeInfo systemNoticeInfo) {	
		String username = checkAdminToken(token);
		Result<String> result = new Result<String>();
		result.setResult(systemNoticeService.modifySystemNotice(systemNoticeInfo, username));
		return result;
	}
}