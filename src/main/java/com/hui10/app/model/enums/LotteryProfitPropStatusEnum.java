package com.hui10.app.model.enums;
/**
 * @ClassName: LotteryProfitPropStatusEnum.java
 * @Description:
 * @author zhangll
 * @date 2017年10月27日 下午4:44:44
 */
public enum LotteryProfitPropStatusEnum {
	ENABLE_WAITING("0","待启用"),ENABLE("1","启用"),DISABLE("2","停用"),STOP("3","终止");
	private String code;
	private String desc;
	/**
	 * @param code
	 * @param desc
	 */
	private LotteryProfitPropStatusEnum(String code, String desc) {
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
	
	

}
