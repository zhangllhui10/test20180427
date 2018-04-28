package com.hui10.app.model.enums;

/**
 * @ClassName: HomeLotteryStatusEnum.java
 * @Description:
 * @author fanht
 * @date 2018年1月22日  15:05:37
 */
public enum HomeLotteryStatusEnum {


	WAIT_RECEIVE(0,"待领取"),
	WAIT_PAY(1,"待支付"),
	WAIT_OPEN(2,"待开奖"),
    WINNING(3, "已中奖"),
    NOT_WINNING(4, "未中奖"),
    EXPIRED(5, "已过期"),
    TICKET_FAIL(6, "出票失败"),
    CANCLE(7, "取消");

	HomeLotteryStatusEnum(int state, String desc) {
		this.state = state;
		this.desc = desc;
	}

	private int state;
	private String desc;
	
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
