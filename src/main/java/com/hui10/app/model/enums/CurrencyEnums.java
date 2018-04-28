package com.hui10.app.model.enums;

public enum CurrencyEnums {

	RMB("CNY","人民币");

	
	private CurrencyEnums(String code, String msg){
		this.code=code;
		this.msg=msg;
	}
	
	private String code;
	private String msg;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	

	
}
