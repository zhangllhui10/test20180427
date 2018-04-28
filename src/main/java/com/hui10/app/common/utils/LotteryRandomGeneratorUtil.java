package com.hui10.app.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.model.lottery.enums.LotteryCodeEnum;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.exception.CommonException;

/**
 * 彩票随机生成器
 * @author chiheng
 *
 */
public class LotteryRandomGeneratorUtil {
	
	public static String generateRandomLottery(String lotterycode) {
		JSONArray betdetail = new JSONArray();
		
		LotteryCodeEnum lotteryCode = LotteryCodeEnum.findByState(lotterycode);
		if(null == lotteryCode) {
			throw new CommonException(ICommon.LOTTERY_CODE_ERROR, PropertiesUtils.get(ICommon.LOTTERY_CODE_ERROR));
		}
		switch (lotteryCode) {
		case UNIONLOTTO:
			betdetail = generateRandomLotto();
			break;

		default:
			break;
		}
		return betdetail.toJSONString();
	}
	
	
	private static JSONArray generateRandomLotto() {
		List<Integer> reds = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		loop: while(true) {
			int number = (int)(Math.random()*33 + 1);
			for (int i = 0; i < reds.size(); i++) {
				if(reds.get(i) == number) {
					continue loop;
				}
			}
			reds.add(number); 
			if(reds.size() == 6) {
				break;
			}
		}
		Collections.sort(reds);
		for (int i = 0; i< reds.size(); i++) {
			if (reds.get(i) < 10) {
				sb.append("0");
			}
			sb.append(reds.get(i));
			if (i != reds.size() - 1) {
				sb.append(",");
			}
		}
		JSONArray jsonArray = new JSONArray();
		JSONObject betdetail = new JSONObject();
		betdetail.put("red", sb.toString());
		int blue = (int)(Math.random()*16 + 1);
		betdetail.put("blue", blue >= 10 ? String.valueOf(blue) : "0"+String.valueOf(blue));
		betdetail.put("lotterycode", LotteryCodeEnum.UNIONLOTTO.getState());
		betdetail.put("bettype", "1000101");
		betdetail.put("multiple", "1");
		jsonArray.add(betdetail);
		return jsonArray;
	}
}
