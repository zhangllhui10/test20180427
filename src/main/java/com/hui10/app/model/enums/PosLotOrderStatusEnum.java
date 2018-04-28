package com.hui10.app.model.enums;

public enum PosLotOrderStatusEnum {

	/**
	 * 0-10 订单状态， 11-20 支付状态 21 -30 出票状态 31-40 中奖状态  41-50 派奖
	 *
	 */
	INIT(0), CANCEL(1), ENDCANCEL(2), TOBUY(8), TOPAY(9), PAID(15),   TICKETFAIL(21) , TICKETING(22),  PARTTICKET(23), ALLTICKET(24), PRIZED(31), NOPRIZE(35), SMALLPRIZE(36),  MEDIUMPRIZE(37),  BIGPRIZE(38);

	private PosLotOrderStatusEnum(int value) {
		this.value = value;
	}

	private int value;

	public int getValue() {
		return this.value;
	}
	
	public static PosLotOrderStatusEnum findByValue(int value) {
		for (PosLotOrderStatusEnum orderStatus : PosLotOrderStatusEnum.values()) {
			if(orderStatus.getValue() == value) {
				return orderStatus;
			}
		}
		return null;
	}

}
