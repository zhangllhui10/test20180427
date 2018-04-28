package com.hui10.app.model.order.enums;

/**
 * 对账状态枚举类
 * @author chiheng
 *
 */
public enum OrderAccountBalanceStatusEnum {
	
	NO("0", "无异常"), YES("1", "有异常");

	OrderAccountBalanceStatusEnum(String state, String desc) {
		this.state = state;
		this.desc = desc;
	}

	private String state;
	private String desc;
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static OrderAccountBalanceStatusEnum getDesc(Integer state) {
		for (OrderAccountBalanceStatusEnum orderAccountBalanceStatus : values()) {
			if (orderAccountBalanceStatus.state.equals(state)) {
				return orderAccountBalanceStatus;
			}
		}
		return null;
	}
}
