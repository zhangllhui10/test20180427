package com.hui10.app.dao.user;

import java.util.List;

import com.hui10.app.model.user.UserOrder;

/**
 * @ClassName: UserOrderDao.java
 * @Description:
 * @author wengf
 * @date 2017年10月18日 下午2:57:39
 */
public interface UserOrderDao {
	
	public int saveUserOrder(UserOrder userOrder);
	
	
	List<UserOrder> selectByOrderid(String orderid);
	
	public String queryPhoneByOrderid(String orderid);

}
