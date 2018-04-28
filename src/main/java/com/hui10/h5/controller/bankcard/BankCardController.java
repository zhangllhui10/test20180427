package com.hui10.h5.controller.bankcard;

import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.user.UserBankCard;
import com.hui10.common.constants.ICommon;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.h5.service.bankcard.BankCardService;
import com.hui10.model.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2018年01月19日 14:15
 */
@Controller
public class BankCardController extends BaseController{

    @Autowired
    private BankCardService bankCardService;

    /**
     * 查询银行卡列表
     * @param token
     * @return
     */
    @RequestMapping(value = "/v*/h5/card/bankcards", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<UserBankCard>> queryBindBankcards(@RequestParam String token) {
        String uid = this.checkToken(token);
        Result<List<UserBankCard>> result = new Result<List<UserBankCard>>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
        result.setResult(bankCardService.queryBankCards(uid));
        return result;

    }

    /**
     * 绑卡
     * @param token
     * @param userBankCard
     * @return
     */
    @RequestMapping(value = "/v*/h5/card/bindcard", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public Result<String> bindBankcard(@RequestParam String token, UserBankCard userBankCard) {
        String uid = this.checkToken(token);
        userBankCard.setUid(uid);
        Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
        result.setSuccess(bankCardService.bindBankcard(userBankCard));
        result.setResult(userBankCard.getId());
        return result;

    }

    /**
     * 解绑
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "/v*/h5/card/unbind", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> unBindBankcard(@RequestParam String token, @RequestParam String id) {
        String uid = this.checkToken(token);
        Result<Boolean> result = new Result<Boolean>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
        result.setResult(bankCardService.unBindBankcard(uid, id));
        return result;

    }
}
