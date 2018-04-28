package com.hui10.app.model.enums;

import com.hui10.common.utils.StringUtils;

/**
 * @ClassName: TicketStatusEnum.java
 * @Description:
 * @author wengf
 * @date 2017年10月25日 上午11:29:37
 */
public enum TicketStatusEnum {


	INIT("0","初始化"),
    SUCCESS("1", "成功"),
    FAIL("2", "失败");

	TicketStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private String code;
	private String desc;
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static TicketStatusEnum getEnum(String code) {
		for (TicketStatusEnum lotteryStatusEnum : values()) {
			if (StringUtils.equals(lotteryStatusEnum.code, code)) {
				return lotteryStatusEnum;
			}
		}
		return null;
	}

}
