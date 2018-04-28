package com.hui10.app.dao.marketing;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.model.marketing.MarketingAccessGroup;

public interface MarketingAccessGroupDao {
    int deleteByPrimaryKey(String groupid);

    int insert(MarketingAccessGroup record);

    int insertSelective(MarketingAccessGroup record);

    MarketingAccessGroup selectByPrimaryKey(String groupid);

    int updateByPrimaryKeySelective(MarketingAccessGroup record);

    int updateByPrimaryKey(MarketingAccessGroup record);
    
    int insertBatch(@Param("list") List<MarketingAccessGroup> marketingAccessGroupList);
    
    int insertBatchGroupCity(@Param("list") List<JSONObject> cityLIst);
    
    int insertBatchGroupMerchant(@Param("list") List<JSONObject> merchantList);
    
    List<MarketingAccessGroup> selectByMerchantname(@Param("set") Set<String> merchantnameList,@Param("marketingid") String marketingid);
    
    int deleteBatchCityByGroupId(@Param("list") List<JSONObject> groupid);
    
    int deleteBatchMerchantByGroupId(@Param("list")List<JSONObject> groupid);
    
    int deleteBatchGroupById(@Param("list") List<MarketingAccessGroup> marketingAccessGroupList);
    
    int deleteBatchCityByMarketingId(@Param("marketingid") String marketingid);
    
    int deleteBatchMerchantByMarketingId(@Param("marketingid") String marketingid);
}