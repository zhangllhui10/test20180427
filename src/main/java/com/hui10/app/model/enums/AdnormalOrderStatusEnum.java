package com.hui10.app.model.enums;

import com.hui10.common.utils.StringUtils;

/**
 * @ClassName: AdnormalOrderStatusEnum.java
 * @Description:
 * @author huangrui
 * @date 2018/4/3 15:55:21
 */
public enum AdnormalOrderStatusEnum {


	UNTREATED("1","未处理"), NOT_PAY_MONEY("2", "已处理不打款"), PAY_MONEY("3", "已处理并打款"), PAY_MONEY_FAIL("4", "打款失败");

	AdnormalOrderStatusEnum(String code, String desc) {
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
	
	public static AdnormalOrderStatusEnum getEnum(String code) {
		for (AdnormalOrderStatusEnum adnormalOrderStatusEnum : values()) {
			if (StringUtils.equals(adnormalOrderStatusEnum.code, code)) {
				return adnormalOrderStatusEnum;
			}
		}
		return null;
	}

}
