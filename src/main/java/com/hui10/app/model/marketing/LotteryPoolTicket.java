package com.hui10.app.model.marketing;

import java.io.Serializable;

public class LotteryPoolTicket implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8534622481820867535L;

	private String ticketid;
	
	private String betdetail;
	
	private String marketingid;
	
	private String ticketstatus;
	
	private int sequence;

	public String getTicketid() {
		return ticketid;
	}

	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}

	public String getBetdetail() {
		return betdetail;
	}

	public void setBetdetail(String betdetail) {
		this.betdetail = betdetail;
	}

	public String getMarketingid() {
		return marketingid;
	}

	public void setMarketingid(String marketingid) {
		this.marketingid = marketingid;
	}

	public String getTicketstatus() {
		return ticketstatus;
	}

	public void setTicketstatus(String ticketstatus) {
		this.ticketstatus = ticketstatus;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
