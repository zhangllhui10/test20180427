package com.hui10.app.dao.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.order.OrderAccountBalanceDetail;
import com.hui10.app.model.order.OrderAccountBalanceInfo;

public interface HuiOrderBalanceDao {
	
	/**
	 * 查找对账信息
	 * @param accountid
	 * @return
	 */
	OrderAccountBalanceInfo findOneInfo(@Param("accountid")String accountid);
	
	/**
	 * 新增对账信息
	 * @param orderAccountBalanceInfo
	 * @return
	 */
	int addOneInfo(OrderAccountBalanceInfo orderAccountBalanceInfo);

	/**
	 * 新增对账明细信息
	 * @param orderAccountBalanceDetails
	 * @return
	 */
	int addDetails(@Param("list")List<OrderAccountBalanceDetail> orderAccountBalanceDetails);
	
	/**
	 * 查询对账结果
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @param index
	 * @param offset
	 * @return
	 */
	public List<OrderAccountBalanceInfo> queryAccountBalanceInfo(@Param("status")String status, @Param("starttime")String starttime, 
			@Param("endtime")String endtime, @Param("index")Integer index, @Param("offset")Integer offset);
	
	/**
	 * 查询对账结果个数
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public int queryAccountBalanceInfoCount(@Param("status")String status, @Param("starttime")String starttime, @Param("endtime")String endtime);
	
	/**
	 * 查询对账异常明细
	 * @param accountid
	 * @param index
	 * @param offset
	 * @return
	 */
	public List<OrderAccountBalanceDetail> queryAccountBalanceDetails(@Param("accountid")String accountid,
			@Param("index")Integer index, @Param("offset")Integer offset);
	
	/**
	 * 查询对账异常明细个数
	 * @param accountid
	 * @return
	 */
	public int queryAccountBalanceDetailsCount(@Param("accountid")String accountid);
}
