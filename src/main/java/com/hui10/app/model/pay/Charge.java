package com.hui10.app.model.pay;
/**
 * 
* @ClassName: Charge  
* @Description: 收款
* @author yangcb  
* @date 2017年4月1日  
*
 */
public class Charge {

	/*******商户号 M********/
	private  String merc_id;//支付平台分配的商户号
	/*******随机字符串 M********/
	private String salt;//随机字符串，不长于32位。推荐安全规范-随机数生成算法
	/*******签名 M********/
	private String signature;
	/*******请求发起日期时间 M********/
	private String req_time;//yyyyMMddHHmmss
	/*******交易号 M********/
	private String trade_no;
	/*******收款类型 M********/
	private String charge_type;
	/*******上游清算机构 M********/
	private String tradeChannel;
	/*******支付授权码 C********/
	private String charge_auth_code;
	/*******商户门店编号C********/
	private String  store_id;
	/*******商户终端编号C********/
	private String terminal_id;
	/*******操作员编号C********/
	private String operator_id;
	/*******付款通道********/
	private String trade_channel;

	public String getMerc_id() {
		return merc_id;
	}
	public void setMerc_id(String merc_id) {
		this.merc_id = merc_id;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getReq_time() {
		return req_time;
	}
	public void setReq_time(String req_time) {
		this.req_time = req_time;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getCharge_type() {
		return charge_type;
	}
	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}
	public String getTradeChannel() {
		return tradeChannel;
	}
	public void setTradeChannel(String tradeChannel) {
		this.tradeChannel = tradeChannel;
	}
	public String getCharge_auth_code() {
		return charge_auth_code;
	}
	public void setCharge_auth_code(String charge_auth_code) {
		this.charge_auth_code = charge_auth_code;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getTerminal_id() {
		return terminal_id;
	}
	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

    public String getTrade_channel() {
        return trade_channel;
    }

    public void setTrade_channel(String trade_channel) {
        this.trade_channel = trade_channel;
    }
}
