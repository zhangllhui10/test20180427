package com.hui10.app.model.message;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息实体类
 * @author huangrui
 */
public class MessageInfo implements Serializable{
	
	private static final long serialVersionUID = 7535938156411134813L;

	private String id;                  //消息ID
	
	private String type;                //消息类型（1 投注通知；2中奖通知；3派奖通知）
	
	private String title;               //消息标题
	
	private String content;             //消息内容
	
	private String uid;                 //用户ID
	
	private String orderid;             //订单ID

	private String creator;             //创建人
	
	private Date createtime;            //创建时间
	
	private String modifier;            //修改人
	
	private Date modifytime;            //修改时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
}
