package com.hui10.app.model.marketing.enums;
/**
 * 
 * @author chiheng
 *
 */
public enum LotteryPoolTicketStatusEnum {
	
	STILL_IN_POOL("0","未赠送"),OUT_FROM_POOL("1","已赠送");
	
	private String code;
	private String desc;
	/**
	 * @param code
	 * @param desc
	 */
	private LotteryPoolTicketStatusEnum(String code, String desc) {
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
	
	public static LotteryPoolTicketStatusEnum getByCode(String code){
		for(LotteryPoolTicketStatusEnum pool : values()){
			if(pool.code.equals(code)){
				return pool;
			}
			
		}
		return null;
	}
}
