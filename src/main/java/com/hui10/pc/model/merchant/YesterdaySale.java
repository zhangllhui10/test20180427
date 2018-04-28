package com.hui10.pc.model.merchant;

import java.io.Serializable;

public class YesterdaySale implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -330749699260284861L;
	
	private int salecount;
	
	private long salemoney;

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
