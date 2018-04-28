package com.hui10.app.dao.message;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.message.MessageInfo;

/**
 * @author huangrui
 * @ClassName:
 * @Description:
 * @date 2018年4月8日 19:39
 */
public interface MessageDao {

    int addMessage(MessageInfo messageInfo);
    
    List<MessageInfo> queryMessageList(@Param("uid") String uid, @Param("lastid")String lastid, @Param("pagesize") Integer pagesize);
}
