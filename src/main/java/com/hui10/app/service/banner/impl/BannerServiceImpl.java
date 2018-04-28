package com.hui10.app.service.banner.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.utils.ValidatorUtils;
import com.hui10.app.dao.banner.BannerDao;
import com.hui10.app.model.banner.BannerInfo;
import com.hui10.app.model.enums.BannerPositionEnum;
import com.hui10.app.model.enums.BannerStatusEnum;
import com.hui10.app.service.banner.BannerService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;

/**
 * @ClassName: BannerServiceImpl.java
 * @Description:广告管理实现类
 * @author huangrui
 * @date 2018年1月22日 14:34:27
 */
@Service
public class BannerServiceImpl implements BannerService {
	
	private static Logger logger = Logger.getLogger(BannerServiceImpl.class);

    @Autowired
    private BannerDao bannerDao;
    
	@Override
	public String addBanner(BannerInfo bannerInfo, String username) {		
		bannerInfo.setId(UUIDUtil.getUUID());
		checkBean(bannerInfo);
		bannerInfo.setCreator(username);
		bannerInfo.setCreatetime(new Date());
		bannerInfo.setModifier(username);
		bannerInfo.setModifytime(new Date());
		bannerDao.addBanner(bannerInfo);	
		return bannerInfo.getId();
	}

	@Override
	public String modifyBanner(BannerInfo bannerInfo, String username) {
		checkBean(bannerInfo);
		BannerInfo banner = bannerDao.queryBannerInfo(bannerInfo.getId());
		if(banner == null){
			throw new CommonException(ICommon.PARAMETER_IS_ERROR, PropertiesUtils.get(ICommon.PARAMETER_IS_ERROR,"轮播图ID"));
		}
		bannerInfo.setModifier(username);
		bannerInfo.setModifytime(new Date());
		bannerDao.modifyBanner(bannerInfo);		
		return bannerInfo.getId();
	}
	
	@Override
	public String changeBannerStatus(String id, String status, String username) {
		BannerInfo banner = bannerDao.queryBannerInfo(id);
		if(banner == null){
			throw new CommonException(ICommon.PARAMETER_IS_ERROR, PropertiesUtils.get(ICommon.PARAMETER_IS_ERROR,"轮播图ID"));
		}
		if(BannerStatusEnum.getEnum(status) == null){
    		throw new CommonException(ICommon.PARAMETER_IS_ERROR, PropertiesUtils.get(ICommon.PARAMETER_IS_ERROR,"状态"));
    	}
		BannerInfo bannerInfo = new BannerInfo();
		bannerInfo.setId(id);
		bannerInfo.setStatus(status);
		bannerInfo.setModifier(username);
		bannerInfo.setModifytime(new Date());
		bannerDao.modifyBanner(bannerInfo);
		return null;
	}

	@Override
	public List<BannerInfo> queryBannerList(String position) {
		if(StringUtils.isBlank(position)){
			position = null;
		}
		List<BannerInfo> list = bannerDao.queryBannerList(position);
		if(list == null){
			list = new ArrayList<>();
		}
		return list;
	}
	
	/**
     * 基本信息校验
     */
    private void checkBean(BannerInfo bannerInfo){
    	ValidatorUtils.checkBean(bannerInfo, com.hui10.common.constants.ICommon.PARAM_ERROR);
    	if(BannerStatusEnum.getEnum(bannerInfo.getStatus()) == null){
    		throw new CommonException(ICommon.PARAMETER_IS_ERROR, PropertiesUtils.get(ICommon.PARAMETER_IS_ERROR,"状态"));
    	}
    	if(BannerPositionEnum.getEnum(bannerInfo.getPosition()) == null){
    		throw new CommonException(ICommon.PARAMETER_IS_ERROR, PropertiesUtils.get(ICommon.PARAMETER_IS_ERROR,"显示位置"));
    	}
		try {
			if(bannerInfo.getTitle().getBytes("utf-8").length > 200){
				throw new CommonException(ICommon.PARAM_TOO_LONG, PropertiesUtils.get(ICommon.PARAM_TOO_LONG,"标题"));
			}
			if(bannerInfo.getLinkurl().getBytes("utf-8").length > 500){
				throw new CommonException(ICommon.PARAM_TOO_LONG, PropertiesUtils.get(ICommon.PARAM_TOO_LONG,"跳转地址"));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			throw new CommonException(com.hui10.common.constants.ICommon.PARAM_ERROR, PropertiesUtils.get(com.hui10.common.constants.ICommon.PARAM_ERROR));
		}
    }
}
