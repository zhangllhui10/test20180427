package com.hui10.app.model.order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 彩票订单
 *
 * @author yangcb
 * @create 2017-10-17 15:40
 **/
public class LotteryOrder implements Serializable {

    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 订单编号
     */
    private String orderid;
    /**
     * 投注站省份
     */
    private String stationprovince;
    /**
     * 投注站编号
     */
    private String stationno;
    /**
     * 订单总金额
     */
    private int orderamount;
    /**
     * 订单总金额
     */
    private int actualamount;
    /**
     * 支付交易流水
     */
    private String outtradeno;
    /**
     * 商户编号
     */
    private String merchantno;
    private String merchantname;
    /**
     * 投注流⽔号 -宝乐彩订单号
     */
    private String orderno;
    /**
     * 设备SN
     */
    private String serialno;
    /**
     * 投注期号
     */
    private String issuenumber;
    /**
     * 订单投注时间
     */
    private Date ordertime;
    /**
     * 彩票玩法代码
     */
    private String gamecode;
    /**
     * 彩票前置系统为汇拾分配的唯一性标识
     */
    private String spid;
    /**
     * 状态 1-未支付，2-取消，3-支付成功
     */
    private int status = 1;
    /**
     * 否中奖：1-未开奖，2-已开奖未中奖，3-已开奖中奖
     */
    private int winstatus = 1;

    private String prizelevel;

    private long winprize;
    /**
     * 彩金领取：1-未领取 2-已领取 3-作废
     */
    private int bonusstatus = 1;
    /**
     * 投注对象信息
     */
    private String remark;
    /**
     * 订单创建时间
     */
    private Date createdate;
    /**
     * 订单更新时间
     */
    private Date updatedate;

    /**
     * 订单来源
     */
    private int source;

    /**
     * 支付时间
     */
    private Date paytime;

    private List<LotteryOrderDetail> orderDetails;

    /**
     * 开奖时间
     */
    private Date lotterytime;


    private String lotterycode;

    /**
     * 收单机构id
     */
    private String acquirerno;
    /**
     * 汇拾在收单机构商户号
     */
    private String channelmercid;

    /**
     * 2017-12-11
     * 用户手机号
     */
    private String userphone;

    
    private String encryptorderid;
    
    private String lotterystatus;
    
    /**
     * 用户ID
     */
    private String uid;
    
    /**
     * 订单类型：1普通，2赠送
     */
    private String ordertype;

    /**
     * 注数
     */
    private int betnumber;

    /**
     * 税后金额
     */
    private long sendprize;

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getAcquirerno() {
        return acquirerno;
    }

    public void setAcquirerno(String acquirerno) {
        this.acquirerno = acquirerno;
    }

    public String getChannelmercid() {
        return channelmercid;
    }

    public void setChannelmercid(String channelmercid) {
        this.channelmercid = channelmercid;
    }

    public Date getLotterytime() {
        return lotterytime;
    }

    public void setLotterytime(Date lotterytime) {
        this.lotterytime = lotterytime;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getStationprovince() {
        return stationprovince;
    }

    public void setStationprovince(String stationprovince) {
        this.stationprovince = stationprovince;
    }

    public String getStationno() {
        return stationno;
    }

    public void setStationno(String stationno) {
        this.stationno = stationno;
    }

    public int getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(int orderamount) {
        this.orderamount = orderamount;
    }

    public String getOuttradeno() {
        return outtradeno;
    }

    public void setOuttradeno(String outtradeno) {
        this.outtradeno = outtradeno;
    }

    public String getMerchantno() {
        return merchantno;
    }

    public void setMerchantno(String merchantno) {
        this.merchantno = merchantno;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getIssuenumber() {
        return issuenumber;
    }

    public void setIssuenumber(String issuenumber) {
        this.issuenumber = issuenumber;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public String getGamecode() {
        return gamecode;
    }

    public void setGamecode(String gamecode) {
        this.gamecode = gamecode;
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWinstatus() {
        return winstatus;
    }

    public void setWinstatus(int winstatus) {
        this.winstatus = winstatus;
    }

    public int getBonusstatus() {
        return bonusstatus;
    }

    public void setBonusstatus(int bonusstatus) {
        this.bonusstatus = bonusstatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
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

    public List<LotteryOrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<LotteryOrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getLotterycode() {
        return lotterycode;
    }

    public void setLotterycode(String lotterycode) {
        this.lotterycode = lotterycode;
    }

	public String getMerchantname() {
		return merchantname;
	}

	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}

	public String getEncryptorderid() {
		return encryptorderid;
	}

	public void setEncryptorderid(String encryptorderid) {
		this.encryptorderid = encryptorderid;
	}

	public String getLotterystatus() {
		return lotterystatus;
	}

	public void setLotterystatus(String lotterystatus) {
		this.lotterystatus = lotterystatus;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

    public int getActualamount() {
        return actualamount;
    }

    public void setActualamount(int actualamount) {
        this.actualamount = actualamount;
    }

    public int getBetnumber() {
        return betnumber;
    }

    public void setBetnumber(int betnumber) {
        this.betnumber = betnumber;
    }

    public long getSendprize() {
        return sendprize;
    }

    public void setSendprize(long sendprize) {
        this.sendprize = sendprize;
    }
}
