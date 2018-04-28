package com.hui10.app.service.banner;

import java.util.List;
import com.hui10.app.model.banner.BannerInfo;

/**
 * @ClassName: BannerService.java
 * @Description:营销广告管理接口
 * @author huangrui
 * @date 2018年1月22日 11:43:21
 */
public interface BannerService {
	
	/**
     * 新增广告
     * @param bannerInfo 广告信息
     * @return
     */
    public String addBanner(BannerInfo bannerInfo, String username);
    
    /**
     * 修改广告
     * @param bannerInfo 广告信息
     * @return
     */
    public String modifyBanner(BannerInfo bannerInfo, String username);
    
    /**
     * 修改广告状态
     * @param id 广告ID
     * @param status 状态：0 停用，1 启用
     * @return
     */
    public String changeBannerStatus(String id, String status, String username);
    
    /**
     * 查询广告列表
     * @param position 显示位置：1 轮播 2 悬浮
     * @return
     */
    public List<BannerInfo> queryBannerList(String position);

}
