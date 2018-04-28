package com.hui10.app.model.enums;

/**
 * 
 * @author chiheng
 *
 */
public enum StationTypeEnum {

	BAOLECAI("1","宝乐彩自营"), HUI10("2","汇彩宝");

	private String code;
	private String desc;

	private StationTypeEnum(String code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static StationTypeEnum getByCode(String code){
		for(StationTypeEnum stationTypeEnum : StationTypeEnum.values()){
			if(stationTypeEnum.getCode().equals(code)){
				return stationTypeEnum;
			}
		}
		return null;
	}
}
