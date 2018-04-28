package com.hui10.app.controller.prize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.user.BigprizeHandle;
import com.hui10.app.model.user.MediumHandle;
import com.hui10.app.service.user.BigprizeHandleService;
import com.hui10.app.service.user.WithDrawService;
import com.hui10.common.constants.ICommon;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.model.common.Result;

/**
 * @ClassName: WithDrawController.java
 * @Description:
 * @author wengf
 * @date 2017年10月19日 下午4:06:52
 */
@Controller
public class WithDrawController extends BaseController {
	
	@Autowired
	private WithDrawService withDrawService;
	
	@Autowired
	private BigprizeHandleService bigprizeHandleService;
	
	@ResponseBody
    @RequestMapping(value = "/v*/user/order/withdraw", method = RequestMethod.POST)
	public Result<Integer> withDraw(@RequestParam String token, String orderid, @RequestParam String bankcardid){
		Result<Integer> result = new Result<Integer>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		String uid = this.checkToken(token);
		result.setResult(withDrawService.withDraw(uid, orderid, bankcardid));
		return result;
	}
	
	@ResponseBody
    @RequestMapping(value = "/v*/manager/order/statushandle", method = RequestMethod.POST)
	public Result<Integer> handlStatus(@RequestParam String token, MediumHandle mediumHandle, MultipartFile picture){
		Result<Integer> result = new Result<Integer>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		String userName = this.checkAdminToken(token);
		result.setResult(withDrawService.auditMediumHandle(userName, mediumHandle, picture));
		return result;
	}
	
	@ResponseBody
    @RequestMapping(value = "/v*/manager/order/bigprizhandle", method = RequestMethod.POST)
	public Result<Integer> bigPizeHandle(@RequestParam String token, @RequestParam MultipartFile screenshortfile, MultipartFile photofile, BigprizeHandle handle){
		Result<Integer> result = new Result<Integer>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		String uid = this.checkAdminToken(token);
		result.setResult(bigprizeHandleService.saveHandle(uid, screenshortfile, photofile, handle));
		return result;
	}

}
