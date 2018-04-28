package com.hui10.app.model.pay;

/***
 * 
* @ClassName: UnifiedOrder  
* @Description: 统一下单
* @author yangcb  
* @date 2017年4月1日  
*
 */
public class UnifiedOrder {

	/*******商户号 M********/
	private  String merc_id;//支付平台分配的商户号
	/*******随机字符串 M********/
	private String salt;//随机字符串，不长于32位。推荐安全规范-随机数生成算法
	/*******签名 M********/
	private String signature;
	/*******请求发起日期时间 M********/
	private String req_time;//yyyyMMddHHmmss
	/*******商品描述 M********/
	private String trade_desc;//商品或支付单简要描述
	/*******商品详情 M********/
	private String trade_detail;//对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
	/*******附加数据 O********/
	private String attach;//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	/*******商户订单号 M********/
	private String merc_order_no;//商户系统内部的订单号,32个字符内、可包含字母,
	/*******二级商户号 C********/
	private String sub_merc_id;//二级商户号
	/*******交易金额 M********/
	private double trade_amt;//单位为元
	/*******货币类型 M********/
	private String currency;//符合ISO 4217标准的三位字母代码，目前仅支持人民币：CNY
	/*******终端IP M********/
	private String req_ip;//用户端实际ip,例如123.12.12.123
	/*******订单失效时间 O********/
	private String time_expire;//订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010；注意: 订单失效时间不能超过45分钟
	/*******客户端通知地址 O********/
	private String client_notify_url;//客户端通知地址
	/*******服务端通知地址 M********/
	private String server_notify_url;//客户订单系统回调地址

	/*******转账金额 M********/
	private double transfer_amount;
	/*******收款人账号 M********/
	private String recipient_account_number;
	/*******收款人账户名称 M********/
	private String recipient_account_name;
	/*******收款人开户行名称 M********/
	private String destination_bank_name;
	/*******收款人开户行联行号 M********/
	private String destination_bank_code;
	/*******收款人账户类型 M********/
	private String recipient_account_type;


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
	public String getTrade_desc() {
		return trade_desc;
	}
	public void setTrade_desc(String trade_desc) {
		this.trade_desc = trade_desc;
	}
	public String getTrade_detail() {
		return trade_detail;
	}
	public void setTrade_detail(String trade_detail) {
		this.trade_detail = trade_detail;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getMerc_order_no() {
		return merc_order_no;
	}
	public void setMerc_order_no(String merc_order_no) {
		this.merc_order_no = merc_order_no;
	}
	public String getSub_merc_id() {
		return sub_merc_id;
	}
	public void setSub_merc_id(String sub_merc_id) {
		this.sub_merc_id = sub_merc_id;
	}
	public double getTrade_amt() {
		return trade_amt;
	}
	public void setTrade_amt(double trade_amt) {
		this.trade_amt = trade_amt;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getReq_ip() {
		return req_ip;
	}
	public void setReq_ip(String req_ip) {
		this.req_ip = req_ip;
	}
	public String getTime_expire() {
		return time_expire;
	}
	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}
	public String getClient_notify_url() {
		return client_notify_url;
	}
	public void setClient_notify_url(String client_notify_url) {
		this.client_notify_url = client_notify_url;
	}
	public String getServer_notify_url() {
		return server_notify_url;
	}
	public void setServer_notify_url(String server_notify_url) {
		this.server_notify_url = server_notify_url;
	}

    public double getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(double transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public String getRecipient_account_number() {
        return recipient_account_number;
    }

    public void setRecipient_account_number(String recipient_account_number) {
        this.recipient_account_number = recipient_account_number;
    }

    public String getRecipient_account_name() {
        return recipient_account_name;
    }

    public void setRecipient_account_name(String recipient_account_name) {
        this.recipient_account_name = recipient_account_name;
    }

    public String getDestination_bank_name() {
        return destination_bank_name;
    }

    public void setDestination_bank_name(String destination_bank_name) {
        this.destination_bank_name = destination_bank_name;
    }

    public String getDestination_bank_code() {
        return destination_bank_code;
    }

    public void setDestination_bank_code(String destination_bank_code) {
        this.destination_bank_code = destination_bank_code;
    }

    public String getRecipient_account_type() {
        return recipient_account_type;
    }

    public void setRecipient_account_type(String recipient_account_type) {
        this.recipient_account_type = recipient_account_type;
    }
}
