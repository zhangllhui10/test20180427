package com.hui10.app.model.enums;
/**
 * @ClassName: CityTypeEnum.java
 * @Description:城市类型：1省份，2城市，3区县
 * @author fanzy
 * @date 2017年4月5日 下午2:45:03
 */
public enum CityTypeEnum {
	
	PROVINCE("1","省份"),CITY("2","城市"),DISTRICT("3","区县");
	

	private String state;
	private String stateInfo;
	
	
	private CityTypeEnum(String state,String stateInfo){
		this.state=state;
		this.stateInfo=stateInfo;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getStateInfo() {
		return stateInfo;
	}


	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	
	
	
}
