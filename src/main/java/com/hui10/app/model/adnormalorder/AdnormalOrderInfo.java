package com.hui10.app.model.adnormalorder;

import java.io.Serializable;
import java.util.Date;

public class AdnormalOrderInfo implements Serializable{	
	
	private static final long serialVersionUID = 3377551577735089317L;
	
	private String id;                      //主键ID
	
	private String orderid;                 //订单号
	
	private String outtradeno;              //交易流水号
	
	private String userphone;               //用户手机号
	
	private String stationno;               //投注站编号
	
	private String serialno;                //设备S/N号
	
	private String bankcardid;              //用户绑定银行卡编号
	
	private long amount;                    //打款金额
	
	private String status;                  //状态 1-未处理，2-已处理不打款，3-已处理并打款, 4-打款失败
	
	private String submitter;               //提交人 
	
	private Date submittime;                //提交时间
	
	private String handler;                 //处理人
	 
	private Date handletime;                //处理时间
	
	private Date createtime;                //创建时间
	
	private Date updatetime;                //更新时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOuttradeno() {
		return outtradeno;
	}

	public void setOuttradeno(String outtradeno) {
		this.outtradeno = outtradeno;
	}

	public String getUserphone() {
		return userphone;
	}

	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}

	public String getStationno() {
		return stationno;
	}

	public void setStationno(String stationno) {
		this.stationno = stationno;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getBankcardid() {
		return bankcardid;
	}

	public void setBankcardid(String bankcardid) {
		this.bankcardid = bankcardid;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public Date getSubmittime() {
		return submittime;
	}

	public void setSubmittime(Date submittime) {
		this.submittime = submittime;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public Date getHandletime() {
		return handletime;
	}

	public void setHandletime(Date handletime) {
		this.handletime = handletime;
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
}