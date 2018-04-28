package com.hui10.app.dao.lottery;

import com.hui10.app.model.lottery.LotteryInfo;
import com.hui10.app.model.lottery.LotteryRegion;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: LotteryInfoDao.java
 * @Description:
 * @author wengf
 * @date 2017年10月26日 下午3:33:19
 */
public interface LotteryInfoDao {
	
	public LotteryInfo selectByCode(@Param("lotterycode")String lotterycode);

	/**
	 * 查询全部彩种信息
	 * @return
	 */
	public List<LotteryInfo> selectList();
	
	public List<LotteryInfo> queryLotteryInfoByCode(@Param("lotterycode") String lotterycode);
	
	public Integer updateLotteryInfo(LotteryInfo lotteryInfo);
	
	public Integer insertLotteryInfo(LotteryInfo lotteryInfo);
	
	public Integer deleteLotteryRegion(@Param("lotterycode") String lotterycode);
	
	public Integer insertLotterRegion(List<LotteryRegion> list);
	
	public List<LotteryInfo> queryLotteryListByNameOrCode(@Param("lotterycode") String lotterycode,@Param("lotteryname") String lotteryname);
	
	public List<LotteryInfo> queryLotteryListByName(@Param("lotterycode") String lotterycode,@Param("lotteryname") String lotteryname);
	
}
