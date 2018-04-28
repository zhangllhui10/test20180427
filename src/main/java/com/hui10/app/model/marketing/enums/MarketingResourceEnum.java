package com.hui10.app.model.marketing.enums;

/**
 * 
 * @author chiheng
 *
 */
public enum MarketingResourceEnum {
	
	LOCAL("1","本地"), YINGTAI("2","英泰");
	
	MarketingResourceEnum(String state, String desc) {
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
	
	public static MarketingResourceEnum findByState(String state) {
		for (MarketingResourceEnum marketingResourceEnum : MarketingResourceEnum.values()) {
			if(marketingResourceEnum.getState().equals(state)) {
				return marketingResourceEnum;
			}
		}
		return null;
	}
}
