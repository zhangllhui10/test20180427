package com.hui10.app.service.prize;

import java.util.Map;

import com.hui10.app.common.lottery.HttpResult;
import com.hui10.app.model.order.LotteryOrder;

/**
 * @ClassName: PrizeService.java
 * @Description:
 * @author zhangll
 * @date 2017年10月18日 上午10:13:48
 */
public interface LotteryPrizeService {

	public HttpResult<String> dealWinPrizeFile(Map<String,Object> map);
	/**
	 * 处理中奖订单
	 * @param gamecode 彩种
	 * @param issuenumber 期号
	 * @param lotterytime 开奖时间
	 * @param filecheckcode 文件MD5值
	 * @param filename 文件名
	 * @param localPath  本地文件所在路径
	 * @user zhangll
	 * @date 2018年3月19日 下午3:08:27
	 */
	public void treatePrize_Order(String gamecode, String issuenumber, Long lotterytime, String filecheckcode, String filename, String localPath,String market,String promotionid);
	
	public LotteryOrder selectPrizeOrder(String orderid, String uid);
	
	public LotteryOrder selectAdutingOrder(String orderid);
	/**
	 * 定时任务去下载开奖文件
	 * 
	 * @user zhangll
	 * @date 2017年11月10日 下午3:39:01
	 */
	public void winPrizeNotifyJob();
	
	public HttpResult<String> dealWinPrizeFileMarket(Map<String,Object> map);
}
