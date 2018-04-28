package com.hui10.app.model.order;

import java.io.Serializable;

public class OrderAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6574852897682454758L;
	
	private String orderid;
	
	private String huiorderid;
	
	private Long orderamount;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getHuiorderid() {
		return huiorderid;
	}

	public void setHuiorderid(String huiorderid) {
		this.huiorderid = huiorderid;
	}

	public long getOrderamount() {
		return orderamount;
	}

	public void setOrderamount(Long orderamount) {
		this.orderamount = orderamount;
	}

}
