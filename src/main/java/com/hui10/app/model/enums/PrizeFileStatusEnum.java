package com.hui10.app.model.enums;
/**
 * @ClassName: PrizeFileStatusEnum.java
 * @Description:开奖通知文件是否处理过
 * @author zhangll
 * @date 2017年10月18日 下午4:09:49
 */
public enum PrizeFileStatusEnum {
	UNTREATED("0","未处理"),TREATED("1","已处理");
	
	private String code;
	private String desc;
	/**
	 * @param code
	 * @param desc
	 */
	private PrizeFileStatusEnum(String code, String desc) {
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
