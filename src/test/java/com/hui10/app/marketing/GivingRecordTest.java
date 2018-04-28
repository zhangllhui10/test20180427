package com.hui10.app.marketing;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hui10.app.TestBase;
import com.hui10.app.service.marketing.GivingRecordService;
import com.hui10.common.utils.uuid.UUIDUtil;

public class GivingRecordTest extends TestBase{
	
	@Autowired
	private GivingRecordService givingRecordService;
	
	
	@Test
	public void test1() {
		
		givingRecordService.createGivingRecord("2017022414004178258618888", "201803100446287615360000005270", null, 
				null, null, "10001", 10000, "1101", "UH99111371","1111");
		
	}
	
	@Test
	public void test2() {
		givingRecordService.receivingUpdate("2017022414004178258618888", "201804032325223223000000001201", UUIDUtil.getUUID());
	}
	
	@Test
	public void test3() {
		givingRecordService.updateExpireTickets();
	}

}
