package com.hui10.app.model.enums;
/**
 * 提现状态
 * @author  fanht
 * @time 2017年9月6日 下午17:56:45
 */
public enum ExtractCashStatusEnum {

	EXTRACT_APPLY("1", "申请提现"), EXTRACT_SUCCESS("2", "提现成功"), EXTRACT_FAIL("3", "提现失败");

	ExtractCashStatusEnum(String state, String desc) {
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
	public static ExtractCashStatusEnum getDesc(Integer state) {

		for (ExtractCashStatusEnum extractCashStatusEnum : values()) {
			if (extractCashStatusEnum.state.equals(state)) {
				return extractCashStatusEnum;
			}
		}
		return null;
	}
}
