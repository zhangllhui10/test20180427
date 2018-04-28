package com.hui10.app.service.marketing;

import com.hui10.app.model.marketing.MarketingAccessChannel;

/**
 * @ClassName: ChannelService.java
 * @Description:
 * @author zhangll
 * @date 2018年3月20日 下午4:47:54
 */
public interface ChannelService {
	
	public void addChannel(MarketingAccessChannel channel);
	
	public void editChannel(MarketingAccessChannel channel);
	
	public void updateStatus(String channelid,String status);

}
