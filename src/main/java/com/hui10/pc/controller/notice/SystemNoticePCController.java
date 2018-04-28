package com.hui10.pc.controller.notice;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hui10.app.common.web.BasePCController;
import com.hui10.app.model.notice.SystemNoticeInfo;
import com.hui10.app.service.notice.SystemNoticeService;
import com.hui10.model.common.Result;

/**
 * @ClassName: SystemNoticePCController.java
 * @Description:
 * @author huangrui
 * @date 2018年4月17日  15:56:30
 */
@Controller
public class SystemNoticePCController extends BasePCController {
	@Autowired
	private SystemNoticeService systemNoticeService;

	@RequestMapping(value = "/pc/notice/querylist", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<Map<String, Object>> querySystemNoticeList(@RequestParam String token, @RequestParam Integer pageno, 
			@RequestParam Integer pagesize) {
		this.checkMerchantToken(token);
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		result.setResult(systemNoticeService.querySystemNoticePageList(pageno, pagesize));
		return result;
	}
	
	@RequestMapping(value = "/pc/notice/queryinfo", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<SystemNoticeInfo> querySystemNoticeInfo(@RequestParam String token, @RequestParam String id) {
		this.checkMerchantToken(token);
		Result<SystemNoticeInfo> result = new Result<SystemNoticeInfo>();
		result.setResult(systemNoticeService.querySystemNoticeInfo(id));
		return result;
	}
}