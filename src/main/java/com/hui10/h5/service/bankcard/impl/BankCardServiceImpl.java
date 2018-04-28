package com.hui10.h5.service.bankcard.impl;

import com.hui10.app.common.constants.ICommon;
import com.hui10.app.model.user.UserBankCard;
import com.hui10.app.service.user.UserBankCardService;
import com.hui10.common.utils.BankInfoUtil;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.exception.CommonException;
import com.hui10.h5.service.bankcard.BankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2018年01月19日 15:32
 */
@Service
public class BankCardServiceImpl implements BankCardService{

    @Autowired
    private UserBankCardService userBankCardService;

    @Override
    public List<UserBankCard> queryBankCards(String uid) {
        return userBankCardService.queryBankCards(uid);
    }

    @Override
    public boolean bindBankcard(UserBankCard userBankcard) {
        if (StringUtils.isBlank(userBankcard.getCardno())) {
            throw new CommonException(ICommon.BANKCARD_NOT_EMPTY, PropertiesUtils.get(ICommon.BANKCARD_NOT_EMPTY));
        }
        if (StringUtils.isBlank(userBankcard.getCardname())) {
            throw new CommonException(ICommon.BANKNAME_NOT_EMPTY, PropertiesUtils.get(ICommon.BANKNAME_NOT_EMPTY));
        }
        if (StringUtils.isBlank(userBankcard.getCardholder())) {
            throw new CommonException(ICommon.CARDHOLDER_EMPTY, PropertiesUtils.get(ICommon.CARDHOLDER_EMPTY));
        }
        if (!BankInfoUtil.checkBankCard(userBankcard.getCardno())) {
            throw new CommonException(ICommon.BANKCARD_FORMAT_ERROR, PropertiesUtils.get(ICommon.BANKCARD_FORMAT_ERROR));
        }
        return userBankCardService.bindBankcard(null, userBankcard);
    }

    @Override
    public boolean unBindBankcard(String uid, String id) {
        UserBankCard userBankCard = new UserBankCard();
        userBankCard.setUid(uid);
        userBankCard.setId(id);
        return 0 < userBankCardService.unBindBankcard(userBankCard);
    }
}
