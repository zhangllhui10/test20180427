package com.hui10.app.dao.marketing;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.marketing.Marketing;
import com.hui10.app.model.marketing.MarketingGroup;

public interface MarketingDao {
    int insert(Marketing record);

    Marketing selectById(String marketingid);

    int updateById(Marketing record);
    
    Marketing selectByMarketingname(@Param("marketingname") String marketingname,@Param("marketingid")String marketingid);
    
    List<Marketing> findCurrMarketings(@Param("lotterycode")String lotterycode);
    
    List<Marketing> queryMarketingList();
    
    List<MarketingGroup> findMarketingGroups(@Param("marketingid")String marketingid, @Param("channelid")String channelid);
}