package com.hui10.app.service.main;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.model.lottery.LotteryPast;
import com.hui10.app.model.main.HomeLottery;
import com.hui10.app.model.main.HomePage;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: MainService.java
 * @Description:
 * @author wengf
 * @date 2017年10月26日 下午3:40:51
 */
public interface MainService {
	
	public HomePage getPage(String uid);
	
	public List<LotteryPast> gePasts();

    /**
     * 首页查询
     * @param uid
     * @return
     */
	JSONObject queryHome(String uid);

    /**
     * 重置订单状态
     * @param homeLottery
     * @param systemDate
     */
	void resetOrderStatus(HomeLottery homeLottery, Date systemDate, boolean winPrizeFlag);

    /**
     * 最近使用银行卡信息
     * @param uid
     * @param winPrizeFlag
     * @return
     */
    JSONObject getUserLastUsedCard(String uid, boolean winPrizeFlag);

    /**
     * 获取第一注注码
     * @param remark
     * @return
     */
    String getFirstCodetail(String remark);
}
