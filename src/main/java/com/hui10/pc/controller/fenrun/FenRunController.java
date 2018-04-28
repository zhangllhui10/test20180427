package com.hui10.pc.controller.fenrun;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hui10.app.common.web.BasePCController;
import com.hui10.model.common.Result;
import com.hui10.pc.service.fenrun.FenRunService;

/**
 * @ClassName: FenRunController.java
 * @Description:
 * @author huangrui
 * @date 2018年4月24日  15:32:30
 */
@Controller
public class FenRunController extends BasePCController {
	
	@Autowired
	private FenRunService fenRunService;

	@RequestMapping(value = "/pc/fenrun/querylist", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<Map<String, Object>> queryFenRunList(@RequestParam String token, @RequestParam String merchantno,
			@RequestParam Integer pageno, @RequestParam Integer pagesize) {
		this.checkMerchantToken(token);
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		result.setResult(fenRunService.queryFenRunList(merchantno, pageno, pagesize));
		return result;
	}
	
}