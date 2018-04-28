package com.hui10.pc.model.fenrun;

import java.io.Serializable;

public class FenRun implements Serializable{
	
	private static final long serialVersionUID = -4940891675742604608L;

	private String month;           //月份
	
	private long money;             //分润额（单位：分）

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}
}
