package com.hui10.app.dao.marketing;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.marketing.MarketingAccessChannel;

/**
 * @ClassName: MarketingAccessChannelDao.java
 * @Description:
 * @author zhangll
 * @date 2018年3月20日 下午5:11:09
 */
public interface MarketingAccessChannelDao {

	int addChannel(MarketingAccessChannel channel);

	int editChannel(MarketingAccessChannel channel);

	int updateStatus(@Param("channelid") String channelid, @Param("status") String status);

	MarketingAccessChannel getChannel(@Param("channelname") String channelname, @Param("channelid") String channelid);

	MarketingAccessChannel getChannelForUpdate(@Param("channelname") String channelname, @Param("channelid") String channelid);

}
