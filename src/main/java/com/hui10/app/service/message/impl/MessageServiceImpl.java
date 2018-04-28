package com.hui10.app.service.message.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.hui10.app.dao.message.MessageDao;
import com.hui10.app.model.enums.MessageTypeEnum;
import com.hui10.app.model.message.MessageInfo;
import com.hui10.app.service.message.MessageService;
import com.hui10.common.mq.sender.HuiQueueSender;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.enums.mpush.AfterOpenAction;
import com.hui10.enums.user.UserInvoketypeEnum;
import com.hui10.model.mq.HuiUPushMessageBean;

/**
 * @ClassName: MessageServiceImpl.java
 * @Description:消息接口实现类（518营销活动专用）
 * @author huangrui
 * @date 2018年4月8日 19:53:27
 */
@Service
public class MessageServiceImpl implements MessageService {
	
	private String bet_notify_title = "投注通知";
	
	private String win_notify_title = "中奖通知";
	
	private String send_notify_title = "派奖通知";
	
	private String bet_notify_content = PropertiesUtils.get("BET_NOTIFY_CONTENT").replace("\\n", "\n"); 
	
	private String win_notify_content = PropertiesUtils.get("WIN_NOTIFY_CONTENT").replace("\\n", "\n");
	
	private String send_success_notify_content = PropertiesUtils.get("SEND_SUCCESS_NOTIFY_CONTENT").replace("\\n", "\n");
	
	private String send_fail_notify_content = PropertiesUtils.get("SEND_FAIL_NOTIFY_CONTENT").replace("\\n", "\n");
	
    @Autowired
    private MessageDao messageDao;
    
    @Autowired
    private HuiQueueSender customizedcastSender;
    
    @Autowired
    private HuiQueueSender broadcastSender;
    
	@Override
	public List<MessageInfo> queryMessageList(String uid, String lastid, Integer pagesize) {
		if(StringUtils.isBlank(lastid)){
			lastid = null;
		}
		return messageDao.queryMessageList(uid, lastid, pagesize); 
	}

	@Override
	@Async
	public void addPushMessage(String uid, String orderid, String type) {
		
		String title = null;
		String content = null;
		if(MessageTypeEnum.BET_NOTIFY.getCode().equals(type)){
			title = bet_notify_title;
			content = bet_notify_content;
			broadcastSendMessage(content);
		}else if(MessageTypeEnum.WIN_NOTIFY.getCode().equals(type)){
			title = win_notify_title;
			content = win_notify_content;
			customizedcastSendMessage(uid, content);
		}else if(MessageTypeEnum.SEND_SUCCESS_NOTIFY.getCode().equals(type)){
			title = send_notify_title;
			content = send_success_notify_content;
			customizedcastSendMessage(uid, content);
		}else if(MessageTypeEnum.SEND_FAIL_NOTIFY.getCode().equals(type)){
			title = send_notify_title;
			content = send_fail_notify_content;
			customizedcastSendMessage(uid, content);
		}else{
			return;
		}

		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setId(UUIDUtil.getUUID());
		messageInfo.setType(type);
		messageInfo.setTitle(title);
		messageInfo.setContent(content);
		messageInfo.setUid(uid);
		messageInfo.setOrderid(orderid);
		messageInfo.setCreatetime(new Date());
		messageInfo.setModifytime(new Date());
		messageDao.addMessage(messageInfo);
	}
	
	/**
     * 自定义播
     */
	public void customizedcastSendMessage(String uid, String content){
		HuiUPushMessageBean bean = new HuiUPushMessageBean();
		bean.setAppType(UserInvoketypeEnum.HUI_CAIBAO.getState());
		bean.setTitle(content);
		bean.setText(content);
		bean.setTicker(content);
		bean.setUid(uid);
		bean.setAfterOpenAction(AfterOpenAction.go_app.getState());
		customizedcastSender.sendMessage(bean);
	}
	
	/**
     * 广播
     */
	public void broadcastSendMessage(String content){
		HuiUPushMessageBean bean = new HuiUPushMessageBean();
		bean.setAppType(UserInvoketypeEnum.HUI_CAIBAO.getState());
		bean.setTitle(content);
		bean.setText(content);
		bean.setTicker(content);
		bean.setAfterOpenAction(AfterOpenAction.go_app.getState());
		broadcastSender.sendMessage(bean);
	}
}
