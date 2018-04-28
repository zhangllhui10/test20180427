package com.hui10.app.model.marketing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MarketingGroup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6501140207112859408L;
	
	private Date bettime;
	
	/**
	 * 0不限制
	 */
	private int betlimit;
	
	/**
	 * 1随机 0不随机
	 */
	private String israndom;
	
	/**
	 * 每个手机号每天注数
	 */
	private int betnumberrule;
	
	/**
	 * 领取天数
	 */
	private int days;
	
	private List<String> cityids;
	
	private List<String> merchantnos;
	
	/**
	 * 金额
	 */
	private long money;
	
	/**
	 * 赠送注数
	 */
	private int betnumber;

	public Date getBettime() {
		return bettime;
	}

	public void setBettime(Date bettime) {
		this.bettime = bettime;
	}

	public int getBetlimit() {
		return betlimit;
	}

	public void setBetlimit(int betlimit) {
		this.betlimit = betlimit;
	}

	public String getIsrandom() {
		return israndom;
	}

	public void setIsrandom(String israndom) {
		this.israndom = israndom;
	}

	public int getBetnumberrule() {
		return betnumberrule;
	}

	public void setBetnumberrule(int betnumberrule) {
		this.betnumberrule = betnumberrule;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public List<String> getCityids() {
		return cityids;
	}

	public void setCityids(List<String> cityids) {
		this.cityids = cityids;
	}

	public List<String> getMerchantnos() {
		return merchantnos;
	}

	public void setMerchantnos(List<String> merchantnos) {
		this.merchantnos = merchantnos;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public int getBetnumber() {
		return betnumber;
	}

	public void setBetnumber(int betnumber) {
		this.betnumber = betnumber;
	}

}
