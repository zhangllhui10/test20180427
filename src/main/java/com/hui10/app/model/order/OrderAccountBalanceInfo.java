package com.hui10.app.model.order;

import java.io.Serializable;
import java.util.Date;

public class OrderAccountBalanceInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8079745303679216925L;
	
	private String accountid;
	
	private String accounttime;
	
	private Date createtime;
	
	private int ordertotal;
	
	private long orderamount;
	
	private String accountstatus;

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getAccounttime() {
		return accounttime;
	}

	public void setAccounttime(String accounttime) {
		this.accounttime = accounttime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getOrdertotal() {
		return ordertotal;
	}

	public void setOrdertotal(int ordertotal) {
		this.ordertotal = ordertotal;
	}

	public long getOrderamount() {
		return orderamount;
	}

	public void setOrderamount(long orderamount) {
		this.orderamount = orderamount;
	}

	public String getAccountstatus() {
		return accountstatus;
	}

	public void setAccountstatus(String accountstatus) {
		this.accountstatus = accountstatus;
	}

}
