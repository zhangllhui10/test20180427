package com.hui10.app.controller.order;

import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.web.BaseController;
import com.hui10.app.service.order.LotteryOrderSerice;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.model.common.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-17 17:41
 **/
@RestController
public class OrderController extends BaseController {


    @Autowired
    private LotteryOrderSerice lotteryOrderSerice;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 前置下单选号
     *
     * @return
     */
    @RequestMapping(value = {"/v*/unionpay/lottery/generateOrder", "/v*/unionpay/lottery/generateorder"})
    public Result<Object> pickLotteryOrder(@RequestHeader(name = "Authorization") String authorization, @RequestBody String paramsBody) {
        logger.debug("request params :authorization={} ,paramsBody={}", authorization, paramsBody);
        if (StringUtils.isEmpty(authorization) || StringUtils.isEmpty(paramsBody)) {
            return new Result<>(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR), false);
        }
        Result result = new Result();
        Map<String, Object> resultMap = lotteryOrderSerice.pickLotteryOrder(authorization, paramsBody);
        result.setResult(resultMap);
        return result;

    }

    /**
     * 前置投注通知
     *
     * @param authorization
     * @param paramsBody
     * @return
     */
    @RequestMapping(value = {"/v*/unionpay/lottery/completionNotify", "/v*/unionpay/lottery/completionnotify"})
    public Result betOrder(@RequestHeader(name = "Authorization") String authorization, @RequestBody String paramsBody) {
        logger.debug("request params :authorization={} ,paramsBody={}", authorization, paramsBody);
        if (StringUtils.isEmpty(authorization) || StringUtils.isEmpty(paramsBody)) {
            return new Result<>(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR), false);
        }
        Result result = new Result();
        Map<String, Object> resultMap = lotteryOrderSerice.betOrder(authorization, paramsBody);
        result.setResult(resultMap);
        return result;
    }

    /**
     * 前置查询通知
     *
     * @param authorization
     * @param paramsBody
     * @return
     */
    @RequestMapping(value = {"/v*/unionpay/lottery/queryCompletionNotify", "/v*/unionpay/lottery/querycompletionnotify"})
    public Result queryCompletionNotify(@RequestHeader(name = "Authorization") String authorization, @RequestBody String paramsBody) {
        logger.debug("request params :authorization={} ,paramsBody={}", authorization, paramsBody);

        if (StringUtils.isEmpty(authorization) || StringUtils.isEmpty(paramsBody)) {
            return new Result<>(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR), false);
        }
        Result result = new Result();
        Map<String, Object> resultMap = lotteryOrderSerice.queryCompletionNotify(authorization, paramsBody);
        result.setResult(resultMap);
        return result;
    }

    /**
     * 前置取消订单
     *
     * @param authorization
     * @param paramsBody
     * @return
     */
    @RequestMapping(value = {"/v*/unionpay/lottery/cancelOrder", "/v*/unionpay/lottery/cancelorder"})
    public Result cancelOrder(@RequestHeader(name = "Authorization") String authorization, @RequestBody String paramsBody) {
        logger.debug("request params :authorization={} ,paramsBody={}", authorization, paramsBody);

        if (StringUtils.isEmpty(authorization) || StringUtils.isEmpty(paramsBody)) {
            return new Result<>(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR), false);
        }
        Result result = new Result();
        Map<String, Object> resultMap = lotteryOrderSerice.cancelOrder(authorization, paramsBody);
        result.setResult(resultMap);
        return result;
    }

    /**
     * App 投注下单
     *
     * @param token
     * @param issuenumber
     * @param gamecode
     * @param betdetail
     * @return
     */
    @RequestMapping(value = {"/v*/app/lottery/generateOrderByApp", "/v*/app/lottery/generateorderbyapp"})
    public Result generateOrderByApp(String token, String issuenumber, String gamecode, String betdetail) {
        logger.debug("request params :token={} ,issuenumber={},gamecode={},betdetail={}", token, issuenumber, gamecode, betdetail);

        String uid = checkHuicardUserToken(token);
        if (StringUtils.isEmpty(issuenumber) || StringUtils.isEmpty(gamecode) || StringUtils.isEmpty(betdetail)) {
            return new Result<>(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR), false);
        }

        Result result = new Result();
        Map<String, Object> resultMap = lotteryOrderSerice.generateOrderByApp(issuenumber, gamecode, betdetail, uid);
        result.setResult(resultMap.get("orderid").toString());
        return result;
    }

    /**
     * 前置扫描二维码
     *
     * @param authorization
     * @param paramsBody
     * @return
     */
    @RequestMapping(value = {"/v*/unionpay/lottery/checkOrderInfo", "/v*/unionpay/lottery/checkorderinfo"})
    public Result checkOrderInfo(@RequestHeader(name = "Authorization") String authorization, @RequestBody String paramsBody) {
        logger.debug("request params :authorization={} ,paramsBody={}", authorization, paramsBody);

        if (StringUtils.isEmpty(authorization) || StringUtils.isEmpty(paramsBody)) {
            return new Result<>(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR), false);
        }
        Result result = new Result();
        Map<String, Object> resultMap = lotteryOrderSerice.checkOrderInfo(authorization, paramsBody);
        result.setResult(resultMap);
        return result;
    }

    /**
     * App 期号查询
     *
     * @param token
     * @return
     */
    @RequestMapping(value = {"v*/app/lottery/queryOnSaleIssue", "v*/app/lottery/queryonsaleissue"})
    public Result queryOnSaleIssueByApp(String token, String gamecodes) {
        String uid = checkHuicardUserToken(token);
        logger.debug("request params :token={}", token);
        Result result = new Result();
        result.setResult(lotteryOrderSerice.queryOnSaleIssueByApp(gamecodes));
        return result;
    }

    /**
     * 订单最终投注结果
     *
     * @param token
     * @param orderid
     * @return
     */
    @RequestMapping(value = {"/v*/app/lottery/channelOrderResult", "/v*/app/lottery/channelorderresult"})
    public Result channelOrderResult(String token, String orderid) {

        logger.debug("request params :token={},orderid={}", token, orderid);
        Result result = new Result();
        result.setResult(lotteryOrderSerice.channelOrderResult(orderid));
        return result;

    }

    /**
     * 前置查询期号
     *
     * @param authorization
     * @param paramsBody
     * @return
     */
    @RequestMapping(value = {"/v*/unionpay/lottery/queryOnSaleIssue", "/v*/unionpay/lottery/queryonsaleissue"})
    public Result queryOnSaleIssue(@RequestHeader(name = "Authorization") String authorization, @RequestBody String paramsBody) {
        logger.debug("request params :authorization={} ,paramsBody={}", authorization, paramsBody);
        if (StringUtils.isEmpty(authorization) || StringUtils.isEmpty(paramsBody)) {
            return new Result<>(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR), false);
        }

        Result result = new Result();
        result.setResult(lotteryOrderSerice.queryOnSaleIssue(authorization, paramsBody));
        return result;
    }

    /**
     * 同号投注下一期
     * @param token
     * @param orderid
     * @return
     */
    @RequestMapping(value = {"/v*/app/same/lottery/betting", "/v*/unionpay/lottery/queryonsaleissue"})
    public Result sameNumberBetting(@RequestParam String token, @RequestParam String orderid) {
        String uid = checkHuicardUserToken(token);
        Result result = new Result();
        result.setResult(lotteryOrderSerice.sameNumberBetting(uid, orderid));
        return result;
    }


}
