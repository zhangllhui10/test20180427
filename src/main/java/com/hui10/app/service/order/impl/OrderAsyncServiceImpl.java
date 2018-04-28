package com.hui10.app.service.order.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hui10.app.common.pay.AmountUtils;
import com.hui10.app.common.pay.PaySdkUtil;
import com.hui10.app.dao.order.LotteryOrderDao;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.service.order.OrderAsyncService;
import com.hui10.app.service.user.UserInfoService;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-22 14:54
 **/
@Service
public class OrderAsyncServiceImpl implements OrderAsyncService {


    private Logger logger = LoggerFactory.getLogger(OrderAsyncServiceImpl.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");


//    private static String DOWNLOAD_URL = PropertiesUtils.get("DOWNLOAD_URL");


//    @Autowired
//    private UserOrderService userOrderService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private LotteryOrderDao lotteryOrderDao;

    @Override
    @Async
    public void uplNotify(LotteryOrder lotteryOrder) {

        //短信通知 2017-12-05
//        try {
//            String phone = userOrderService.queryPhoneByOrderid(lotteryOrder.getOrderid());
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("order", DOWNLOAD_URL);
//            SendMsgUtils.send(phone, SmsConstants.SMS_SUCCESS_TICKET, params);
//        } catch (Exception e) {
//            logger.error("根据订单获取手机号,发送验证码信息错误:{}", e.getMessage());
//        }

        Map<String, String> params = new HashMap<>(20);
        params.put("sp_id", lotteryOrder.getSpid());
        params.put("sp_order_number", lotteryOrder.getOrderid());
        params.put("order_amount", AmountUtils.changeF2Y(Long.parseLong(lotteryOrder.getOrderamount() + "")));
        //TODO 彩票内容提供方（汇拾）在收单机构的商户号（对账使用）
        params.put("channel_merc_id", lotteryOrder.getChannelmercid());
        params.put("channel_trade_number", lotteryOrder.getOuttradeno());
        params.put("channel_pay_time", sdf.format(lotteryOrder.getPaytime()));
        //TODO 以收单机构提供的清算日期为准
        params.put("channel_settle_date", sdfDate.format(lotteryOrder.getPaytime()));
        params.put("station_provice_code", lotteryOrder.getStationprovince());
        params.put("lottery_type", lotteryOrder.getGamecode());
        //TODO 收单机构id（统计分润使用）
        params.put("channel_id", lotteryOrder.getAcquirerno());
        params.put("lottery_merc_id", lotteryOrder.getMerchantno());
        params.put("trade_status", "SUCCESS");

        String result = null;

        int num = 0;
        int times = 3;
        while (num < times) {
            try {
                result = PaySdkUtil.uplNotify(params);
                if ("SUCCESS".equalsIgnoreCase(result)) {
                    break;
                }
                num++;
            } catch (Exception e) {
                logger.error("通知聚合支付错误：{}", e.getMessage());
                num++;
                result = PaySdkUtil.uplNotify(params);
                if ("SUCCESS".equalsIgnoreCase(result)) {
                    break;
                }
            }
        }


    }

    @Override
    @Async
    public void userNotify(String userphone, String orderId) {

        try {
            //用户注册校验获取uid  2017-12-05
            String uid = userInfoService.betOrderGetUserUidByPhone(userphone, null);
            /**
             * 订单关联
             */
           /* UserOrder userOrder = new UserOrder();
            userOrder.setOrderid(orderId);
            userOrder.setUid(uid);
            userOrder.setCreatetime(new Date());
            userOrder.setId(UUIDUtil.getUUID());
            userOrderService.saveUserOrder(userOrder);*/

            LotteryOrder lotteryOrder = new LotteryOrder();
            lotteryOrder.setOrderid(orderId);
            lotteryOrder.setUid(uid);
            lotteryOrderDao.update(lotteryOrder);
        } catch (Exception e) {
            logger.error("异步绑定关系出现异常:{}", e.getMessage());
        }
    }
}
