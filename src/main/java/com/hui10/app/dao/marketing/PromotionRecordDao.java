package com.hui10.app.dao.marketing;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.marketing.LotteryGivingRecordInfo;
import com.hui10.app.model.marketing.LotteryReceiveRecordInfo;

public interface PromotionRecordDao {
	
	int addLotteryGivingRecord(LotteryGivingRecordInfo lotteryGivingRecordInfo);
	
	int addBatchGivingRecords(@Param("list")List<LotteryGivingRecordInfo> list);
	
	int modifyLotteryGivingRecord(LotteryGivingRecordInfo lotteryGivingRecordInfo);
	
	LotteryGivingRecordInfo queryLotteryGivingRecord(@Param("id") String id);
	
	int updateExpireTickets();
	
	int updateGivingRecordStatus(@Param("recordid")String recordid, @Param("status")String status);
	
	List<LotteryGivingRecordInfo> queryLotteryGivingRecordList(@Param("marketingid") String marketingid);
	
	int userDaySendedLotteryNumber(@Param("uid")String uid, @Param("marketingid")String marketingid, @Param("daystart")Date daystart,
			@Param("dayend")Date dayend);
	
	/**
	 * 
	 * @param uid
	 * @param recordid
	 * @param orderid
	 * @param betdetail
	 * @return
	 */
	int updateGivingRecord(@Param("uid")String uid, @Param("recordid")String recordid, @Param("orderid")String orderid,
			@Param("betdetail")String betdetail);
	
	/**
	 * 
	 * @param uid
	 * @param recordid
	 * @param orderid
	 * @param betdetail
	 * @param receivenum
	 * @return
	 */
	int updateGivingRecordAndMarketing(@Param("uid")String uid, @Param("recordid")String recordid, 
			@Param("orderid")String orderid, @Param("betdetail")String betdetail, @Param("receivenum")int receivenum);
	
	/**
	 * 查询未领取的彩票
	 * @param uid
	 * @return
	 */
	List<LotteryGivingRecordInfo> findNotReceivedRecords(@Param("uid")String uid);
	
	/**
	 * 查询领取的彩票
	 * @param uid
	 * @return
	 */
	List<LotteryReceiveRecordInfo> findReceivedLotteries(@Param("uid")String uid, @Param("index")Integer index, @Param("offset")Integer offset, @Param("winstatus")String winstatus);
	
	/**
	 * 查询数量
	 * @param uid
	 * @return
	 */
	int findReceivedLotteriesCount(@Param("uid")String uid);
}