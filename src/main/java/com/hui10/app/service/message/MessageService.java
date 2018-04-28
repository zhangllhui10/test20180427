package com.hui10.app.service.message;

import java.util.List;
import com.hui10.app.model.message.MessageInfo;

/**
 * @ClassName: MessageService.java
 * @Description:消息接口
 * @author huangrui
 * @date 2018年4月8日 19:47:21
 */
public interface MessageService {
	
	/**
     * 添加推送通知消息
     * @param uid 用户ID
     * @param orderid 订单ID 
     * @param type 消息类型（1 投注通知；2中奖通知；3派奖成功通知；4派奖失败通知）枚举类：MessageTypeEnum.java
     * @return
     */
	public void addPushMessage(String uid, String orderid, String type);
    
    /**
     * 查询消息列表
     * @param uid 用户ID
     * @param lastid 最后一条消息ID
     * @param pagesize 每页条数
     * @return
     */
    public List<MessageInfo> queryMessageList(String uid, String lastid, Integer pagesize);
    
}
