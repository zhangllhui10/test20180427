package com.hui10.app.model.order.enums;

/**
 * 投注方式枚举类
 * @author chiheng
 *
 */
public enum LotteryBetTypeEnum {
	
	SINGLE("1000101", "单式"), MULTIPLE("1000101", "复式"), DANTUO("1000102", "胆拖");

	LotteryBetTypeEnum(String state, String desc) {
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
	
	public static LotteryBetTypeEnum getDesc(Integer state) {
		for (LotteryBetTypeEnum lotteryBetTypeEnum : values()) {
			if (lotteryBetTypeEnum.state.equals(state)) {
				return lotteryBetTypeEnum;
			}
		}
		return null;
	}
}
