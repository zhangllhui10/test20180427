package com.hui10.app.controller.merchant;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hui10.app.common.web.BaseController;
import com.hui10.app.service.merchant.MerchantInfoService;
import com.hui10.common.constants.ICommon;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.model.common.Result;


@Controller
public class MerchantController extends BaseController{
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	
	
	/**
	 * 调用方：前置系统
	 * 同步新增渠道商户入网申请
	 * @param authorization
	 * @param paramsBody
	 * @return
	 * @user zhangll
	 * @date 2017年10月30日 下午4:50:45
	 */
	@RequestMapping(value = "/merchant/apply/add",produces = { "application/json;charset=UTF-8" },method = RequestMethod.POST)
	@ResponseBody
	public Result<String> createMerchantApply(@RequestHeader(name = "Authorization") String authorization, @RequestBody String paramsBody) {
		Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		merchantInfoService.createMerchantApply(authorization, paramsBody);
		return result;
	}
	/**
	 * 调用方：前置系统
	 * 同步商户终端增加和删除
	 * @param authorization
	 * @param paramsBody
	 * @return
	 * @user zhangll
	 * @date 2017年11月2日 下午2:30:17
	 */
	@RequestMapping(value = "/merchant/terminal/manage",produces = { "application/json;charset=UTF-8" },method = RequestMethod.POST)
	@ResponseBody
	public Result<String> createMerchantTerminal(@RequestHeader(name = "Authorization") String authorization, @RequestBody String paramsBody) {
		Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		merchantInfoService.manageMerTerminal(authorization, paramsBody);
		return result;
	}
	
	/**
	 * 调用方：前置系统
	 * 同步修改渠道和渠道商户的销售状态
	 * @param authorization
	 * @param paramsBody
	 * @return
	 * @user zhangll
	 * @date 2017年11月3日 上午11:30:42
	 */
	@RequestMapping(value = "/apply/sale/status",produces = { "application/json;charset=UTF-8" },method = RequestMethod.POST)
	@ResponseBody
	public Result<String> updateApplySaleStatus(@RequestHeader(name = "Authorization") String authorization, @RequestBody String paramsBody) {
		Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		merchantInfoService.updateApplySaleStatus(authorization, paramsBody);
		return result;
	}
	
	
	/**
	 * 后台接口
	 * 审核渠道商户
	 * @param applyid
	 * @param merchantid
	 * @param lotterystatus
	 * @param lotteryreason
	 * @return
	 * @user zhangll
	 * @date 2017年10月30日 下午4:56:11
	 */
	@RequestMapping(value = "/v*/admin/merchant/check/merlotterystatus", method = RequestMethod.POST)
	@ResponseBody
	public  Result<String> checkMerchantLotteryStatus(String token,String applyid,String merchantid,String lotterystatus,String lotteryreason){
		Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		checkAdminToken(token);
		validateStringParameterNotBlank(applyid,merchantid,lotterystatus);
		merchantInfoService.checkMerLotteryStatus(applyid, merchantid, lotterystatus, lotteryreason);
		return result;
	}
	
	@RequestMapping(value = "/v*/admin/merchant/location",produces = { "application/json;charset=UTF-8" },method = RequestMethod.POST)
	@ResponseBody
	public Result<Integer> updateMerchantLocation(@RequestParam String token, @RequestParam String merchantid, @RequestParam Double longitude, @RequestParam Double latitude) {
		checkAdminToken(token);
		Result<Integer> result = new Result<>();
		result.setResult(merchantInfoService.updateLocation(merchantid, longitude, latitude));
		return result;
	}
	
	/**
     * 查询附近投注店
     * @param token
     * @param longitude 经度
     * @param latitude 纬度
     * @return
     */
    @RequestMapping(value = "/v*/app/merchant/nearby", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<Map<String, String>>> findNearByMerchants(@RequestParam Double longitude, @RequestParam Double latitude) {
        Result<List<Map<String, String>>> result = new Result<>();
        result.setResult(merchantInfoService.findMerchantsNearBy(longitude, latitude));
        return result;
    }
}
