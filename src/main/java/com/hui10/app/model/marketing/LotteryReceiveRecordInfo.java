package com.hui10.app.model.marketing;

import java.io.Serializable;
import java.util.Date;

public class LotteryReceiveRecordInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2973555568135251280L;
	
	/**
	 * 赠送时间
	 */
	private Date givetime;
	
	/**
	 * 领取时间
	 */
	private Date receivetime;
	
	/**
	 * 彩票详情
	 */
	private String betdetail;
	
	/**
	 * 1-未支付，2-出票成功，3-取消, 4-已过期、5-出票失败
	 */
	private int status;
	
	/**
	 * 0--未开奖，1--已开奖
	 */
	private String lotterystatus;
	
	/**
	 * 1-未中奖，2-中奖
	 */
	private int winstatus;
	
	/**
	 * 订单号
	 */
	private String orderid;
	
	/**
	 * 1小奖 2中奖 3大奖
	 */
	private String prizelevel;
	
	/**
	 * 中奖金额
	 */
	private long winprize;
	
	/**
	 * 派奖金额
	 */
	private long sendprize;
	
	/**
	 * 彩金领取：1-未领取 2-已领取  3-领奖处理中   4-领取失败    5-证件审核中   6-证件审核失败 7- 证件审核成功 8-线下领奖 
	 */
	private String bonusstatus;
	
	public Date getGivetime() {
		return givetime;
	}

	public void setGivetime(Date givetime) {
		this.givetime = givetime;
	}

	public Date getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}

	public String getBetdetail() {
		return betdetail;
	}

	public void setBetdetail(String betdetail) {
		this.betdetail = betdetail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLotterystatus() {
		return lotterystatus;
	}

	public void setLotterystatus(String lotterystatus) {
		this.lotterystatus = lotterystatus;
	}

	public int getWinstatus() {
		return winstatus;
	}

	public void setWinstatus(int winstatus) {
		this.winstatus = winstatus;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getPrizelevel() {
		return prizelevel;
	}

	public void setPrizelevel(String prizelevel) {
		this.prizelevel = prizelevel;
	}

	public long getWinprize() {
		return winprize;
	}

	public void setWinprize(long winprize) {
		this.winprize = winprize;
	}

	public long getSendprize() {
		return sendprize;
	}

	public void setSendprize(long sendprize) {
		this.sendprize = sendprize;
	}

	public String getBonusstatus() {
		return bonusstatus;
	}

	public void setBonusstatus(String bonusstatus) {
		this.bonusstatus = bonusstatus;
	}

}
