package com.hui10.app.model.user;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: UserOrder.java
 * @Description:
 * @author wengf
 * @date 2017年10月17日 下午2:40:40
 */
public class UserOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3060424704593357914L;

	private String id;
	private String uid;
	private String orderid;
	private Date createtime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	

}
