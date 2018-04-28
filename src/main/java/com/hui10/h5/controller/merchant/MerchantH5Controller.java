package com.hui10.h5.controller.merchant;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hui10.app.common.web.BaseController;
import com.hui10.app.service.merchant.MerchantInfoService;
import com.hui10.model.common.Result;

@Controller
public class MerchantH5Controller extends BaseController{
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	/**
     * 查询银行卡列表
     * @param token
     * @return
     */
    @RequestMapping(value = "/v*/h5/merchant/nearby", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<Map<String, String>>> findNearByMerchants(@RequestParam String token, @RequestParam Double longitude, @RequestParam Double latitude) {
        //this.checkToken(token);
        Result<List<Map<String, String>>> result = new Result<>();
        result.setResult(merchantInfoService.findMerchantsNearBy(longitude, latitude));
        return result;

    }
}
