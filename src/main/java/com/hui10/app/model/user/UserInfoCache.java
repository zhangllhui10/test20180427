package com.hui10.app.model.user;

import java.io.Serializable;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2018年03月17日 15:32
 */
public class UserInfoCache extends UserInfo implements Serializable{

    private static final long serialVersionUID = -6025776478969077589L;

    /**
     * 最近使用过的银行卡信息
     */
    private UserBankCard userBankCard;

    public UserBankCard getUserBankCard() {
        return userBankCard;
    }

    public void setUserBankCard(UserBankCard userBankCard) {
        this.userBankCard = userBankCard;
    }

}
