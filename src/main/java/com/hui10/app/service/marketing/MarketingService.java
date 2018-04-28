package com.hui10.app.service.marketing;

import java.util.List;

import com.hui10.app.model.marketing.Marketing;
import com.hui10.app.model.marketing.MarketingGroup;

/**
 * @ClassName: MarketingService.java
 * @Description:
 * @author zhangll
 * @date 2018年2月27日 上午9:38:52
 */
public interface MarketingService {
	/**
	 * 
	 * @param marketing
	 * @param accessjson json格式
	 * @user zhangll
	 * @date 2018年2月27日 上午10:42:53
	 */
	public void addMarketing(Marketing marketing,String accessjson);
	
	public void updateMarketing(Marketing marketing,String accessjson);
	
	public void updateMarketingStatus(String marketingid,String status);
	
	public List<Marketing> findCurrMarketings(String lotterycode);
	
	public List<MarketingGroup> findMarketingGroups(String marketingid, String channelid);
	
	Marketing findOneMarketing(String marketingid);

}
