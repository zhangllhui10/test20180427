package com.hui10.app.model.enums;

import com.poslot.enums.lottery.OrderStatus;

/**
 * @ClassName: PrizeLevelEnum.java
 * @Description:是否为大奖
 * @author zhangll
 * @date 2017年10月20日 上午10:17:17
 */
public enum PrizeLevelEnum {
	SMALL("1","小奖"),MEDIUM("2","中奖"),BIG("3","大奖"),
	POSLOT_SMALL(String.valueOf(OrderStatus.SMALLPRIZE.getValue()),"小奖"),
	POSLOT_MEDIUM(String.valueOf(OrderStatus.MEDIUMPRIZE.getValue()),"中奖"),
	POSLOT_BIG(String.valueOf(OrderStatus.BIGPRIZE.getValue()),"大奖");;
	private String code;
	private String desc;
	/**
	 * @param code
	 * @param desc
	 */
	private PrizeLevelEnum(String code, String desc) {
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
