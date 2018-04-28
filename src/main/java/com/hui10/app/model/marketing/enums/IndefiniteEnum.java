package com.hui10.app.model.marketing.enums;

/**
 * 
 * @author chiheng
 *
 */
public enum IndefiniteEnum {
	
	NO("0","有限期"), YES("1","无限期");
	
	IndefiniteEnum(String state, String desc) {
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
