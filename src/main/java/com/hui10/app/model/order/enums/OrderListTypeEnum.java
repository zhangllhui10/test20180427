package com.hui10.app.model.order.enums;

/**
 * 订单列表查询类型
 * @author fanht
 *
 */
public enum OrderListTypeEnum {

	BETTED("1", "已投订单"),
    WAITBET("2", "待投订单"),
    EXPIRED("3", "过期订单"),
    WINNING("4", "中奖订单");

	OrderListTypeEnum(String state, String desc) {
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
	
	public static OrderListTypeEnum getEnum(String state) {
		for (OrderListTypeEnum lotteryBetTypeEnum : values()) {
			if (lotteryBetTypeEnum.state.equals(state)) {
				return lotteryBetTypeEnum;
			}
		}
		return null;
	}
}
