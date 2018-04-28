package com.hui10.app.service.order;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.hui10.app.model.order.OrderAccount;
import com.hui10.app.model.order.OrderAccountBalanceDetail;
import com.hui10.app.model.order.OrderAccountBalanceDetailVo;
import com.hui10.app.model.order.OrderAccountBalanceInfo;
import com.hui10.app.model.order.OrderAccountBalanceInfoVo;

public interface HuiOrderBalanceService {
	/**
	 * @param accountid
	 * @return
	 */
	public OrderAccountBalanceInfo findOneInfo(String accountid);
	
	/**
	 * 从ftp服务器下载csv文件
	 * accounttime yyyyMMdd
	 * @return
	 */
	public String downloadCSV(String accounttime);
	
	/**
	 * 新增对账信息
	 * @param orderAccountBalanceInfo
	 * @return
	 */
	public int addOneInfo(OrderAccountBalanceInfo orderAccountBalanceInfo);
	
	/**
	 * 新增对账明细信息
	 * @param orderAccountBalanceDetails
	 * @return
	 */
	public int addDetails(List<OrderAccountBalanceDetail> orderAccountBalanceDetails);
	
	/**
	 * 从文件获取数据生成map
	 * @param file
	 * @return
	 */
	public Map<String, OrderAccount> generateMapFromFile(File file) throws Exception;
	
	/**
	 * 对账
	 * @return
	 */
	public int accountBalance();
	
	/**
	 * 查询对账结果
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	public OrderAccountBalanceInfoVo queryAccountBalanceInfo(String status, String starttime, String endtime, Integer pageno, Integer pagesize);
	
	/**
	 * 查询对账异常明细
	 * @param accountid
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	public OrderAccountBalanceDetailVo queryAccountBalanceDetails(String accountid, Integer pageno, Integer pagesize);
	
	
	/**
	 * 查询对账结果个数
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	public int queryAccountBalanceInfoCount(String status, String starttime, String endtime);
	
	/**
	 * 查询对账异常明细个数
	 * @param accountid
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	public int queryAccountBalanceDetailsCount(String accountid);
	
	/**
	 * @param accountid
	 * @param response
	 * @return
	 */
	public void downloadFile(String accountid, HttpServletResponse response);
}
