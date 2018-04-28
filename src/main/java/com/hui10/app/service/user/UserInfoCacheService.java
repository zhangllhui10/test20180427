package com.hui10.app.service.user;

import com.hui10.app.model.user.UserBankCard;
import com.hui10.app.model.user.UserInfoCache;

/**
 * @author fanht
 * @ClassName:
 * @Description: 用户缓存操作
 * @date 2018年03月17日 15:38
 */
public interface UserInfoCacheService {

    /**
     * 获取用户缓存信息
     * @param uid
     * @return
     */
    UserInfoCache getUserInfoCache(String uid);

    /**
     * 更新用户缓存信息
     * @param userInfoCache
     */
    void updateUserInfoCache(UserInfoCache userInfoCache);

    /**
     * 更新用户最近使用银行卡信息
     * @param uid
     * @param userBankCard
     * @param userBankCard ture 更新 false 删除
     */
    void updateUserLastUsedBankCard(String uid, UserBankCard userBankCard, boolean flag);
}
