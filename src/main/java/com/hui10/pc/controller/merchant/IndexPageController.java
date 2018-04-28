package com.hui10.pc.controller.merchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hui10.app.common.web.BasePCController;
import com.hui10.model.common.Result;
import com.hui10.pc.model.merchant.IndexPage;
import com.hui10.pc.service.merchant.IndexPageService;

@Controller
public class IndexPageController extends BasePCController{
	
	@Autowired
	private IndexPageService indexPageService;
	
	@RequestMapping(value="/pc/index")
	@ResponseBody
	public Result<IndexPage> index(@RequestParam String token, @RequestParam String merchantno){
		this.checkMerchantToken(token);
		Result<IndexPage> result = new Result<>();
		result.setResult(indexPageService.findIndexPage(merchantno));
		return result;
	}

}
