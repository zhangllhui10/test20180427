package com.hui10.app.service.acquirerhschannel.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hui10.app.common.constants.ICommon;
import com.hui10.app.dao.acquirerhschannel.AcquirerHschannelDao;
import com.hui10.app.model.acquirer.AcquirerHschannel;
import com.hui10.app.service.acquirerhschannel.AcquirerHschannelService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.exception.CommonException;

/**
 * @ClassName: AcquirerHschannelServiceImpl.java
 * @Description:
 * @author zhangll
 * @date 2017年11月15日 下午2:47:57
 */
@Service
public class AcquirerHschannelServiceImpl implements AcquirerHschannelService {
	@Autowired
	private AcquirerHschannelDao acquirerHschannelDao;

	@Override
	public void addHui10MerNo(AcquirerHschannel acqHsch) {

		AcquirerHschannel hschannel_1 = acquirerHschannelDao.queryHschannel(acqHsch.getAcquirerno(), acqHsch.getProvinceid());
		if (null != hschannel_1) {
			throw new CommonException(ICommon.ACQ_PROVINCE_HS_MERCID_EXIST, PropertiesUtils.get(ICommon.ACQ_PROVINCE_HS_MERCID_EXIST));
		}
		AcquirerHschannel hschannel_2 = acquirerHschannelDao.queryHschannelByMercid(acqHsch.getChannelmercid());
		if (null != hschannel_2) {
			throw new CommonException(ICommon.HS_MERCID_EXIST, PropertiesUtils.get(ICommon.HS_MERCID_EXIST));

		}
		acquirerHschannelDao.addHschannel(acqHsch);
	}

	@Override
	public void deleteHsMercNo(String id) {
		AcquirerHschannel channel = acquirerHschannelDao.queryHschannelById(id);
		if(null == channel){
			throw new CommonException(ICommon.HS_MERCNO_NO_EXIST,PropertiesUtils.get(ICommon.HS_MERCNO_NO_EXIST));
		}
		acquirerHschannelDao.deleteHschannel(Integer.parseInt(id));
	}

}
