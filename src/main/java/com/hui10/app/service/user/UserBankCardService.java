package com.hui10.app.service.user;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.model.user.UserBankCard;

/**
 * @author fanht
 * @ClassName: UserBankcardService
 * @Description: 用户银行卡
 * @date 2017年09月05日 16:05
 */
public interface UserBankCardService {

    /**
     * 绑卡发送验证码
     * @param userBankcard
     * @return
     */
    boolean sendCodeForBindBankcard(UserBankCard userBankcard);

    /**
     * 绑定银行卡
     * @param userBankcard
     * @return
     */
    boolean bindBankcard(String code, UserBankCard userBankcard);

    /**
     * 解绑银行卡
     * @param userBankcard
     * @return
     */
    int unBindBankcard(UserBankCard userBankcard);

    /**
     * 查询用户绑卡信息
     * @param uid
     * @return
     */
    JSONObject queryUserBindBankcard(String uid);

    /**
     * 验证用户是否绑卡
     * @param uid
     */
    UserBankCard validateUserBindCard(String uid, String cardid);
    
    public List<UserBankCard> queryBankCards(String uid);
}
