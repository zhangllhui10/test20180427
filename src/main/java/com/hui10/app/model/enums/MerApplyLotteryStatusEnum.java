package com.hui10.app.model.enums;
/**
 * @ClassName: MerApplyLotteryStatusEnum.java
 * @Description:
 * @author zhangll
 * @date 2017年10月30日 下午5:33:18
 */
public enum MerApplyLotteryStatusEnum {

	 UNSUBMITTED("0", "未提交"), IN_AUDIT("1", "审核中"), AUDIT_PASS("2", "审核通过"), AUDIT_REFUSE("3", "审核拒绝");
	private String code;
	private String desc;
	/**
	 * @param code
	 * @param desc
	 */
	private MerApplyLotteryStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static MerApplyLotteryStatusEnum getDesc(String code) {

		for (MerApplyLotteryStatusEnum merApplyLotteryStatusEnum : values()) {
			if (merApplyLotteryStatusEnum.code.equals(code)) {
				return merApplyLotteryStatusEnum;
			}
		}
		return null;
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
