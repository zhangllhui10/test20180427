package com.hui10.app.service.marketing.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.utils.ValidatorUtils;
import com.hui10.app.dao.marketing.MarketingAccessChannelDao;
import com.hui10.app.dao.marketing.MarketingAccessGatewayDao;
import com.hui10.app.model.marketing.MarketingAccessChannel;
import com.hui10.app.model.marketing.MarketingAccessGateway;
import com.hui10.app.service.marketing.ChannelService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.enums.common.CommonStatusEnum;
import com.hui10.exception.CommonException;

/**
 * @ClassName: ChannelServiceImpl.java
 * @Description:
 * @author zhangll
 * @date 2018年3月20日 下午5:03:21
 */
@Service
public class ChannelServiceImpl implements ChannelService {
	@Autowired
	private MarketingAccessChannelDao marketingAccessChannelDao;
	@Autowired
	private MarketingAccessGatewayDao marketingAccessGatewayDao;

	@Override
	public void addChannel(MarketingAccessChannel channel) {
		ValidatorUtils.checkBean(channel, ICommon.PARAMETER_ERR);
		getGateWay(channel.getGatewayid());
		checkChannelByName(channel.getChannelname());
		Date now = new Date();
		channel.setChannelid(UUIDUtil.getUUID());
		channel.setCreatetime(now);
		channel.setUpdatetime(now);
		channel.setStatus(CommonStatusEnum.ENABLE.getState());
		marketingAccessChannelDao.addChannel(channel);
	}

	@Override
	public void editChannel(MarketingAccessChannel channel) {
		ValidatorUtils.checkBean(channel, ICommon.PARAMETER_ERR);
		checkParams(channel.getChannelid());
		getGateWay(channel.getGatewayid());
		checkChannelById(channel.getChannelid());
		checkChannelByName(channel.getChannelid(), channel.getChannelname());
		Date now = new Date();
		channel.setUpdatetime(now);
		marketingAccessChannelDao.editChannel(channel);
	}

	@Override
	public void updateStatus(String channelid, String status) {
		checkParams(channelid, status);
		if (null == CommonStatusEnum.getInfo(status)) {
			throw new CommonException(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR, "status"));
		}
		MarketingAccessChannel mc = checkChannelById(channelid);
		if (StringUtils.equals(status, mc.getStatus())) {
			throw new CommonException(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR, "status"));
		}
		marketingAccessChannelDao.updateStatus(channelid, status);

	}

	private void getGateWay(String gatewayid) {
		MarketingAccessGateway gateway = marketingAccessGatewayDao.selectByPrimaryKey(gatewayid);
		if (null == gateway) {
			throw new CommonException(ICommon.MARKETING_ACCESSPROUP_ACCESSSETINGID_ILLEGAL,
					PropertiesUtils.get(ICommon.MARKETING_ACCESSPROUP_ACCESSSETINGID_ILLEGAL));

		}
	}
	
	private void checkParams(String... keys) {
		for (String string : keys) {
			if (StringUtils.isBlank(string)) {
				throw new CommonException(ICommon.PARAMETER_ERR, PropertiesUtils.get(ICommon.PARAMETER_ERR,string));
			}
		}
	}

	private MarketingAccessChannel checkChannelById(String channelid) {
		MarketingAccessChannel channel = marketingAccessChannelDao.getChannel(null, channelid);
		if (null == channel) {
			throw new CommonException(ICommon.MARKETING_CHANNEL_NOT_EXIST, PropertiesUtils.get(ICommon.MARKETING_CHANNEL_NOT_EXIST));
		}
		return channel;
	}

	private void checkChannelByName(String channelname) {
		if (null != marketingAccessChannelDao.getChannel(channelname, null)) {
			throw new CommonException(ICommon.MARKETING_ACCESSGROUP_MERCHANTNAME_EXIST, PropertiesUtils.get(ICommon.MARKETING_ACCESSGROUP_MERCHANTNAME_EXIST));
		}
	}
	
	private void checkChannelByName(String channelid, String channelname) {
		if (null != marketingAccessChannelDao.getChannelForUpdate(channelname, channelid)) {
			throw new CommonException(ICommon.MARKETING_ACCESSGROUP_MERCHANTNAME_EXIST, PropertiesUtils.get(ICommon.MARKETING_ACCESSGROUP_MERCHANTNAME_EXIST));
		}
	}

}
