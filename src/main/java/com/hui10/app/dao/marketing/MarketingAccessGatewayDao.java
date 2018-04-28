package com.hui10.app.dao.marketing;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.marketing.MarketingAccessGateway;

public interface MarketingAccessGatewayDao {

    int insert(MarketingAccessGateway record);

    int insertSelective(MarketingAccessGateway record);

    MarketingAccessGateway selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MarketingAccessGateway record);

    int updateByPrimaryKey(MarketingAccessGateway record);
    List<MarketingAccessGateway> selectByIds(@Param("list") List<Integer> id);
}