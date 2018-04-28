package com.hui10.app.model.order;

import java.io.Serializable;
import java.util.Date;


public class Ticket implements Serializable {

    private static final long serialVersionUID = -8570198276660306982L;

    private Bet bet;
	
    public Bet getBet() {
		return bet;
	}

	public void setBet(Bet bet) {
		this.bet = bet;
	}
	
	private String uid;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	private Integer ordersource;

	public Integer getOrdersource() {
		return ordersource;
	}

	public void setOrdersource(Integer ordersource) {
		this.ordersource = ordersource;
	}


	private String stationcode;
	public String getStationcode() {
		return stationcode;
	}

	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}

	private Long sendprize;


	public Long getSendprize() {
		return sendprize;
	}

	public void setSendprize(Long sendprize) {
		this.sendprize = sendprize;
	}

	public void setBetnumber(int betnumber) {
		this.betnumber = betnumber;
	}


	/**
     *   投注流水号
     * Column: poslot_lottery_ticket.ticketid
    @mbggenerated 2018-01-05 18:49:50
     */
    private String ticketid;

    /**
     *   宝乐彩订单编号
     * Column: poslot_lottery_ticket.orderid
    @mbggenerated 2018-01-05 18:49:50
     */
    private String orderid;

    /**
     *   汇彩宝订单号
     * Column: poslot_lottery_ticket.orderid
    @mbggenerated 2018-01-05 18:49:50
     */
    private String orderno;

    /**
     *   来自于那0宝乐彩1汇拾
     * Column: poslot_lottery_ticket.ordertype
    @mbggenerated 2018-01-05 18:49:50
     */
    private int ordertype;

    /**
     *   彩票类型
     * Column: poslot_lottery_ticket.lotterycode
    @mbggenerated 2018-01-05 18:49:50
     */
    private String lotterycode;

    /**
     *   彩票期号
     * Column: poslot_lottery_ticket.issueno
    @mbggenerated 2018-01-05 18:49:50
     */
    private String issueno;

    /**
     *   投注类型
     * Column: poslot_lottery_ticket.bettype
    @mbggenerated 2018-01-05 18:49:50
     */
    private String bettype;

    /**
     *   投注详情
     * Column: poslot_lottery_ticket.betdetail
    @mbggenerated 2018-01-05 18:49:50
     */
    private String betdetail;
    
    private String errorcode;
    
    private int betnumber;
    
    public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	/**
     * 投注注码
     */
    private String betvalue;

	public String getBetvalue() {
		return betvalue;
	}

	public void setBetvalue(String betvalue) {
		this.betvalue = betvalue;
	}

	/**
     *   倍数
     * Column: poslot_lottery_ticket.multiple
    @mbggenerated 2018-01-05 18:49:50
     */
    private Integer multiple;

    /**
     *   钱数单位分
     * Column: poslot_lottery_ticket.amount
    @mbggenerated 2018-01-05 18:49:50
     */
    private Long amount;

    /**
     *   逻辑机编号
     * Column: poslot_lottery_ticket.betmachineid
    @mbggenerated 2018-01-05 18:49:50
     */
    private String betmachineid;

    /**
     *   投注时间
     * Column: poslot_lottery_ticket.bettime
    @mbggenerated 2018-01-05 18:49:50
     */
    private Date bettime;

    /**
     *   出票时间
     * Column: poslot_lottery_ticket.tickettime
    @mbggenerated 2018-01-05 18:49:50
     */
    private Date tickettime;

    /**
     *   省份
     * Column: poslot_lottery_ticket.provinceid
    @mbggenerated 2018-01-05 18:49:50
     */
    private String provinceid;

    /**
     *   状态
     * Column: poslot_lottery_ticket.status
    @mbggenerated 2018-01-05 18:49:50
     */
    private int status;

    /**
     *   开奖时间
     * Column: poslot_lottery_ticket.prizetime
    @mbggenerated 2018-01-05 18:49:50
     */
    private Date prizetime;

    /**
     *   最晚兑奖时间
     * Column: poslot_lottery_ticket.prizedeadline
    @mbggenerated 2018-01-05 18:49:50
     */
    private Date prizedeadline;

    /**
     *   获得奖金
     * Column: poslot_lottery_ticket.winprize
    @mbggenerated 2018-01-05 18:49:50
     */
    private Long winprize;

    /**
     *   票号
     * Column: poslot_lottery_ticket.ticketkey
    @mbggenerated 2018-01-05 18:49:50
     */
    private String ticketkey;
	
	//积分卡号
	private String pointcardno;
	private String mobile;
	private String betcitycode;
	private String drawway;
	
	private int sendstatus;

	private String buid;
	public int getSendstatus() {
		return sendstatus;
	}

	public void setSendstatus(int sendstatus) {
		this.sendstatus = sendstatus;
	}

	public String getBetcitycode() {
		return betcitycode;
	}

	public void setBetcitycode(String betcitycode) {
		this.betcitycode = betcitycode;
	}

	public String getPointcardno() {
		return pointcardno;
	}

	public void setPointcardno(String pointcardno) {
		this.pointcardno = pointcardno;
	}

	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	
	
	public String getDrawway() {
		return drawway;
	}

	public void setDrawway(String drawway) {
		this.drawway = drawway;
	}


	private String machineruncode;


	public String getMachineruncode() {
		return machineruncode;
	}

	public void setMachineruncode(String machineruncode) {
		this.machineruncode = machineruncode;
	}

    public String getTicketid() {
        return ticketid;
    }

    public void setTicketid(String ticketid) {
        this.ticketid = ticketid == null ? null : ticketid.trim();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public int getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(int ordertype) {
        this.ordertype = ordertype;
    }

    public String getLotterycode() {
        return lotterycode;
    }

    public void setLotterycode(String lotterycode) {
        this.lotterycode = lotterycode == null ? null : lotterycode.trim();
    }

    public String getIssueno() {
        return issueno;
    }

    public void setIssueno(String issueno) {
        this.issueno = issueno == null ? null : issueno.trim();
    }

    public String getBettype() {
        return bettype;
    }

    public void setBettype(String bettype) {
        this.bettype = bettype == null ? null : bettype.trim();
    }

    public String getBetdetail() {
        return betdetail;
    }

    public void setBetdetail(String betdetail) {
        this.betdetail = betdetail == null ? null : betdetail.trim();
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getBetmachineid() {
        return betmachineid;
    }

    public void setBetmachineid(String betmachineid) {
        this.betmachineid = betmachineid == null ? null : betmachineid.trim();
    }

    public Date getBettime() {
        return bettime;
    }

    public void setBettime(Date bettime) {
        this.bettime = bettime;
    }

    public Date getTickettime() {
        return tickettime;
    }

    public void setTickettime(Date tickettime) {
        this.tickettime = tickettime;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid == null ? null : provinceid.trim();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getPrizetime() {
        return prizetime;
    }

    public void setPrizetime(Date prizetime) {
        this.prizetime = prizetime;
    }

    public Date getPrizedeadline() {
        return prizedeadline;
    }

    public void setPrizedeadline(Date prizedeadline) {
        this.prizedeadline = prizedeadline;
    }

    public Long getWinprize() {
        return winprize;
    }

    public void setWinprize(Long winprize) {
        this.winprize = winprize;
    }

    public String getTicketkey() {
        return ticketkey;
    }

    public void setTicketkey(String ticketkey) {
        this.ticketkey = ticketkey == null ? null : ticketkey.trim();
    }

	public Integer getBetnumber() {
		return betnumber;
	}

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getBuid() {
        return buid;
    }

    public void setBuid(String buid) {
        this.buid = buid;
    }
}