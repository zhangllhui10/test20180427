package com.hui10.app.dao.acquirer;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.acquirer.AcquirerApply;
import com.hui10.app.model.acquirer.AcquirerInfo;
import com.hui10.app.model.acquirer.AcquirerLottery;

public interface AcquirerInfoDao {
	
	int addAcquirer(AcquirerInfo acquirerInfo);
	
	int modifyAcquirer(AcquirerInfo acquirerInfo);
	
	List<AcquirerInfo> queryAcquirerList();
	
	AcquirerInfo queryAcquirerInfo(@Param("acquirerno")String acquirerno);
	
	AcquirerInfo queryAcquirerByAccount(@Param("accountid")String accountid);
	
    int addAcquirerApply(AcquirerApply acquirerApply);
	
	int modifyAcquirerApply(AcquirerApply acquirerApply);
	
	int queryAcquirerApplyCount(@Param("acquirerno")String acquirerno, @Param("protocolkey")String protocolkey, 
			@Param("auditstatus")String auditstatus, @Param("salestatus")String salestatus);
	
	List<AcquirerApply> queryAcquirerApplyList(@Param("acquirerno")String acquirerno, @Param("protocolkey")String protocolkey, 
			@Param("auditstatus")String auditstatus, @Param("salestatus")String salestatus, 
			@Param("startnum")Integer startnum, @Param("pagesize")Integer pagesize);
	
	AcquirerApply queryAcquirerApplyInfo(@Param("applyid")String applyid);
	
	List<AcquirerApply> VerifyAcquirerApply(@Param("applyid")String applyid, @Param("acquirerno")String acquirerno, @Param("protocolno")String protocolno,
			@Param("provinceid")String provinceid, @Param("lotterys")List<AcquirerLottery> lotterys);
	
	int addAcquirerLotterys(@Param("lotterys")List<AcquirerLottery> lotterys);
	
	int deleteAcquirerLotterys(@Param("applyid")String applyid); 
	
	List<Map<String,String>> queryAcquirerLotteryList(@Param("acquirerno")String acquirerno, @Param("provinceid")String provinceid);	
	
	int updateAccountAcquirerInfo(@Param("accountid")String accountid, @Param("acquirerno")String acquirerno, @Param("acquirername")String acquirername);
	
	AcquirerInfo getAcquirerInfo(@Param("acquirerno") String acquirerno,@Param("acquirername") String acquirername);
	
	AcquirerInfo getAcquirerInfoLogin(@Param("acquirerno") String acquirerno,@Param("acquirername") String acquirername);
}
