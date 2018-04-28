package com.hui10.app.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.constants.SmsConstants;
import com.hui10.app.common.pay.CMBCUtil;
import com.hui10.app.common.utils.SendMsgUtils;
import com.hui10.app.common.utils.StringFormat;
import com.hui10.app.common.utils.ValidatorUtils;
import com.hui10.app.dao.user.UserBankCardDao;
import com.hui10.app.model.user.UserBankCard;
import com.hui10.app.service.user.UserBankCardService;
import com.hui10.app.service.user.UserInfoCacheService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author fanht
 * @ClassName: UserBankcardServiceImpl
 * @Description: 用户银行卡
 * @date 2017年09月05日 16:29
 */
@Service
public class UserBankCardServiceImpl implements UserBankCardService {

    @Autowired
    private UserInfoCacheService userInfoCacheService;

    @Autowired
    private UserBankCardDao userBankcardDao;
    private String boundBankcard = "boundbankcard";

    @Override
    public boolean sendCodeForBindBankcard(UserBankCard userBankcard) {

        ValidatorUtils.checkBean(userBankcard, com.hui10.common.constants.ICommon.PARAM_ERROR);
        checkPhoneIsBound(userBankcard);
        CMBCUtil.doesItExist(userBankcard.getCardname());
        String userPhone = userBankcard.getReservephone();
        int codeNum = Integer.parseInt(PropertiesUtils.get("sms.code.number"));
        if (!SendMsgUtils.sendSMS(codeNum, userPhone, SmsConstants.COMMON_CODE, userPhone + boundBankcard)) {
            throw new CommonException(ICommon.SEND_VALIDATE_CODE_FAIL, PropertiesUtils.get(ICommon.SEND_VALIDATE_CODE_FAIL));
        }

        return true;
    }


    private void checkPhoneIsBound(UserBankCard userBankcard) {
        UserBankCard bankcard = new UserBankCard();
        bankcard.setUid(null);
        bankcard.setCardno(userBankcard.getCardno());
        UserBankCard userBankcardInfo = userBankcardDao.queryUserBankcard(bankcard);
        if (null != userBankcardInfo) {
            throw new CommonException(ICommon.BANKCARD_BOUND, PropertiesUtils.get(ICommon.BANKCARD_BOUND));
        }


    }


    @Override
    public boolean bindBankcard(String code, UserBankCard userBankcard) {

        ValidatorUtils.checkBean(userBankcard, com.hui10.common.constants.ICommon.PARAM_ERROR);
        checkPhoneIsBound(userBankcard);

            userBankcard.setId(UUIDUtil.getUUID());
            userBankcard.setCreatetime(new Date());
            int result = userBankcardDao.insertBankcardInfo(userBankcard);

            if (result <= 0) {
                throw new CommonException(ICommon.BOUND_BANKCARD_FAIL, PropertiesUtils.get(ICommon.BOUND_BANKCARD_FAIL));
            }

            // 更新用户最近用卡信息
            userInfoCacheService.updateUserLastUsedBankCard(userBankcard.getUid(), userBankcard, true);

            return true;

    }


    @Override
    public int unBindBankcard(UserBankCard userBankcard) {

        UserBankCard bankcard = userBankcardDao.queryUserBankcard(userBankcard);
        if (null == bankcard) {
            throw new CommonException(ICommon.NOBOUND_BANKCARD, PropertiesUtils.get(ICommon.NOBOUND_BANKCARD));
        }
        int result = userBankcardDao.deleteBankcardInfo(userBankcard.getId());

        // 更新用户最近用卡信息
        userInfoCacheService.updateUserLastUsedBankCard(userBankcard.getUid(), bankcard, false);

        return result;
    }


    @Override
    public JSONObject queryUserBindBankcard(String uid) {

    	UserBankCard userBankcard = new UserBankCard();
        userBankcard.setUid(uid);
        UserBankCard userBankcardInfo = userBankcardDao.queryUserBankcard(userBankcard);
        JSONObject jsonObject = new JSONObject();
        if (null != userBankcardInfo) {

            int cardLength = userBankcardInfo.getCardno().length();
            String regex = "0|1|2|3|4|5|6|7|8|9";
            String prefix = userBankcardInfo.getCardno().substring(0, cardLength - 4).replaceAll(regex, "*");
            String mantissa = userBankcardInfo.getCardno().substring(cardLength - 4, cardLength);
            jsonObject.put("cardname", userBankcardInfo.getCardname());
            jsonObject.put("cardno", prefix + mantissa);

        }

        return jsonObject;

    }

    @Override
    public UserBankCard validateUserBindCard(String uid, String cardid) {
        UserBankCard userBankcard = new UserBankCard();
        userBankcard.setUid(uid);
        userBankcard.setId(cardid);
        userBankcard = userBankcardDao.queryUserBankcard(userBankcard);
        if (null == userBankcard) {
            throw new CommonException(ICommon.NOBOUND_BANKCARD, PropertiesUtils.get(ICommon.NOBOUND_BANKCARD));
        }
        return userBankcard;
    }


	@Override
	public List<UserBankCard> queryBankCards(String uid) {
		List<UserBankCard> bankCards = userBankcardDao.queryBankCards(uid);
		for (UserBankCard userBankCard : bankCards) {
		    userBankCard.setCardno(StringFormat.formatCardNumber(userBankCard.getCardno()));
        }
		return bankCards;
	}

}

