package com.hui10.app.service.notice.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.utils.ValidatorUtils;
import com.hui10.app.dao.notice.SystemNoticeDao;
import com.hui10.app.model.notice.SystemNoticeInfo;
import com.hui10.app.service.notice.SystemNoticeService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;

/**
 * @ClassName: SystemNoticeServiceImpl.java
 * @Description:系统公告管理接口实现类
 * @author huangrui
 * @date 2018年4月22日 15:50:27
 */
@Service
public class SystemNoticeServiceImpl implements SystemNoticeService {
	
	private static Logger logger = Logger.getLogger(SystemNoticeServiceImpl.class);

    @Autowired
    private SystemNoticeDao systemNoticeDao;

	@Override
	public String addSystemNotice(SystemNoticeInfo systemNoticeInfo, String username) {
		systemNoticeInfo.setId(UUIDUtil.getUUID());
		checkBean(systemNoticeInfo);
		Date date = new Date();
		systemNoticeInfo.setCreator(username);
		systemNoticeInfo.setCreatetime(date);
		systemNoticeInfo.setModifier(username);
		systemNoticeInfo.setModifytime(date);	
		systemNoticeDao.addSystemNotice(systemNoticeInfo);
		return systemNoticeInfo.getId();
	}

	@Override
	public String modifySystemNotice(SystemNoticeInfo systemNoticeInfo, String username) {
		checkBean(systemNoticeInfo);
		SystemNoticeInfo oldSystemNoticeInfo = systemNoticeDao.querySystemNoticeInfo(systemNoticeInfo.getId());
		if(oldSystemNoticeInfo == null){
			logger.error("公告ID："+systemNoticeInfo.getId());
			throw new CommonException(ICommon.PARAMETER_IS_ERROR, PropertiesUtils.get(ICommon.PARAMETER_IS_ERROR,"公告ID"));
		}
		systemNoticeInfo.setModifier(username);
		systemNoticeInfo.setModifytime(new Date());	
		systemNoticeDao.modifySystemNotice(systemNoticeInfo);
		return systemNoticeInfo.getId();
	}

	@Override
	public SystemNoticeInfo querySystemNoticeInfo(String id) {
		return systemNoticeDao.querySystemNoticeInfo(id);
	}

	@Override
	public Map<String, Object> querySystemNoticePageList(Integer pageno, Integer pagesize) {
		if(pageno < 1){
			throw new CommonException(ICommon.PARAMETER_IS_ERROR, PropertiesUtils.get(ICommon.PARAMETER_IS_ERROR,"当前页数"));
		}
		if(pagesize < 1){
			throw new CommonException(ICommon.PARAMETER_IS_ERROR, PropertiesUtils.get(ICommon.PARAMETER_IS_ERROR,"每页条数"));
		}
		int startnum = (pageno - 1) * pagesize;
		int count = systemNoticeDao.querySystemNoticeCount();
		List<SystemNoticeInfo> list = null;
		if(count > 0){
			list = systemNoticeDao.querySystemNoticeList(startnum, pagesize);
		}else{
			list = new ArrayList<>();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pagesize", pagesize);
		map.put("pageno", pageno);
		map.put("count", count);
		map.put("list", list);
		return map;
	}
	
	@Override
	public List<SystemNoticeInfo> querySystemNoticeList(Integer pagesize) {
		return systemNoticeDao.querySystemNoticeList(0, pagesize);
	}

	/**
     * 基本信息校验
     */
    private void checkBean(SystemNoticeInfo systemNoticeInfo){
    	ValidatorUtils.checkBean(systemNoticeInfo, com.hui10.common.constants.ICommon.PARAM_ERROR);
		try {
			if(systemNoticeInfo.getTitle().getBytes("utf-8").length > 200){
				throw new CommonException(ICommon.PARAM_TOO_LONG, PropertiesUtils.get(ICommon.PARAM_TOO_LONG,"标题"));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			throw new CommonException(com.hui10.common.constants.ICommon.PARAM_ERROR, PropertiesUtils.get(com.hui10.common.constants.ICommon.PARAM_ERROR));
		}
    }
}
