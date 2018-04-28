package com.hui10.pc.controller.pcorder;

import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.web.BasePCController;
import com.hui10.app.model.order.OrderInfoDto;
import com.hui10.app.service.order.PcOrderService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.exception.CommonException;
import com.hui10.model.common.Result;
import com.poslot.model.lottery.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @email mudouyu@aliyun.com
 * @create 2018-04-17 15:14
 **/
@RestController
public class PCOrderController extends BasePCController{


    @Autowired
    private PcOrderService pcOrderService;

    @RequestMapping(value = "/pc/order/query")
    public Result queryOrderList(String token, String merchantno, Date begin, Date end, Integer pagesize, Integer pageno, String source, String gamecode) {

        this.checkMerchantToken(token);

        if (null == begin || null == end || pagesize == null || pageno == null) {
            return new Result<>(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR), false);
        }
        /**
         * 查询总数
         */
        Integer count = pcOrderService.queryOrderCount(merchantno, begin, end, source, gamecode);
        /**
         * 查询分页列表
         */
        List<OrderInfoDto> orderList = pcOrderService.queryOrderList(merchantno, begin, end, pagesize, pageno, source, gamecode);
        /**
         * 查询总金额
         */
        Long orderSumAmount = pcOrderService.queryOrderSumAmount(merchantno, begin, end, source, gamecode);

        Map<String, Object> resultData = new HashMap(3);
        resultData.put("count", count);
        resultData.put("list", orderList);
        resultData.put("sumamount", orderSumAmount);

        Result result = new Result();
        result.setResult(resultData);

        return result;
    }


//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
//            @Override
//            public void setAsText(String value) {
//                try {
//                    if (value != null && !value.trim().equals("")) {
//                        setValue(new Date(Long.valueOf(value)));
//                    }else {
//                        setValue(null);
//                    }
//                } catch (Exception e) {
//                    setValue(null);
//                    throw new CommonException(com.hui10.common.constants.ICommon.DATE_FORMAT_ERROR, PropertiesUtils.get(com.hui10.common.constants.ICommon.DATE_FORMAT_ERROR));
//                }
//            }
//        });
//    }

}
