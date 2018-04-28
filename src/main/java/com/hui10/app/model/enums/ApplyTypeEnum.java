package com.hui10.app.model.enums;
/**
 * @ClassName: ApplyTypeEnum.java
 * @Description:
 * @author zhangll
 * @date 2017年11月3日 上午11:40:30
 */
public enum ApplyTypeEnum {
	MERCHANT_APPLY("2","商户申请信息"),ACQUIRER_APPLY("1","机构申请信息");
	private String code;
	private String desc;
	/**
	 * @param code
	 * @param desc
	 */
	private ApplyTypeEnum(String code, String desc) {
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
