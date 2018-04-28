package com.hui10.app.controller.main;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.lottery.LotteryPast;
import com.hui10.app.model.main.HomePage;
import com.hui10.app.service.main.MainService;
import com.hui10.common.constants.ICommon;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.model.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName: MainController.java
 * @Description:
 * @author wengf
 * @date 2017年10月26日 下午3:39:48
 */
@Controller
public class MainController extends BaseController{
	
	@Autowired
	private MainService mainService;
	
	@RequestMapping(value="/v*/home/homepage", method = RequestMethod.POST)
	@ResponseBody
	public Result<HomePage> getHomePage(@RequestParam String token){
		Result<HomePage> result = new Result<>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		String uid = this.checkToken(token);
		result.setResult(mainService.getPage(uid));
		return result;
	}
	
	
	@RequestMapping(value="/v*/home/pastlottery", method = RequestMethod.POST)
	@ResponseBody
	public Result<List<LotteryPast>> getPast(){
		Result<List<LotteryPast>> result = new Result<>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		result.setResult(mainService.gePasts());
		return result;
	}

    /**
     * 首页
     * @param token
     * @return
     */
    @RequestMapping(value = "/v*/home", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public Result<JSONObject> queryHome(String token) {
	    String uid = null;
        if (StringUtils.isNotBlank(token)) {
            uid = this.checkToken(token);
        }
	    Result<JSONObject> result = new Result<>();
	    result.setResult(mainService.queryHome(uid));
	    return result;
    }

}
