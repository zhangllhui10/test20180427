package com.hui10.app.service.marketing;

import java.util.List;

import com.hui10.app.model.marketing.LotteryGivingRecordInfo;

public interface GivingRecordService {
	
	/**
	 * 创建投注记录
	 * @param uid
	 * @param channelid
	 * @param acquireno
	 * @param serialno
	 * @param stationno
	 * @param lotterycode
	 * @param money
	 * @param cityid
	 * @param merchantno
	 * @param orderno
	 * @return
	 */
	List<LotteryGivingRecordInfo> createGivingRecord(String uid, String channelid, String acquireno, String serialno, String stationno, String lotterycode, long money, String cityid, String merchantno, String orderno);
	
	/**
	 * 领取彩票,更新赠送记录
	 * @param uid
	 * @param recordid
	 * @param orderid
	 * @return lotteryGivingRecordInfo
	 */
	LotteryGivingRecordInfo receivingUpdate(String uid, String recordid, String orderid);
	
	/**
	 * 
	 * @param recordid
	 * @return
	 */
	public LotteryGivingRecordInfo queryLotteryGivingRecord(String recordid);
	/**
	 * 更新过期未领取的记录
	 * @param status
	 * @return
	 */
	int updateExpireTickets();
	
	/**
	 * 
	 * @param recordid
	 * @param status
	 * @return
	 */
	int updateGivingRecordStatus(String recordid, String status);
	
	
	/**
	 * 查询今日已经赠送的彩票个数
	 * @param uid
	 * @param marketingid
	 * @return
	 */
	int userDaySendedLotteryNumber(String uid, String marketingid);
	
	/**
	 * 
	 * @param uid
	 * @param recordid
	 * @param orderid
	 * @param betdetail
	 * @return
	 */
	int updateGivingRecord(String uid, String recordid, String orderid, String betdetail);
	
	/**
	 * 
	 * @param uid
	 * @param recordid
	 * @param orderid
	 * @param betdetail
	 * @param receivenum
	 * @return
	 */
	int updateGivingRecordAndMarketing(String uid, String recordid, String orderid, String betdetail, int receivenum);
	
}	
