package com.hui10.pc.service.fenrun;

import java.util.Map;

/**
 * @ClassName: FenRunService.java
 * @Description:
 * @author huangrui
 * @date 2018年4月24日  14:28:54
 */
public interface FenRunService {
	
	/**
	  * 查询商户上月彩票销售分润 
	  * @param merchantno 商户号
	  */
	public long queryFenRunOfLastMonth(String merchantno);
	
	
	/**
	  * 查询商户彩票销售分润列表
	  * @param merchantno 商户号
	 * @throws Exception 
	  */
	public Map<String, Object> queryFenRunList(String merchantno, Integer pageno, Integer pagesize);

}
