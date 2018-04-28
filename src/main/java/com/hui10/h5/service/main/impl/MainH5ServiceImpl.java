package com.hui10.h5.service.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.model.enums.LotteryStatusEnum;
import com.hui10.app.model.order.enums.OrderStatusEnum;
import com.hui10.app.service.banner.BannerService;
import com.hui10.app.service.user.UserOrderService;
import com.hui10.h5.service.main.MainH5Service;

/**
 * @ClassName: MainH5ServiceImpl.java
 * @Description:
 * @author zhangll
 * @date 2018年1月23日 上午10:51:39
 */
@Service
public class MainH5ServiceImpl implements MainH5Service {
	
	private static final int pagesize = 2;
	private static final String service_phone = "400-9100-005";
	@Autowired
	private BannerService bannerService;
	@Autowired
	private UserOrderService userOrderService;
	@Override
	public JSONObject homePage(String uid) {
		JSONObject result = new JSONObject();
		result.put("bannerlist", bannerService.queryBannerList(null));
		result.put("noopenorder", userOrderService.queryOrders(uid, null, null, LotteryStatusEnum.NO_OPEN.getCode(), pagesize));
		result.put("havaopenorder", userOrderService.queryOrders(uid, null, null, LotteryStatusEnum.HAVE_OPE.getCode(), pagesize));
		result.put("nopayorder", userOrderService.queryOrders(uid, null, OrderStatusEnum.NOPAY.getState(), null, pagesize));
		result.put("servicephone", service_phone);
		return result;
	}

}
