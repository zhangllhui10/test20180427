package com.hui10.app.model.order;

import java.io.Serializable;
import java.util.Date;

public class OrderAccountBalanceDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9188029373022674402L;
	
	private String accountid;
	
	private String orderid;
	
	private String huiorderid;
	
	private Long orderamount;
	
	private Long huiorderamount;
	
	private String resource;
	
	private Date ordertime;

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

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

	public long getHuiorderamount() {
		return huiorderamount;
	}

	public void setHuiorderamount(Long huiorderamount) {
		this.huiorderamount = huiorderamount;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

}
