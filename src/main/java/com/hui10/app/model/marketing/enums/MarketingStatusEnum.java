package com.hui10.app.model.marketing.enums;
/**
 * @ClassName: MarketingStatusEnum.java
 * @Description:
 * @author zhangll
 * @date 2018年2月27日 下午3:44:30
 */
public enum MarketingStatusEnum {
	ENABLE("1","启用"),DISABLE("0","停用"),DELAY_ENABLE("2","待启用");
	private String code;
	private String desc;
	/**
	 * @param code
	 * @param desc
	 */
	private MarketingStatusEnum(String code, String desc) {
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
	
	public static MarketingStatusEnum getByCode(String code){
		for(MarketingStatusEnum mse : values()){
			if(mse.code.equals(code)){
				return mse;
			}
			
		}
		return null;
	}
}
