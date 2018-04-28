package com.hui10.app.service.notice;

import java.util.List;
import java.util.Map;
import com.hui10.app.model.notice.SystemNoticeInfo;

/**
 * @ClassName: SystemNoticeService.java
 * @Description:系统公告管理接口
 * @author huangrui
 * @date 2018年4月17日 15:43:21
 */
public interface SystemNoticeService {
	
	/**
     * 新增系统公告
     * @param systemNoticeInfo 系统公告信息
     * @return
     */
    public String addSystemNotice(SystemNoticeInfo systemNoticeInfo, String username);
    
    /**
     * 修改系统公告
     * @param systemNoticeInfo 系统公告信息
     * @return
     */
    public String modifySystemNotice(SystemNoticeInfo systemNoticeInfo, String username);
    
    /**
     * 查询系统公告详情
     * @param id 系统公告ID
     * @return
     */
    public SystemNoticeInfo querySystemNoticeInfo(String id);
    
    /**
     * 查询系统公告分页列表
     * @return
     */
    public Map<String, Object> querySystemNoticePageList(Integer pageno, Integer pagesize);

    /**
     * 查询系统公告列表
     * @return
     */
    public List<SystemNoticeInfo> querySystemNoticeList(Integer pagesize);
   
}
