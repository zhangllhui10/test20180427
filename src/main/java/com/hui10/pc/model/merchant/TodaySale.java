package com.hui10.pc.model.merchant;

import java.io.Serializable;
import java.util.List;


public class TodaySale implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2509306672771234551L;
	
	private int salecount;
	
	private long salemoney;
	
	private List<TodaySaleList> list;
	

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

	public List<TodaySaleList> getList() {
		return list;
	}

	public void setList(List<TodaySaleList> list) {
		this.list = list;
	}

}
