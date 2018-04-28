package com.hui10.app.dao.prize;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.order.LotteryOrder;
import com.hui10.app.model.prize.LotteryPrizeFileContent;
import com.hui10.app.model.prize.LotteryPrizeFileInfo;

/**
 * @ClassName: PrizeDao.java
 * @Description:
 * @author zhangll
 * @date 2017年10月18日 上午11:50:16
 */
public interface PrizeDao {
	
	public Integer insertPrizeFile(LotteryPrizeFileInfo lotteryPrizeFileInfo);
	
	public List<LotteryPrizeFileInfo> queryPrizeFileList(@Param("status") String status);
	
	public Integer updatePrizeFile(LotteryPrizeFileInfo lotteryPrizeFileInfo);
	
	public LotteryPrizeFileInfo queryPrizeFileByCode(@Param("gamecode") String gamecode,@Param("issuenumber") String issuenumber,@Param("filecheckcode") String filecheckcode);
	
	public Integer updateLotteryOrder(List<LotteryPrizeFileContent> lotteryPrizeFileContent);
	
	public long getPrizeAmount(String orderid);
	
	public int updateBonusStatus(@Param("bonusstatus")String bonusstatus,@Param("orderid")String orderid, @Param("updatedate")Date updatedate, @Param("sendprize")Long sendprize);
	
	public LotteryOrder selectPrizeOrder(@Param("orderid")String orderid, @Param("uid")String uid);
	
	public LotteryOrder selectAuditingOrder(@Param("orderid")String orderid);
	
	public List<String> queryOrderNoByOrderNoList(List<LotteryPrizeFileContent> lotteryPrizeFileContent);
	
	public int queryPrizeCount(@Param("uid")String uid);

	/**
	 * 
	 * @param gamecode
	 * @param issuenumber
	 * @param lotterystatus
	 * @param ordertype
	 *            订单类型
	 * @return
	 * @user zhangll
	 * @date 2018年4月8日 上午11:32:06
	 */
	public Integer updateLotteryStatus(@Param("gamecode") String gamecode, @Param("issuenumber") String issuenumber,
			@Param("lotterystatus") String lotterystatus, @Param("ordertype") String ordertype);

	/**
	 * 更新用户中奖金额
	 * 
	 * @param lotteryPrizeList
	 * @user hui10
	 * @date 2018年3月13日 下午1:22:17
	 */
	public Integer updateUserWinamount(List<LotteryPrizeFileContent> lotteryPrizeList);
	/**
	 * @param orderno 英泰的投注流水号
	 * @return
	 * @user zhangll
	 * @date 2018年3月19日 下午3:49:13
	 */
	public Map<String,String> queryInfoByOrderno(@Param("orderno")String orderno);
	
	public List<Map<String,String>> queryInfoListByOrderno(@Param("list")List<String> orderno);
	
	public LotteryPrizeFileInfo queryPrizeFileByCodeAndPromotionid(@Param("gamecode") String gamecode,@Param("issuenumber") String issuenumber,@Param("filecheckcode") String filecheckcode,@Param("promotionid")String promotionid);
}
