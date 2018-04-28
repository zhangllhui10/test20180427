package com.hui10.app.controller.marketing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.marketing.Marketing;
import com.hui10.app.model.marketing.MarketingAccessChannel;
import com.hui10.app.service.marketing.ChannelService;
import com.hui10.app.service.marketing.MarketingService;
import com.hui10.model.common.Result;

/**
 * @ClassName: MarketingController.java
 * @Description:
 * @author zhangll
 * @date 2018年2月27日 上午9:40:08
 */
@Controller
public class MarketingController extends BaseController {
	
	@Autowired
	private MarketingService marketingService;
	@Autowired
	private ChannelService channelService;
	
	@RequestMapping(value="/marketing/add",method=RequestMethod.POST,produces={ "application/json;charset=UTF-8" })
	@ResponseBody
	public Result<Boolean> addMarketing(String token,Marketing marketing, @RequestParam String accessjson){
		Result<Boolean> result = new Result<Boolean>();
		this.checkAdminToken(token);
		validateStringParameterNotBlank(accessjson);
		marketingService.addMarketing(marketing, accessjson);
		return result;
	}
	
	@RequestMapping(value="/marketing/update",method=RequestMethod.POST,produces={ "application/json;charset=UTF-8" })
	@ResponseBody
	public Result<Boolean> updateMarketing(String token,Marketing marketing,@RequestParam String accessjson){
		Result<Boolean> result = new Result<Boolean>();
		this.checkAdminToken(token);
		validateStringParameterNotBlank(accessjson);
		marketingService.updateMarketing(marketing, accessjson);
		return result;
	}
	
	@RequestMapping(value="/marketing/update/status",method=RequestMethod.POST,produces={ "application/json;charset=UTF-8" })
	@ResponseBody
	public Result<Boolean> updateMarketingStatus(@RequestParam String token,@RequestParam String marketingid,@RequestParam String status){
		Result<Boolean> result = new Result<Boolean>();
		this.checkAdminToken(token);
		validateStringParameterNotBlank(marketingid,status);
		marketingService.updateMarketingStatus(marketingid, status);
		return result;
	}
	
	@RequestMapping(value="/marketing/channel/add",method=RequestMethod.POST,produces={ "application/json;charset=UTF-8" })
	@ResponseBody
	public Result<Boolean> addChannel(@RequestParam String token,MarketingAccessChannel channel){
		Result<Boolean> result = new Result<Boolean>();
		this.checkAdminToken(token);
		channelService.addChannel(channel);
		return result;
	}
	
	@RequestMapping(value="/marketing/channel/update",method=RequestMethod.POST,produces={ "application/json;charset=UTF-8" })
	@ResponseBody
	public Result<Boolean> updateChannel(@RequestParam String token,MarketingAccessChannel channel){
		Result<Boolean> result = new Result<Boolean>();
		this.checkAdminToken(token);
		channelService.editChannel(channel);
		return result;
	}
	
	@RequestMapping(value="/marketing/channel/update/status",method=RequestMethod.POST,produces={ "application/json;charset=UTF-8" })
	@ResponseBody
	public Result<Boolean> updateStatus(@RequestParam String token,String channelid,String status){
		Result<Boolean> result = new Result<Boolean>();
		this.checkAdminToken(token);
		channelService.updateStatus(channelid, status);
		return result;
	}
	
	
	

}
