package com.hui10.app.model.order.enums;

/**
 * 彩票选码方式枚举类
 * @author chiheng
 *
 */
public enum LotterySelectTypeEnum {
	
	RANDOM(1, "机选"), SELF(2, "自选");

	LotterySelectTypeEnum(Integer state, String desc) {
		this.state = state;
		this.desc = desc;
	}

	private Integer state;
	private String desc;
	
	public Integer getState() {
		return state;
	}
	
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static LotterySelectTypeEnum getDesc(Integer state) {
		for (LotterySelectTypeEnum lotterySelectTypeEnum : values()) {
			if (lotterySelectTypeEnum.state.equals(state)) {
				return lotterySelectTypeEnum;
			}
		}
		return null;
	}
}
