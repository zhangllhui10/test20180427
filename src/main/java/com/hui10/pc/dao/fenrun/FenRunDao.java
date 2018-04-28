package com.hui10.pc.dao.fenrun;

import java.util.List;
import com.hui10.pc.model.fenrun.FenRun;

/**
 * @ClassName: FenRunDao.java
 * @Description:
 * @author huangrui
 * @date 2018年4月24日  14:28:54
 */
public interface FenRunDao {
	
	 /**
	  * 查询商户上月彩票销售分润 
	  * @param merchantno 商户号
	  */
	String queryFenRunOfLastMonth(String merchantno);
	
	/**
	  * 查询商户彩票销售分润总额
	  * @param merchantno 商户号
	  */
	String queryFenRunTotalAmount(String merchantno);
	
	/**
	  * 查询商户彩票销售分润列表
	  * @param merchantno 商户号
	  */
	List<FenRun> queryFenRunList(String merchantno);
}
