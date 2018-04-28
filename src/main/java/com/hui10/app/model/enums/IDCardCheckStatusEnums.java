package com.hui10.app.model.enums;

/**
 * @ClassName: IDCardCheckStatusEnums.java
 * @Description:
 * @author wengf
 * @date 2017年11月6日 下午3:09:00
 */
public enum IDCardCheckStatusEnums {

	NO_VALID("5","待审核"),DRAW("2","派发成功"),DRAW_FAIL("4","派发失败"),CER_CHECK_FAIL("6", "证件审核失败"), CER_CHECK_OK("7", "证件审核成功");

	private String code;
	private String desc;

	private IDCardCheckStatusEnums(String code, String desc) {
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

	public static IDCardCheckStatusEnums getByCode(String code) {

		for (IDCardCheckStatusEnums status : values()) {
			if (status.code.equals(code)) {
				return status;
			}
		}
		return null;
	}

}
