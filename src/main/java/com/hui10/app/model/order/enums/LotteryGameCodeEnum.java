package com.hui10.app.model.order.enums;

/**
 * 彩票玩法枚举类
 * @author chiheng
 *
 */
public enum LotteryGameCodeEnum {
	
	DCB("10001", "双色球");

	LotteryGameCodeEnum(String state, String desc) {
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
	
	public static LotteryGameCodeEnum getDesc(Integer state) {
		for (LotteryGameCodeEnum lotteryGameCodeEnum : values()) {
			if (lotteryGameCodeEnum.state.equals(state)) {
				return lotteryGameCodeEnum;
			}
		}
		return null;
	}
}
