package com.hui10.app.dao.adnormalorder;

import org.apache.ibatis.annotations.Param;
import com.hui10.app.model.adnormalorder.AdnormalOrderInfo;
import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.user.UserBankCard;

/**
 * @ClassName: AdnormalOrderDao.java
 * @Description:
 * @author huangrui
 * @date 2018/4/3  15:19:21
 */
public interface AdnormalOrderDao {
	
	public LotteryOrder queryLotteryOrderInfo(@Param("orderid")String orderid, @Param("userphone")String userphone);
	
	public UserBankCard queryUserBankCard(@Param("bankcardid")String bankcardid, @Param("userphone")String userphone);

	public int insert(AdnormalOrderInfo adnormalOrderInfo);
	
	public int update(AdnormalOrderInfo adnormalOrderInfo);
	
	public AdnormalOrderInfo queryAdnormalOrderById(@Param("id")String id);
	
	public AdnormalOrderInfo queryAdnormalOrderByOuttradeno(@Param("outtradeno")String outtradeno);
}
