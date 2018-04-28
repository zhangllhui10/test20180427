package com.hui10.app.model.order.enums;

/**
 * 订单状态枚举类
 * @author chiheng
 *
 */
public enum OrderStatusEnum {
	
	NOPAY(1, "未支付"), CANCEL(3, "取消"), PAID(2, "支付成功"), EXPIRE(4,"已过期"),FAILBILL(5,"出票失败"),PARTTICKET(6,"部分出票");

	OrderStatusEnum(int state, String desc) {
		this.state = state;
		this.desc = desc;
	}

	private int state;
	private String desc;
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static OrderStatusEnum getDesc(Integer state) {
		for (OrderStatusEnum lotteryStatusEnum : values()) {
			if (lotteryStatusEnum.state==state) {
				return lotteryStatusEnum;
			}
		}
		return null;
	}
}
