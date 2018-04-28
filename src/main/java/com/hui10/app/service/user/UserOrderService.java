package com.hui10.app.service.user;

import java.util.List;

import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.user.UserOrder;

/**
 * @ClassName: UserOrderService.java
 * @Description:
 * @author wengf
 * @date 2017年10月18日 下午3:00:04
 */
public interface UserOrderService {
	
	/**
	 * 保存用户订单
	 * @param userOrder
	 * @return
	 * @user wengf
	 * @date 2017年10月18日 下午3:01:14
	 */
	public int saveUserOrder(UserOrder userOrder);
	
	/**
	 * 关联订单
	 * @param uid
	 * @param onderno
	 * @return
	 * @user wengf
	 * @date 2017年10月18日 下午3:06:18
	 */
	public int bindOrder(String uid, String orderid);
	
	/**
	 * 
	 * @param uid
	 * @param orderid
	 * @param toPay 待支付
	 * @param lotterStatus 开奖状态
	 * @return
	 * @user wengf
	 * @date 2017年10月25日 上午11:25:14
	 */
	public List<LotteryOrder> queryOrders(String uid, String orderid, Integer toPay, String lotterStatus, Integer pagesize);
	
	/**
	 * @param orderid
	 * @return
	 * @user wengf
	 * @date 2017年12月4日 下午2:42:47
	 */
	public String queryPhoneByOrderid(String orderid);

}
