package com.hui10.app.model.enums;
/**
 * @ClassName: UserIsNewUserEnum.java
 * @Description:
 * @author zhangll
 * @date 2017年12月5日 下午3:32:50
 */
public enum UserIsNewUserEnum {
	IS_NEW("1","新用户"),IS_NOT_NEW("0","不是新用户");
	
	private String code;
	private String desc;
	/**
	 * @param code
	 * @param desc
	 */
	private UserIsNewUserEnum(String code, String desc) {
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
