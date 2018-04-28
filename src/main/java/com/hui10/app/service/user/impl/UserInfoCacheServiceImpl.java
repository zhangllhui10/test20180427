package com.hui10.app.service.user.impl;

import com.hui10.app.model.user.UserBankCard;
import com.hui10.app.model.user.UserInfoCache;
import com.hui10.app.service.user.UserInfoCacheService;
import com.hui10.common.cache.CacheManager;
import com.hui10.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2018年03月17日 15:41
 */
@Service
public class UserInfoCacheServiceImpl implements UserInfoCacheService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public UserInfoCache getUserInfoCache(String uid) {
        UserInfoCache userInfoCache = null;
        if (cacheManager.exist(uid)) {
            userInfoCache = (UserInfoCache) cacheManager.get(uid);
        }
        return userInfoCache;
    }

    @Override
    public void updateUserInfoCache(UserInfoCache userInfoCache) {
        if (null != userInfoCache && StringUtils.isNotBlank(userInfoCache.getUid())) {
            cacheManager.put(userInfoCache.getUid(), userInfoCache, 0);
        }
    }

    @Override
    public void updateUserLastUsedBankCard(String uid, UserBankCard userBankCard, boolean flag) {
        UserInfoCache userInfoCache = this.getUserInfoCache(uid);
        userInfoCache = null != userInfoCache ? userInfoCache : new UserInfoCache();
        userInfoCache.setUid(uid);
        if (flag) {
            userInfoCache.setUserBankCard(userBankCard);
            this.updateUserInfoCache(userInfoCache);
        } else {
            UserBankCard cacheCard = userInfoCache.getUserBankCard();
            if (null != cacheCard && userBankCard.getId().equals(cacheCard.getId())) {
                userInfoCache.setUserBankCard(null);
                this.updateUserInfoCache(userInfoCache);
            }
        }
    }
}
