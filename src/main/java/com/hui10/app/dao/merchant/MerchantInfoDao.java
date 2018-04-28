package com.hui10.app.dao.merchant;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.merchant.MerchantApply;
import com.hui10.app.model.merchant.MerchantInfo;
import com.hui10.app.model.merchant.MerchantLottery;
import com.hui10.app.model.merchant.MerchantTerminal;


public interface MerchantInfoDao {

	int addMerchant(MerchantInfo merchantInfo);
	
	int modifyMerchant(MerchantInfo merchantInfo);
	
	int queryMerchantCount(@Param("acquirerno")String acquirerno, @Param("merchantname")String merchantname);
	
	List<MerchantInfo> queryMerchantList(@Param("acquirerno")String acquirerno, @Param("merchantname")String merchantname, 
			@Param("startnum")Integer startnum, @Param("pagesize")Integer pagesize);
	
	List<MerchantInfo> searchMerchantList(@Param("acquirerno")String acquirerno, @Param("merchantname")String merchantname);
	
	MerchantInfo queryMerchantInfo(@Param("merchantno")String merchantno);
	
	List<MerchantInfo>  VerifyMerchantInfo(@Param("merchantname")String merchantname, @Param("provinceid")String provinceid);
	
	int addMerchantApply(MerchantApply merchantApply);
	
	int modifyMerchantApply(MerchantApply merchantApply);
	
	int queryMerchantApplyCount(@Param("acquirerno")String acquirerno, @Param("auditstatus")String auditstatus,
			@Param("lotterystatus")String lotterystatus, @Param("salestatus")String salestatus,
			@Param("merchantname")String merchantname, @Param("protocolkey")String protocolkey);
	
	List<MerchantApply> queryMerchantApplyList(@Param("acquirerno")String acquirerno, @Param("auditstatus")String auditstatus,
			@Param("lotterystatus")String lotterystatus, @Param("salestatus")String salestatus,
			@Param("merchantname")String merchantname, @Param("protocolkey")String protocolkey, 
			@Param("startnum")Integer startnum, @Param("pagesize")Integer pagesize);
	
	MerchantApply queryMerchantApplyInfo(@Param("applyid")String applyid);
	
	int  VerifyMerchantApply(@Param("applyid")String applyid, @Param("merchantid")String merchantid, @Param("protocolno")String protocolno,
			@Param("provinceid")String provinceid, @Param("lotterys")List<MerchantLottery> lotterys);
	
	int addMerchantLotterys(@Param("lotterys")List<MerchantLottery> lotterys);
	
	int deleteMerchantLotterys(@Param("applyid")String applyid);


	Map<String,String> queryMerchantInfoAndHschannel(String merchantno);
	
	int deleteMerchantTerminal(@Param("id")String id); 
	int addMerchantTerminals(@Param("terminals")List<MerchantTerminal> terminals);
	
	Integer maxStationNo();
	
	List<Map<String, String>> findMerchantsNearBy(@Param("longitude")Double longitude, @Param("latitude")Double latitude, @Param("nearbydistance")int nearbydistance);
	
	int updateLocation(@Param("merchantid")String merchantid, @Param("longitude")Double longitude, @Param("latitude")Double latitude);
	
	List<MerchantInfo> queryMerchantByIds(@Param("list") List<String> merchantidList);
	
	int updateMerchantStationCode(@Param("merchantno")String merchantno, @Param("stationcode")String stationcode);
	
	MerchantInfo findMerchantInfoByMerchantNo(@Param("merchantno")String merchantno);
}
