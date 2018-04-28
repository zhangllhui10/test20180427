package com.hui10.app.model.enums;

import com.hui10.common.utils.StringUtils;

/**
 * @ClassName: LotteryStatusEnum.java
 * @Description:
 * @author wengf
 * @date 2017年10月25日 上午11:29:37
 */
public enum LotteryStatusEnum {
	

	NO_OPEN("0","未开奖"),HAVE_OPE("1", "开奖");

	LotteryStatusEnum(String code, String desc) {
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
	
	public static LotteryStatusEnum getDesc(String code) {
		for (LotteryStatusEnum lotteryStatusEnum : values()) {
			if (StringUtils.equals(lotteryStatusEnum.code, code)) {
				return lotteryStatusEnum;
			}
		}
		return null;
	}

}
