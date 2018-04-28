package com.hui10.app.service.marketing.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.utils.UnionLottoPoolGeneratorUtil;
import com.hui10.app.dao.marketing.UnionLottoPoolDao;
import com.hui10.app.model.lottery.enums.LotteryCodeEnum;
import com.hui10.app.model.marketing.UnionLottoPoolTicket;
import com.hui10.app.model.marketing.enums.LotteryPoolTicketStatusEnum;
import com.hui10.app.service.marketing.LotteryPoolService;
import com.hui10.common.utils.uuid.UUIDUtil;

@Component("unionLottoPoolService")
public class UnionLottoPoolServiceImpl implements LotteryPoolService{
	
	@Autowired
	private UnionLottoPoolDao unionLottoPoolDao;
	
	@Override
	public void initLotteryPool(String marketingid) {
		AtomicInteger sequence = new AtomicInteger(0);
		ExecutorService executorService = Executors.newFixedThreadPool(16);
		//获取红球
		UnionLottoPoolGeneratorUtil.generateLottoRed();
		LinkedList<String> reds = UnionLottoPoolGeneratorUtil.getLottoRed();
		if (null == reds) {
			return;
		}
		for (int i = 0; i< 16; i++) {
			final int index = i;
			executorService.execute(new Runnable() {
				
				@Override
				public void run() {
					int size = 2 << 10;
					List<UnionLottoPoolTicket> tickets = new ArrayList<>(size);
					int num = reds.size() % size == 0 ? reds.size() / size : reds.size() / size + 1;
					int count = 0;
					for (String red : reds) {
						JSONObject betdetail = new JSONObject();
						betdetail.put("red", red);
						betdetail.put("blue", index >= 10 ? String.valueOf(index) : "0"+String.valueOf(index));
						betdetail.put("lotterycode", LotteryCodeEnum.UNIONLOTTO.getState());
						betdetail.put("bettype", "1000101");
						betdetail.put("multiple", "1");
						
						UnionLottoPoolTicket unionLottoPoolTicket = new UnionLottoPoolTicket();
						unionLottoPoolTicket.setBetdetail(betdetail.toJSONString());
						unionLottoPoolTicket.setMarketingid(marketingid);
						unionLottoPoolTicket.setTicketid(UUIDUtil.getUUID());
						unionLottoPoolTicket.setTicketstatus(LotteryPoolTicketStatusEnum.STILL_IN_POOL.getCode());
						unionLottoPoolTicket.setSequence(sequence.addAndGet(1));
						tickets.add(unionLottoPoolTicket);
						
						if (tickets.size() == (2 << 10)) {
							unionLottoPoolDao.addLottoTicketToPool(tickets);
							tickets.clear();
							count ++;
						}else {
							if (count == (num - 1)) {
								unionLottoPoolDao.addLottoTicketToPool(tickets);
								tickets.clear();
							}
						}
					}
				}
			});
		}
		executorService.shutdown();
	}
		


	@Override
	public int updatePoolTicketStatus(String ticketid, String ticketstatus) {
		return unionLottoPoolDao.updatePoolTicketStatus(ticketid, ticketstatus);
	}
	
	@Override
	public int updatePoolExpireTicket(String ticketstatus, String status) {
		return unionLottoPoolDao.updatePoolExpireTicket(ticketstatus, status);
	}
	
	@Override
	public UnionLottoPoolTicket pullLotteryTicket() {
		return unionLottoPoolDao.pullLotteryTicket();
	}

}
