package com.hui10.app.service.adnormalorder;

public interface AdnormalOrderSerice {
    
	/**
     * 新增异常订单
     * @param orderid 彩票订单号
     * @param outtradeno 支付流水号
     * @param userphone 用户手机号
     * @param bankcardid 用户银行卡绑定ID
     * @param username 操作人名称
     * @return
     */
	public String addAdnormalOrder(String orderid, String outtradeno, String userphone, String bankcardid, String username);

	/**
     * 处理异常订单
     * @param id 异常订单记录ID
     * @param bankcardid 用户银行卡绑定ID
     * @param amount 打款金额
     * @param status 状态（1-未支付，2-出票成功，3-取消, 4-已过期、5-出票失败）
     * @param username 操作人名称
     * @return
     */
	public String modifyAdnormalOrder(String id, String bankcardid, long amount, String status, String username);
}
