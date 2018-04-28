package com.hui10.app.model.marketing;

import java.io.Serializable;
import java.util.Date;

/**
 * 营销活动彩票赠送记录实体类
 * @author huangrui
 */
public class LotteryGivingRecordInfo implements Serializable{

	private static final long serialVersionUID = -5332811175669161710L;
	
	private String id;                  //赠送记录ID
	
	private String marketingid;         //营销活动ID
	
	private String channelid;           //接入途径标识
	
	private String ticketid;            //彩票ID
	
	private String uid;                 //用户ID
	
	private String lotterycode;         //彩种编号
	
    private String issueno;             //投注期号
    
    private String betdetail;           //投注详情
    
    private String stationno;           //投注站编号
    
    private String stationprovince;     //投注站省份
    
    private String merchantno;          //商户编号
    
    private String acquirerno;          //收单机构编号
    
    private String serialno;            //设备SN
    
    private String status;              //状态：1未领取，2已领取, 3已过期
    
    private String orderid;             //投注订单ID（投注成功更新此字段）
    
    private Date givetime;              //赠送时间
    
    private Date receivetime;           //领取时间
    
    private Date deadlinetime;          //领取截止时间，空表示不限制
    
    private Date createtime;            //创建时间
    
    private Date updatetime;            //更新时间
    
    private String orderno;             //赠送来源订单
    
    private String bettimetype;         //0立即投注 1指定时间 2送完立即投注
    
    private String resource;            //null代表本地  32位英泰营销id
    
    private String poslotorderno;       //宝乐彩订单号

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMarketingid() {
		return marketingid;
	}

	public void setMarketingid(String marketingid) {
		this.marketingid = marketingid;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public String getTicketid() {
		return ticketid;
	}

	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getLotterycode() {
		return lotterycode;
	}

	public void setLotterycode(String lotterycode) {
		this.lotterycode = lotterycode;
	}

	public String getIssueno() {
		return issueno;
	}

	public void setIssueno(String issueno) {
		this.issueno = issueno;
	}

	public String getBetdetail() {
		return betdetail;
	}

	public void setBetdetail(String betdetail) {
		this.betdetail = betdetail;
	}

	public String getStationno() {
		return stationno;
	}

	public void setStationno(String stationno) {
		this.stationno = stationno;
	}

	public String getStationprovince() {
		return stationprovince;
	}

	public void setStationprovince(String stationprovince) {
		this.stationprovince = stationprovince;
	}

	public String getMerchantno() {
		return merchantno;
	}

	public void setMerchantno(String merchantno) {
		this.merchantno = merchantno;
	}

	public String getAcquirerno() {
		return acquirerno;
	}

	public void setAcquirerno(String acquirerno) {
		this.acquirerno = acquirerno;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

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

	public Date getDeadlinetime() {
		return deadlinetime;
	}

	public void setDeadlinetime(Date deadlinetime) {
		this.deadlinetime = deadlinetime;
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
	
	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getBettimetype() {
		return bettimetype;
	}

	public void setBettimetype(String bettimetype) {
		this.bettimetype = bettimetype;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getPoslotorderno() {
		return poslotorderno;
	}

	public void setPoslotorderno(String poslotorderno) {
		this.poslotorderno = poslotorderno;
	}
}
