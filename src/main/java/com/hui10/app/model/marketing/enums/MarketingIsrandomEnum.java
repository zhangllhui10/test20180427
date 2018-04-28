package com.hui10.app.model.marketing.enums;
/**
 * @ClassName: MarketingIsrandmEnum.java
 * @Description:
 * @author zhangll
 * @date 2018年2月28日 上午11:46:48
 */
public enum MarketingIsrandomEnum {
	NOT_RANDOM("0","不随机"),RANDOM("1","随机");
	private String code;
	private String desc;
	
	
	/**
	 * @param code
	 * @param desc
	 */
	private MarketingIsrandomEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static MarketingIsrandomEnum getByCode(String code){
		for(MarketingIsrandomEnum misrandom:values()){
			if(misrandom.code.equals(code)){
				return misrandom;
			}
		}
		return null;
	}
	
}
