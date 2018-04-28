package com.hui10.app.model.enums;
/**
 * @ClassName: UserGenderEnum.java
 * @Description:
 * @author zhangll
 * @date 2018年3月2日 上午10:42:45
 */
public enum UserGenderEnum {
	MAN("1","男性"),WOMEN("2","女性"),NONE("0","未知");
	private String code;
	private String desc;
	
	
	/**
	 * @param code
	 * @param desc
	 */
	private UserGenderEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static UserGenderEnum getByCode(String code){
		for(UserGenderEnum user:values()){
			if(user.getCode().equals(code)){
				return user;
			}
		}
		return null;
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
