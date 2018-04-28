package com.hui10.app.service.merchant;

import java.util.List;
import java.util.Map;

import com.hui10.app.model.merchant.MerchantInfo;

/**
 * @ClassName: MerchantInfoService.java
 * @Description:
 * @author zhangll
 * @date 2017年10月30日 下午2:50:50
 */
public interface MerchantInfoService {
	public void createMerchantApply(String authorization, String paramsBody);
	
	public void checkMerLotteryStatus(String applyid,String merchantid,String lotterystatus,String lotteryreason);
	
	public void manageMerTerminal(String authorization, String paramsBody);
	
	public void updateApplySaleStatus(String authorization, String paramsBody);
	
	public List<Map<String, String>> findMerchantsNearBy(Double longitude, Double latitude);
	
	public int updateLocation(String merchantid, Double longitude, Double latitude);
	
	List<MerchantInfo> queryMerchantByMerchantNo(List<String> merchantidList);
	
	public int updateMerchantStationCode(String merchantno, String stationcode);
	
	MerchantInfo findMerchantInfoByMerchantNo(String merchantno);

}
