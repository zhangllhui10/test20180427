package com.hui10.app.user;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hui10.app.TestBase;
import com.hui10.app.dao.user.WithdrawRecordDao;
import com.hui10.app.model.user.WithdrawRecord;

/**
 * @ClassName: WithDrawRecordDaoTest.java
 * @Description:
 * @author wengf
 * @date 2017年10月19日 上午10:56:29
 */
public class WithDrawRecordDaoTest extends TestBase{
	
	
	@Autowired
	private WithdrawRecordDao withdrawRecordDao;
	
	@Test
	public void test00001_update(){
		WithdrawRecord record = new WithdrawRecord();
		record.setId("testid");
		record.setCash(1d);
		record.setUid("testuid");
		record.setPayeebank("testbank");
		record.setPayeename("testname");
		record.setPayeeno("testno");
		record.setFee(200d);
		record.setStatus("1");
		record.setCreatetime(new Date());
		withdrawRecordDao.saveRecord(record);
		record.setUpdatetime(new Date());
		record.setCash(1200d);
		withdrawRecordDao.updateRecord(record);
	}

}
