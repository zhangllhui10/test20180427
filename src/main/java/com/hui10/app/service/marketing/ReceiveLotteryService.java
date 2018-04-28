package com.hui10.app.service.marketing;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.model.marketing.LotteryGivingRecordInfo;
import com.hui10.app.model.marketing.LotteryReceiveRecordVo;

/**
 * @ClassName: ReceiveLotteryService.java
 * @Description:
 * @author huangrui
 * @date 2018年3月8日 10:16:09
 */
public interface ReceiveLotteryService {
	
	/**
	 * 查找未领取的彩票
	 * @param uid
	 * @return
	 */
	public List<LotteryGivingRecordInfo> findNotReceivedLotteries(String uid);
	
	/**
	 * 生成订单
	 * @param lotteryGivingRecord
	 */
	public void generateGivingOrder(LotteryGivingRecordInfo lotteryGivingRecord);
	
	/**
	 * 查找领取的彩票
	 * @param uid
	 * @param pageno
	 * @param pagesize
	 * @param winstatus 
	 * @return
	 */
	public LotteryReceiveRecordVo findReceivedLotteries(String uid, Integer pageno, Integer pagesize, String winstatus);
	
	public Map<String, Object> findMarketingPoolAndCarousel();

	/**
     * 领取彩票
     * @param id 增彩记录ID
     * @param uid 用户UID
     */
	public String receiveLottery(String id, String uid);
	
	/**
     * 营销活动投注结果通知
     */
	public void updateOrderStatus(JSONObject transData);
	
}
