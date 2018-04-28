package com.hui10.app.model.enums;
/**
 * @ClassName: PrizeBonusStatus.java
 * @Description:彩金领取
 * @author zhangll
 * @date 2017年10月20日 上午10:20:37
 */
public enum PrizeBonusStatusEnum {
	NOT_DRAW("1","未领取"),DRAW("2","已领取"),DRAWING("3","领奖处理中") ,DRAW_ERROR("4","领取失败"),CER_CHECKING("5","证件审核中"),CER_CHECK_FAIL("6","证件审核失败"),
	CER_CHECK_OK("7", "证件审核成功"), OFF_LINE_DRAW("8","线下领奖"),DRAW_EXPIRE("9","过期未领作废");
	private String code;
	private String desc;
	
	
	/**
	 * @param code
	 * @param desc
	 */
	private PrizeBonusStatusEnum(String code, String desc) {
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
