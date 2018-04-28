package com.hui10.app.model.enums;
/**
 * @ClassName: PrizeWinStatusEnum.java
 * @Description:是否中奖
 * @author zhangll
 * @date 2017年10月20日 上午10:06:55
 */
public enum PrizeWinStatusEnum {
	NOT_WIN("1","未中奖"),WIN("2","中奖"),NOT_OPEN("0","未开奖");
	private String code;
	private String desc;
	/**
	 * @param code
	 * @param desc
	 */
	private PrizeWinStatusEnum(String code, String desc) {
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
