package com.hui10.app.model.merchant;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户终端信息模型
 * @author huangrui
 */
public class MerchantTerminal implements Serializable{
	
	private static final long serialVersionUID = 3891299283871661318L;
	
	private String id;                  //ID
		
	private String merchantid;          //商户ID
	
	private String terminalno;          //POS终端S/N编号
	
	private Date createtime;            //创建时间
	
	private String creater;             //创建人
	
	private Date modifytime;            //修改时间
	
	private String modifier;            //修改人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	public String getTerminalno() {
		return terminalno;
	}

	public void setTerminalno(String terminalno) {
		this.terminalno = terminalno;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
}
