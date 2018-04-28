package com.hui10.app.model.marketing;

import java.io.Serializable;
import java.util.Date;

public class Marketing implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4787643485235168819L;
	
	private String marketingid;
	
    private String marketingname;

    private Date starttime;
    
    private Date endtime;

    private String indefinite;
    
    private String initiator;

    private String bettimetype;
    
    private Date bettime;

    private String lotterycode;

    private Integer betlimit;

    private String israndom;
    
    private Integer betnumberrule;

    private Integer days;
    
    private String status;
    
    private String resource;
    
    private Date createtime;
    
    private Date updatetime;
    
    private int receivenum;

	public String getMarketingid() {
		return marketingid;
	}

	public void setMarketingid(String marketingid) {
		this.marketingid = marketingid;
	}

	public String getMarketingname() {
		return marketingname;
	}

	public void setMarketingname(String marketingname) {
		this.marketingname = marketingname;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getIndefinite() {
		return indefinite;
	}

	public void setIndefinite(String indefinite) {
		this.indefinite = indefinite;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public String getBettimetype() {
		return bettimetype;
	}

	public void setBettimetype(String bettimetype) {
		this.bettimetype = bettimetype;
	}

	public Date getBettime() {
		return bettime;
	}

	public void setBettime(Date bettime) {
		this.bettime = bettime;
	}

	public String getLotterycode() {
		return lotterycode;
	}

	public void setLotterycode(String lotterycode) {
		this.lotterycode = lotterycode;
	}

	public Integer getBetlimit() {
		return betlimit;
	}

	public void setBetlimit(Integer betlimit) {
		this.betlimit = betlimit;
	}

	public String getIsrandom() {
		return israndom;
	}

	public void setIsrandom(String israndom) {
		this.israndom = israndom;
	}

	public Integer getBetnumberrule() {
		return betnumberrule;
	}

	public void setBetnumberrule(Integer betnumberrule) {
		this.betnumberrule = betnumberrule;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public int getReceivenum() {
		return receivenum;
	}

	public void setReceivenum(int receivenum) {
		this.receivenum = receivenum;
	}
	
}