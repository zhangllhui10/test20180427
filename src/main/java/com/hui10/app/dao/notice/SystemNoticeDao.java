package com.hui10.app.dao.notice;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hui10.app.model.notice.SystemNoticeInfo;

/**
 * @author huangrui
 * @ClassName:
 * @Description:
 * @date 2018年4月17日 15:43
 */
public interface SystemNoticeDao {

    int addSystemNotice(SystemNoticeInfo systemNoticeInfo);

    int modifySystemNotice(SystemNoticeInfo systemNoticeInfo);
    
    SystemNoticeInfo querySystemNoticeInfo(@Param("id") String id);
    
    int querySystemNoticeCount();
    
    List<SystemNoticeInfo> querySystemNoticeList(@Param("startnum") Integer startnum, @Param("pagesize") Integer pagesize);
     
}
