package com.hui10.app.controller.user;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.order.enums.OrderStatusEnum;
import com.hui10.app.service.user.MediumHandleService;
import com.hui10.app.service.user.UserOrderService;
import com.hui10.common.constants.ICommon;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.model.common.Result;

/**
 * @ClassName: UserOrderController.java
 * @Description:
 * @author wengf
 * @date 2017年10月18日 下午2:23:27
 */
@Controller
public class UserOrderController extends BaseController {
	
	@Autowired
	private UserOrderService userOrderService;
	
	@Autowired
	private MediumHandleService mediumHandleService;
	
    @ResponseBody
    @RequestMapping(value = "/v*/user/order/bindorder", method = RequestMethod.POST)
	public Result<String> bindUserOrder(@RequestParam String token, @RequestParam String orderid){
		Result<String> result = new Result<>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
		String uid = this.checkToken(token);
		userOrderService.bindOrder(uid, orderid);
		result.setResult(PropertiesUtils.get(ICommon.SUCCESS));
		return result;
	}
    
    @ResponseBody
    @RequestMapping(value="/v*/user/order/orders", method = RequestMethod.POST)
    public Result<List<LotteryOrder>> queryOrders(@RequestParam String token, String orderid, @RequestParam()String status, @RequestParam(defaultValue="15") Integer pagesize){
    	Result<List<LotteryOrder>> result = new Result<>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
    	String uid = this.checkToken(token);
    	result.setResult(userOrderService.queryOrders(uid, orderid, null, status, pagesize));
    	return result;
    }
    
    @ResponseBody
    @RequestMapping(value="/v*/user/order/topayorders", method = RequestMethod.POST)
    public Result<List<LotteryOrder>> queryToPayOrders(@RequestParam String token, String orderid, @RequestParam(defaultValue="15") Integer pagesize){
    	Result<List<LotteryOrder>> result = new Result<>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
    	String uid = this.checkToken(token);
    	result.setResult(userOrderService.queryOrders(uid, orderid, OrderStatusEnum.NOPAY.getState(), null, pagesize));
    	return result;
    }
    
    @ResponseBody
    @RequestMapping(value="/v*/user/order/bindidcard", method = RequestMethod.POST)
    public Result<Integer> bindIdCard(@RequestParam String token, @RequestParam MultipartFile faceSide, @RequestParam MultipartFile backSide,@RequestParam String orderid,@RequestParam String name,@RequestParam String idcardno, @RequestParam String proxyname, @RequestParam String bankcardid) throws IOException{
    	Result<Integer> result = new Result<>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
    	String uid = this.checkToken(token);
    	result.setResult(mediumHandleService.bindIdCard(uid, orderid, faceSide, backSide, name, idcardno, proxyname, bankcardid));
    	return result;
    }
    
    @ResponseBody
    @RequestMapping(value ="/v*/user/order/cancel", method = RequestMethod.POST)
    public Result<Integer>  cancelAppOrder(@RequestParam String token, @RequestParam String orderid) {
    	Result<Integer> result = new Result<>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
        String uid = this.checkToken(token);
        int count =  mediumHandleService.cancelAppOrder(uid, orderid);
        result.setResult(count);
        return result;
    }

}
