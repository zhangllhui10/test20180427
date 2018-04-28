package com.hui10.app.model.pay;


/**
 * 
* @ClassName: PaySDKNotify  
* @Description: 支付回调通知
* @author yangcb  
* @date 2017年4月12日  
*
 */

public class PaySDKNotify {
	private String pay_cnl;
	private long trade_amt;
	private String currency;
	private String trade_no;
	private String merc_order_no;
	private String  settle_date;
	private String trade_sts;
	private String  attach;
	public String getPay_cnl() {
		return pay_cnl;
	}
	public void setPay_cnl(String pay_cnl) {
		this.pay_cnl = pay_cnl;
	}
	public long getTrade_amt() {
		return trade_amt;
	}
	public void setTrade_amt(long trade_amt) {
		this.trade_amt = trade_amt;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getMerc_order_no() {
		return merc_order_no;
	}
	public void setMerc_order_no(String merc_order_no) {
		this.merc_order_no = merc_order_no;
	}
	public String getSettle_date() {
		return settle_date;
	}
	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}
	public String getTrade_sts() {
		return trade_sts;
	}
	public void setTrade_sts(String trade_sts) {
		this.trade_sts = trade_sts;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	
	
	
	
}
