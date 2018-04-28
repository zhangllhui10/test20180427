package com.hui10.app.controller.adnormalorder;

import com.hui10.app.common.web.BaseController;
import com.hui10.app.service.adnormalorder.AdnormalOrderSerice;
import com.hui10.model.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdnormalOrderController extends BaseController {

    @Autowired
    private AdnormalOrderSerice adnormalOrderSerice;

    /**
     * 新增异常订单
     * @param orderid 彩票订单号
     * @param outtradeno 支付流水号
     * @param userphone 用户手机号
     * @param bankcardid 用户银行卡绑定ID
     * @param token 
     * @return
     */
    @RequestMapping(value = "/order/adnormal/add", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<String> addAdnormalOrder(@RequestParam String token, @RequestParam String orderid, @RequestParam String outtradeno, 
			@RequestParam String userphone, String bankcardid) {
		Result<String> result = new Result<String>();
		String username = checkAdminToken(token);
		result.setResult(adnormalOrderSerice.addAdnormalOrder(orderid, outtradeno, userphone, bankcardid, username));
		return result;
	}
    
    /**
     * 处理异常订单
     * @param id 异常订单记录ID
     * @param bankcardid 用户银行卡绑定ID
     * @param amount 打款金额
     * @param status 状态（1-未支付，2-出票成功，3-取消, 4-已过期、5-出票失败）
     * @param token
     * @return
     */
    @RequestMapping(value = "/order/adnormal/modify", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<String> modifyAdnormalOrder(@RequestParam String token, @RequestParam String id, String bankcardid, 
			long amount, @RequestParam String status) {
		Result<String> result = new Result<String>();
		String username = checkAdminToken(token);
		result.setResult(adnormalOrderSerice.modifyAdnormalOrder(id, bankcardid, amount, status, username));
		return result;
	}
}
