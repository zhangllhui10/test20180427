package com.hui10.app.model.lottery.enums;
/**
 * 
 * @author chiheng
 *
 */
public enum LotteryCodeEnum {
	
	UNIONLOTTO("10001","双色球"), QUICK3("99998","快三");
	
	LotteryCodeEnum(String state, String desc) {
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
	
	public static LotteryCodeEnum findByState(String state) {
		for (LotteryCodeEnum lotteryCodeEnum : LotteryCodeEnum.values()) {
			if (lotteryCodeEnum.getState().equals(state)) {
				return lotteryCodeEnum;
			}
		}
		return null;
	}
}
