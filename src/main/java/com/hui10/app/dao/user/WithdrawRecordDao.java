package com.hui10.app.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.user.WithdrawRecord;

/**
 * @ClassName: UserWithdrawRecordDao.java
 * @Description:
 * @author wengf
 * @date 2017年10月19日 上午10:31:51
 */
public interface WithdrawRecordDao {
	
	public int saveRecord(WithdrawRecord record);
	
	public int updateRecord(WithdrawRecord record);
	
	public WithdrawRecord selectRecord(String id);
	
	public List<WithdrawRecord> queryDoingPayOrder(@Param("status")String status);

}
