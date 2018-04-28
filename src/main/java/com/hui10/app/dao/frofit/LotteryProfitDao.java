package com.hui10.app.dao.frofit;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.profit.LotteryProfitDetailProp;
import com.hui10.app.model.profit.LotteryProfitProp;

/**
 * @ClassName: LotteryProfitDao.java
 * @Description:
 * @author zhangll
 * @date 2017年10月26日 下午6:09:57
 */
public interface LotteryProfitDao {
	
	
	public List<LotteryProfitProp> queryProfitProp(@Param("lotterycode") String lotterycode,@Param("provinceid")String provinceid);
	
	public List<LotteryProfitDetailProp> queryProfitDetailProp(@Param("lotterycode") String lotterycode,@Param("provinceid")String provinceid);

}
