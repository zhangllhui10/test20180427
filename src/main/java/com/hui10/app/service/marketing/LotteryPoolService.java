package com.hui10.app.service.marketing;

import com.hui10.app.model.marketing.UnionLottoPoolTicket;

public interface LotteryPoolService {
	
	/**
	 * 创建彩票池
	 * @param marketingid
	 * @return
	 */
	void initLotteryPool(String marketingid);
	
	/**
	 * 更新状态
	 * @param ticketid
	 * @param ticketstatus
	 * @return
	 */
	int updatePoolTicketStatus(String ticketid, String ticketstatus);
	
	/**
	 * 更新彩票池中过期未领取的彩票
	 * @param ticketstatus
	 * @param status
	 * @return
	 */
	int updatePoolExpireTicket(String ticketstatus, String status);
	
	/**
	 * 从池子里拿一注彩票
	 * @return
	 */
	UnionLottoPoolTicket pullLotteryTicket();
	
	
}
