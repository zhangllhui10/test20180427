package com.hui10.app.service.user;

import org.springframework.web.multipart.MultipartFile;

import com.hui10.app.model.user.MediumHandle;

/**
 * @ClassName: WithDrawService.java
 * @Description:
 * @author wengf
 * @date 2017年10月19日 下午4:39:47
 */
public interface WithDrawService {
	
	/**
	 * 提现
	 * @param uid 提现用户
	 * @param orderid 要领奖的订单编号
	 * @return
	 * @user wengf
	 * @date 2017年10月19日 下午4:42:12
	 */
	public int withDraw(String uid, String orderid, String bankcardid);
	
	/**
	 * 
	 * @param auditor 审核人
	 *  @param idcard ID
	 * @param orderid 审核订单
	 * @param status 审核状态
	 * @return
	 * @user wengf
	 * @date 2017年11月6日 下午3:07:23
	 */
	public int auditMediumHandle(String auditor, MediumHandle handle, MultipartFile screenshort);
	
	/**
	 * 查询兑奖打钱是否成功
	 * 
	 * @user wengf
	 * @date 2017年10月25日 下午3:29:03
	 */
	public void timetaskQueryExtractStatus();
	

}
