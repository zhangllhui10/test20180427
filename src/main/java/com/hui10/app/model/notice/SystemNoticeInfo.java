package com.hui10.app.model.notice;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;

/**
 * 系统公告实体类
 * @author huangrui
 */
public class SystemNoticeInfo implements Serializable{

	private static final long serialVersionUID = 7419010676238602870L;

	@NotNull(message = "公告ID不能为空")
	private String id;                  //公告ID
	
	@NotNull(message = "公告标题不能为空")
	private String title;               //公告标题

	@NotNull(message = "公告内容不能为空")
	private String content;             //公告内容
	
	private String location;            //显示位置
	
	private String status;              //状态：0停用 1启用

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
