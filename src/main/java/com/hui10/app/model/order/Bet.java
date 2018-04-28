package com.hui10.app.model.order;

public  abstract class Bet {
	
	public static String  SPILT = ",";
	
	
	/**
	 * 彩种
	 */
	private String lotterycode;

	/**
	 * 投注方式
	 */
	private String bettype;
	
	/**
	 * 倍数
	 */
	private int multiple = 1;
	
	public String getLotterycode() {
		return lotterycode;
	}

	public void setLotterycode(String lotterycode) {
		this.lotterycode = lotterycode;
	}
	

	public String getBettype() {
		return bettype;
	}

	public void setBettype(String bettype) {
		this.bettype = bettype;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
	
	/**
	 * 
	 * @return 投注注数
	 */
	public abstract int getBetnumber();
	
	
	public  boolean  validate()
	{
		if(this.getMultiple() > 99)
		{
			return false;
		}
		return true;
	};
	
}
