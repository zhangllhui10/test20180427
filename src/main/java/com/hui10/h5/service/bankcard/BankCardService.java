package com.hui10.h5.service.bankcard;

import com.hui10.app.model.user.UserBankCard;

import java.util.List;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2018年01月19日 15:28
 */
public interface BankCardService {

    /**
     * 查询银行卡
     * @param uid
     * @return
     */
    List<UserBankCard> queryBankCards(String uid);

    /**
     * 绑定银行卡
     * @param userBankcard
     * @return
     */
    boolean bindBankcard(UserBankCard userBankcard);

    /**
     * 解绑银行卡
     * @param uid
     * @param id
     * @return
     */
    boolean unBindBankcard(String uid, String id);
}
