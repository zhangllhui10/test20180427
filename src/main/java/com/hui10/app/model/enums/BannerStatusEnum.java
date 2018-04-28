package com.hui10.app.model.enums;

import com.hui10.common.utils.StringUtils;

/**
 * @ClassName: BannerStatusEnum.java
 * @Description:
 * @author huangrui
 * @date 2018年1月22日  15:05:37
 */
public enum BannerStatusEnum {
	

	STOP("0","停用"), START("1", "启用");

	BannerStatusEnum(String state, String desc) {
		this.state = state;
		this.desc = desc;
	}

	private String state;
	private String desc;
	
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static BannerStatusEnum getEnum(String state) {
		for (BannerStatusEnum bannerStatusEnum : values()) {
			if (StringUtils.equals(bannerStatusEnum.state, state)) {
				return bannerStatusEnum;
			}
		}
		return null;
	}
}
