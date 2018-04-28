package com.hui10.app.model.enums;

import com.hui10.common.utils.StringUtils;

/**
 * @ClassName: MessageTypeEnum.java
 * @Description:
 * @author huangrui
 * @date 2018年4月8日  20:13:10
 */
public enum MessageTypeEnum {
	
	BET_NOTIFY("1","投注通知"), WIN_NOTIFY("2","中奖通知"), SEND_SUCCESS_NOTIFY("3","派奖成功通知"), SEND_FAIL_NOTIFY("4","派奖失败通知");
		
	private String code;
	private String desc;

	private MessageTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

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
	
	public static MessageTypeEnum getDesc(String code) {
		for (MessageTypeEnum messageTypeEnum : values()) {
			if (StringUtils.equals(messageTypeEnum.code, code)) {
				return messageTypeEnum;
			}
		}
		return null;
	}
}
