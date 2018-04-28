package com.hui10.app.service.user.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.encrypt.AesUtils;
import com.hui10.app.dao.order.LotteryOrderDao;
import com.hui10.app.dao.user.UserOrderDao;
import com.hui10.app.model.enums.LotteryStatusEnum;
import com.hui10.app.model.enums.PrizeBonusStatusEnum;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.order.enums.OrderStatusEnum;
import com.hui10.app.model.user.UserOrder;
import com.hui10.app.service.order.LotteryOrderSerice;
import com.hui10.app.service.user.UserOrderService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;

/**
 * @ClassName: UserOrderServiceImpl.java
 * @Description:
 * @author wengf
 * @date 2017年10月18日 下午3:04:08
 */
@Service
public class UserOrderServiceImpl implements UserOrderService {
	
	@Autowired
	private UserOrderDao userOrderDao;
	
	@Autowired
	private LotteryOrderSerice lotteryOrderSerice;
	
	@Autowired
	private LotteryOrderDao lotteryOrderDao;

	@Override
	public int saveUserOrder(UserOrder userOrder) {
		List<UserOrder> orders = userOrderDao.selectByOrderid(userOrder.getOrderid());
		if (orders.size() > 0) {
			return 1; 
		} 
		return userOrderDao.saveUserOrder(userOrder);
	}

	@Override
	public int bindOrder(String uid, String orderid) {
		List<UserOrder> orders = userOrderDao.selectByOrderid(orderid);
		if (orders.size() > 0) {
			return 1;
		}
		LotteryOrder order = lotteryOrderSerice.selectByOrderid(orderid);
		if (null == order) {
			throw new CommonException(ICommon.ORDER_NOTFOND_ERROR, PropertiesUtils.get(ICommon.ORDER_NOTFOND_ERROR));
		}
		UserOrder userOrder = new UserOrder();
		userOrder.setId(UUIDUtil.getUUID());
		userOrder.setUid(uid);
		userOrder.setOrderid(orderid);
		userOrder.setCreatetime(new Date());
		return saveUserOrder(userOrder);
	}

	@Override
	public List<LotteryOrder> queryOrders(String uid, String orderid, Integer toPay, String lotterStatus, Integer pagesize) {
		if (StringUtils.isNotBlank(lotterStatus) && null == LotteryStatusEnum.getDesc(lotterStatus)) {
			throw new CommonException(ICommon.LOTTERY_STATUS_ERROR, PropertiesUtils.get(ICommon.LOTTERY_STATUS_ERROR));
		}
		List<LotteryOrder> orders = lotteryOrderDao.queryOrders(uid, orderid, toPay, lotterStatus, pagesize);
		for (LotteryOrder order : orders) {
			String temid = order.getOrderid();
			String encryptid = AesUtils.encrypt(temid, PropertiesUtils.get("aes_key").trim());
			order.setEncryptorderid(encryptid);
			Date lotterytime = order.getLotterytime();
			Date now = new Date();
			if (now.after(lotterytime) && order.getStatus()==OrderStatusEnum.NOPAY.getState()) {
				order.setStatus(4);//   改为过期
			}
			//中奖但是未领取过期
			if (differentDays(lotterytime, now) >= 60 && order.getWinstatus() == 2 && order.getBonusstatus() == 1) {
				order.setBonusstatus(Integer.parseInt(PrizeBonusStatusEnum.DRAW_EXPIRE.getCode()));
			}
		}
		return orders;
	}
	
	/**
	 * date2比date1多的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			return day2 - day1;
		}
	}

	@Override
	public String queryPhoneByOrderid(String orderid) {
		String phone = userOrderDao.queryPhoneByOrderid(orderid);
		if (StringUtils.isBlank(phone)) {
			throw new CommonException(ICommon.PHONE_NOT_FONT, PropertiesUtils.get(ICommon.PHONE_NOT_FONT));
		}
		return phone;
	}


}
