package com.hui10.app.controller.order;

import com.alibaba.fastjson.JSON;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.lottery.Md5Util;
import com.hui10.common.utils.DateUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-19 13:47
 **/
@RestController
public class LoteryNotifyTestConotroller {


    @RequestMapping("api/v1/lotteryManager/queryOnSaleIssue")
    public Map<String, Object> queryOnSaleIssue() {
        Map<String, Object> result = new HashMap<>();
        result.put("result", 0);
        result.put("resultDesc", "成功");
        result.put("signature", "null");
        ArrayList<Map<String, Object>> array = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("issueNumber", "2017154");
        map1.put("gameCode", "10001");
        map1.put("lotterystarttime", System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        map1.put("lotteryendtime", calendar.getTime().getTime());
        array.add(map1);

        Map<String, Object> mapR = new HashMap<>();
        mapR.put("lottery",array);
        result.put("transData", JSON.toJSONString(mapR));
        String signature = Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(result), Constants.LOTTERY_MD5_SUFFIX);
        result.put("signature", signature);
        return result;
    }

    @RequestMapping("api/v1/lotteryManager/channelBet")
    public Map<String, Object> channelBet() {
        Map<String, Object> result = new HashMap<>();
        result.put("result", 0);
        result.put("resultDesc", "成功");
        result.put("signature", "null");

        Map<String, Object> transData = new HashMap<>();
        transData.put("orderNo", UUIDUtil.getUUID());
        transData.put("orderAmount", 100000);
        transData.put("orderTime", System.currentTimeMillis());
        transData.put("orderStatus", 1);
        result.put("transData", JSON.toJSONString(transData));
        String signature = Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(result), Constants.LOTTERY_MD5_SUFFIX);
        result.put("signature", signature);
        return result;

    }


}
