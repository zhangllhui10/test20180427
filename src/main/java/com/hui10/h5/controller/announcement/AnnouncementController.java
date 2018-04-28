package com.hui10.h5.controller.announcement;

import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.lottery.LotteryPast;
import com.hui10.app.service.main.MainService;
import com.hui10.common.constants.ICommon;
import com.hui10.common.utils.PropertiesUtils;
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
 * @date 2018年01月19日 11:54
 */
@Controller
public class AnnouncementController extends BaseController{

    @Autowired
    private MainService mainService;

    @RequestMapping(value="/v*/h5/lottery/announcement", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<LotteryPast>> getPast(@RequestParam String token){
        Result<List<LotteryPast>> result = new Result<>(ICommon.SUCCESS, PropertiesUtils.get(ICommon.SUCCESS));
        this.checkToken(token);
        result.setResult(mainService.gePasts());
        return result;
    }
}
