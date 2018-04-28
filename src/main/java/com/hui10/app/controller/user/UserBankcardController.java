package com.hui10.app.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.user.UserBankCard;
import com.hui10.app.service.user.UserBankCardService;
import com.hui10.common.constants.ICommon;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.model.common.Result;

/**
 * @author fanht
 * @ClassName: UserBankcardController
 * @Description: 用户银行卡
 * @date 2017年09月05日 16:00
 */
@Controller
public class UserBankcardController extends BaseController {

    @Autowired
    private UserBankCardService userBankcardService;

    /**
     * 绑卡发送验证码
     * @param token
     * @param userBankcard
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/v*/user/card/sendcode", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public Result<String> sendCodeForBindBankcard(@RequestParam String token, UserBankCard userBankcard) {

        String uid = this.checkToken(token);
        userBankcard.setUid(uid);
        Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
        result.setSuccess(userBankcardService.sendCodeForBindBankcard(userBankcard));
        result.setResult(PropertiesUtils.get(ICommon.SUCCESS));
        return result;

    }


    /**
     * 绑卡
     * @param token
     * @param code
     * @param userBankcard
     * @return
     */
    @RequestMapping(value = "/v*/user/card/bindcard", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public Result<String> bindBankcard(@RequestParam String token,  UserBankCard userBankCard) {
        String uid = this.checkToken(token);
        userBankCard.setUid(uid);
        Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
        result.setSuccess(userBankcardService.bindBankcard(null, userBankCard));
        result.setResult(userBankCard.getId());
        return result;

    }

    /**
     * 解绑
     * @param token
     * @param phone
     * @return
     */
    @RequestMapping(value = "/v*/user/card/unbind", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public Result<String> unBindBankcard(@RequestParam String token, UserBankCard userBankCard) {

        String uid = this.checkToken(token);
        userBankCard.setUid(uid);
        Result<String> result = new Result<String>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
        result.setResult(userBankcardService.unBindBankcard(userBankCard)+"");
        return result;

    }
    
    /**
     * 
     * 查询我的银行卡
     * 
     */
    @RequestMapping(value = "/v*/user/card/bankcards", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<UserBankCard>> queryBindBankcards(@RequestParam String token) {
        String uid = this.checkToken(token);
        Result<List<UserBankCard>> result = new Result<List<UserBankCard>>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
        result.setResult(userBankcardService.queryBankCards(uid));
        return result;

    }


}
