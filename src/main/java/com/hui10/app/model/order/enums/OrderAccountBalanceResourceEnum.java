package com.hui10.app.model.order.enums;

/**
 * 对账订单来源枚举类
 * @author chiheng
 *
 */
public enum OrderAccountBalanceResourceEnum {
	
	BAOLECAI("1", "宝乐彩"), HUI10("2", "汇彩宝"), BOTH("3", "双方订单");

	OrderAccountBalanceResourceEnum(String state, String desc) {
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
	
	public static OrderAccountBalanceResourceEnum getDesc(Integer state) {
		for (OrderAccountBalanceResourceEnum orderAccountBalanceResource : values()) {
			if (orderAccountBalanceResource.state.equals(state)) {
				return orderAccountBalanceResource;
			}
		}
		return null;
	}
}
