package com.hui10.app.model.marketing.enums;
/**
 * @ClassName: MarketingStatusEnum.java
 * @Description:
 * @author zhangll
 * @date 2018年2月27日 下午3:44:30
 */
public enum GivingRecordStatusEnum {
	
	NOT_RECEIVE("1","未领取"), ALREADY_RECEIVE("2","已领取"), EXPIRED("3","已过期");
	
	GivingRecordStatusEnum(String state, String desc) {
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
}
