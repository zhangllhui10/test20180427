package com.hui10.app.marketing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hui10.app.TestBase;
import com.hui10.app.dao.marketing.UnionLottoPoolDao;
import com.hui10.app.model.marketing.UnionLottoPoolTicket;
import com.hui10.app.service.marketing.GivingRecordService;
import com.hui10.app.service.marketing.LotteryPoolService;
import com.hui10.common.utils.uuid.UUIDUtil;

public class LotteryPoolTest extends TestBase{
	
	@Autowired
	private GivingRecordService givingRecordService;
	
	@Resource(name="unionLottoPoolService")
	private LotteryPoolService lotteryPoolService;
	
	@Autowired
	private UnionLottoPoolDao unionLottoPoolDao;
	
	@Test
	public void test1() {
		givingRecordService.createGivingRecord("17888845746", null, null, null, null, "99998", 100, null, null, "11111");
	}
	
	@Test
	public void test2() {
		lotteryPoolService.initLotteryPool(UUIDUtil.getUUID());
		
		try {
			Thread.sleep(2000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("=============finish==========");
	}
	
	@Test
	public void test3() {
		
		List<UnionLottoPoolTicket> list = new ArrayList<>();
		
		UnionLottoPoolTicket ticket = new UnionLottoPoolTicket();
		
		ticket.setMarketingid(UUIDUtil.getUUID());
		ticket.setBetdetail("123");
		ticket.setSequence(1);
		ticket.setTicketid(UUIDUtil.getUUID());
		ticket.setTicketstatus("0");
		list.add(ticket);
		unionLottoPoolDao.addLottoTicketToPool(list);
	}

}
