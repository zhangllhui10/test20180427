package com.hui10.app.dao.banner;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.banner.BannerInfo;


/**
 * @author huangrui
 * @ClassName:
 * @Description:
 * @date 2018年1月22日 11:43
 */
public interface BannerDao {

    int addBanner(BannerInfo bannerInfo);

    int modifyBanner(BannerInfo bannerInfo);
    
    BannerInfo queryBannerInfo(@Param("id") String id);
    
    List<BannerInfo> queryBannerList(@Param("position") String position);
     
}
