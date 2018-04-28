package com.hui10.app.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.order.enums.LotteryGameCodeEnum;
import com.hui10.app.service.order.OrderQueryService;
import com.hui10.model.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2018年03月08日 10:58
 */
@Controller
public class OrderQueryController extends BaseController {

    @Autowired
    private OrderQueryService orderQueryService;

    /**
     * 订单列表头部分
     * @return
     */
    @RequestMapping(value = "/*/order/list/lottery", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public Result<JSONObject> queryHeadLottery() {
        Result<JSONObject> result = new Result<>();
        result.setResult(orderQueryService.getSaleIssueLottery(LotteryGameCodeEnum.DCB.getState()));
        return result;
    }

    /**
     * 订单列表
     * @param token
     * @return
     */
    @RequestMapping(value = "/v*/order/list", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public Result<JSONObject> queryHeadLottery(@RequestParam String token, @RequestParam String type, String lastorderid) {
        String uid = this.checkToken(token);
        Result<JSONObject> result = new Result<>();
        result.setResult(orderQueryService.queryOrderList(uid, type, lastorderid));
        return result;
    }

    /**
     * 订单详情
     * @param token
     * @return
     */
    @RequestMapping(value = "/v*/order/detail", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public Result<JSONObject> queryHeadLottery(@RequestParam String token, @RequestParam String orderid) {
        String uid = this.checkToken(token);
        Result<JSONObject> result = new Result<>();
        result.setResult(orderQueryService.queryOrderDetail(uid, orderid));
        return result;
    }

    /**
     * 订单状态
     * 提供给前端轮训查询
     * @param token
     * @return
     */
    @RequestMapping(value = "/v*/order/status", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public Result<String> queryOrderStatus(@RequestParam String token, @RequestParam String orderid) {
        String uid = this.checkToken(token);
        Result<String> result = new Result<>();
        result.setResult(orderQueryService.queryOrderStatus(uid, orderid));
        return result;
    }

    /**
     * 订单信息
     * @param token
     * @return
     */
    @RequestMapping(value = "/v*/order/payinfo", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public Result<JSONObject> queryOrderInfo(@RequestParam String token, @RequestParam String orderid) {
        String uid = this.checkToken(token);
        Result<JSONObject> result = new Result<>();
        result.setResult(orderQueryService.queryOrderInfo(uid, orderid));
        return result;
    }

    /**
     * 中大奖打印彩票
     * @param token
     * @return
     */
    @RequestMapping(value = "/v*/lottery/print", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> printTicket(@RequestParam String token, @RequestParam String orderid, @RequestParam String wifimac) {
        String uid = this.checkToken(token);
        Result<Boolean> result = new Result<>();
        result.setResult(orderQueryService.printBigPrizeLottery(uid, orderid, wifimac));
        return result;
    }
}
