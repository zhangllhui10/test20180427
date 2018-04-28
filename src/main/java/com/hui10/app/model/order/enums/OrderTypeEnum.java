package com.hui10.app.model.order.enums;

/**
 * 订单类型枚举类
 * @author huangrui
 *
 */
public enum OrderTypeEnum {
	
	ORDINARY("1", "普通"), GIVING("2", "赠送");

	OrderTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private String code;
	private String desc;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static OrderTypeEnum getDesc(String code) {
		for (OrderTypeEnum orderTypeEnum : values()) {
			if (orderTypeEnum.code.equals(code)) {
				return orderTypeEnum;
			}
		}
		return null;
	}
}
