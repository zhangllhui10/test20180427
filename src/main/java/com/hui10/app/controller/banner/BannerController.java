package com.hui10.app.controller.banner;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.banner.BannerInfo;
import com.hui10.app.service.banner.BannerService;
import com.hui10.model.common.Result;

/**
 * @ClassName: BannerController.java
 * @Description:
 * @author huangrui
 * @date 2018年1月22日  14:56:30
 */
@Controller
public class BannerController extends BaseController {
	@Autowired
	BannerService bannerService;

	@RequestMapping(value = "/banner/add", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addBanner(@RequestParam String token, BannerInfo bannerInfo) {
		Result<String> result = new Result<String>();
		String username = checkAdminToken(token);
		result.setResult(bannerService.addBanner(bannerInfo, username));
		return result;
	}
	
	@RequestMapping(value = "/banner/modify", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<String> modifyBanner(@RequestParam String token, BannerInfo bannerInfo) {
		Result<String> result = new Result<String>();
		String username = checkAdminToken(token);
		result.setResult(bannerService.modifyBanner(bannerInfo, username));
		return result;
	}
	
	@RequestMapping(value = "/banner/manage", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<String> changeBannerStatus(@RequestParam String token, @RequestParam String id, @RequestParam String status) {
		Result<String> result = new Result<String>();
		String username = checkAdminToken(token);
		result.setResult(bannerService.changeBannerStatus(id, status, username));
		return result;
	}

	@RequestMapping(value = "/banner/querylist", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<List<BannerInfo>> queryBannerList(@RequestParam String token, String position) {
		Result<List<BannerInfo>> result = new Result<List<BannerInfo>>();
		this.checkCUserToken(token);
		result.setResult(bannerService.queryBannerList(position));
		return result;
	}
}