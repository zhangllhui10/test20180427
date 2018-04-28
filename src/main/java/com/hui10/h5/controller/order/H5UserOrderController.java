package com.hui10.h5.controller.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.order.enums.OrderStatusEnum;
import com.hui10.app.service.user.UserOrderService;
import com.hui10.common.constants.ICommon;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.model.common.Result;

/**
 * @ClassName: UserOrderController.java
 * @Description:
 * @author wengf
 * @date 2018年1月22日 下午4:27:13
 */
@Controller
public class H5UserOrderController extends BaseController{
	
	@Autowired
	private UserOrderService userOrderService;
	
	 @ResponseBody
	    @RequestMapping(value="/h5/user/order/orders", method = RequestMethod.POST)
	    public Result<List<LotteryOrder>> queryOrders(@RequestParam String token, String orderid, @RequestParam()String status, @RequestParam(defaultValue="15") Integer pagesize){
	    	Result<List<LotteryOrder>> result = new Result<>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
	    	String uid = this.checkToken(token);
	    	result.setResult(userOrderService.queryOrders(uid, orderid, null, status, pagesize));
	    	return result;
	    }
	    
	    @ResponseBody
	    @RequestMapping(value="/h5/user/order/topayorders", method = RequestMethod.POST)
	    public Result<List<LotteryOrder>> queryToPayOrders(@RequestParam String token, String orderid,@RequestParam(defaultValue="15") Integer pagesize){
	    	Result<List<LotteryOrder>> result = new Result<>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
	    	String uid = this.checkToken(token);
	    	result.setResult(userOrderService.queryOrders(uid, orderid, OrderStatusEnum.NOPAY.getState(), null, pagesize));
	    	return result;
	    }

}
