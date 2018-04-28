package com.hui10.app.service.loterry;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.model.lottery.LotteryInfo;
import com.hui10.app.model.lottery.LotteryOpenVo;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-11-09 10:45
 **/
public interface LotteryInfoService {

    /**
     * 获取开奖日期列表
     * @return
     */
    List<LotteryOpenVo> getLotteryOpenVo();
    
    Map<String,Object> queryLotteryInfoByCode(Map<String,String> lotteryInfo);
    /**
     * 开奖通知时，将文件基本信息入库
     * @param transData
     * @return
     * @user zhangll
     * @date 2018年4月8日 下午2:42:13
     */
    LotteryInfo lotteryInfoInsert(JSONObject transData);
    
    public void addOrUpdateLotteryInfo(LotteryInfo lotteryInfo,boolean isadd);
    /**
     * 营销开奖通知时，将文件基本信息存储到数据库
     * @param transData
     * @return
     * @user zhangll
     * @date 2018年4月8日 下午2:41:19
     */
    LotteryInfo lotteryInfoInsertMarket(JSONObject transData);



}
