package com.hui10.app.model.banner;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 营销广告信息实体类
 * @author huangrui
 */
public class BannerInfo implements Serializable{
	
	private static final long serialVersionUID = 7535938156411134813L;
	
	@NotNull(message = "轮播图ID不能为空")
	private String id;                  //广告ID
	
	@NotNull(message = "轮播图标题不能为空")
	private String title;               //轮播图标题

	@NotNull(message = "跳转地址不能为空")
	private String linkurl;             //跳转地址
	
	@NotNull(message = "图片不能为空")
	private String picurl;              //封页图片链接地址
	
	@Min(value=0, message="顺序号最小为0")
	@Max(value=999999, message="顺序号最大为999999")
	private Integer sort;               //广告排序
	
	private String status;              //状态：0停用 1启用

	private String creator;             //创建人
	
	private Date createtime;            //创建时间
	
	private String modifier;            //修改人
	
	private Date modifytime;            //修改时间
	
	@NotNull(message = "显示位置不能为空")
	private String position;            //显示位置：1 轮播 2 悬浮

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

	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
