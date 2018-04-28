package com.hui10.app.model.marketing.enums;

/**
 * 
 * @author chiheng
 *
 */
public enum BetTimeTypeEnum {
	
	BET_IMMEDIATE("1","立即投注"), SPECIFIC("2","指定时间"), SEND_OUT_BET("3","送完立即投注");
	
	BetTimeTypeEnum(String state, String desc) {
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
	
	public static BetTimeTypeEnum findByState(String state) {
		for (BetTimeTypeEnum betTimeTypeEnum : BetTimeTypeEnum.values()) {
			if(betTimeTypeEnum.getState().equals(state)) {
				return betTimeTypeEnum;
			}
		}
		return null;
	}
}
