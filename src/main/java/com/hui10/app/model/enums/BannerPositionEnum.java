package com.hui10.app.model.enums;

import com.hui10.common.utils.StringUtils;

/**
 * @ClassName: BannerStatusEnum.java
 * @Description:
 * @author huangrui
 * @date 2018/4/3  11:33:37
 */
public enum BannerPositionEnum {


	LUNBO("1", "轮播"), XUANFU("2", "悬浮");

	BannerPositionEnum(String code, String desc) {
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
	
	public static BannerPositionEnum getEnum(String code) {
		for (BannerPositionEnum bannerPositionEnum : values()) {
			if (StringUtils.equals(bannerPositionEnum.code, code)) {
				return bannerPositionEnum;
			}
		}
		return null;
	}

}
