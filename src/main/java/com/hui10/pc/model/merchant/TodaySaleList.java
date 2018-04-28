package com.hui10.pc.model.merchant;

import java.io.Serializable;

public class TodaySaleList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7742690229948247084L;
	
	String hour;
	
	int salecount;
	
	long salemoney;

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public int getSalecount() {
		return salecount;
	}

	public void setSalecount(int salecount) {
		this.salecount = salecount;
	}

	public long getSalemoney() {
		return salemoney;
	}

	public void setSalemoney(long salemoney) {
		this.salemoney = salemoney;
	}

}
