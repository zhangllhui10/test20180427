package com.hui10.app.model.enums;


/**
 * @ClassName: ExtractCashStatusEnums.java
 * @Description:
 * @author wengf
 * @date 2017年10月25日 下午3:44:26
 */
public enum ExtractCashStatusEnums {
	
	DOING("DOING","付款中"),
    SUCCESS("SUCCESS","付款成功"),
    FAILED("FAILED","付款失败");


	private ExtractCashStatusEnums(String code, String msg){
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
	
	
	public static ExtractCashStatusEnums getByCode(String code){
		for(ExtractCashStatusEnums tradeChannelEnums:values()){
			if(tradeChannelEnums.getCode().equals(code.toUpperCase())){
				return tradeChannelEnums;
			}
		}
		return null;
	}

}
