package com.hui10.h5.controller.order;

import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.order.enums.SourceEnum;
import com.hui10.app.service.order.LotteryOrderSerice;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.model.common.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @email mudouyu@aliyun.com
 * @create 2018-01-22 15:20
 **/
@RestController
@RequestMapping(value = "/h5/order")
public class OrderH5Controller extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LotteryOrderSerice lotteryOrderSerice;

    @RequestMapping(value = {"generateOrder", "generateorder"})
    public Result generateOrderByH5(String token, String issuenumber, String gamecode, String betrequestarray) {
        logger.debug("request params :token={} ,issuenumber={},gamecode={},betrequestarray={}", token, issuenumber, gamecode, betrequestarray);
        if (StringUtils.isEmpty(issuenumber) || StringUtils.isEmpty(gamecode) || StringUtils.isEmpty(betrequestarray)) {
            return new Result<>(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR), false);
        }
        String uid = checkHuicardUserToken(token);
        Result result = new Result();
        Map<String, Object> resultMap = lotteryOrderSerice.generateOrder(issuenumber, gamecode, betrequestarray, uid, SourceEnum.H5.getState());
        result.setResult(resultMap.get("orderid").toString());
        return result;
    }

    @RequestMapping(value = {"queryOnSaleIssue", "queryonsaleissue"})
    public Result queryOnSaleIssueByApp(String token,String gamecodes) {
        logger.debug("request params :token={}",token);
        String uid = checkHuicardUserToken(token);
        Result result = new Result();
        result.setResult(lotteryOrderSerice.queryOnSaleIssueByApp(gamecodes));
        return result;
    }



}
