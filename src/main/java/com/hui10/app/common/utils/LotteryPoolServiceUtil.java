package com.hui10.app.common.utils;

import org.springframework.context.ApplicationContext;

import com.hui10.app.common.constants.ICommon;
import com.hui10.app.model.lottery.enums.LotteryCodeEnum;
import com.hui10.app.service.marketing.LotteryPoolService;
import com.hui10.app.util.ApplicationContextUtil;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.exception.CommonException;

public class LotteryPoolServiceUtil {
	
	
	public static LotteryPoolService getLotteryPoolService(String lotterycode) {
		
		ApplicationContext context = ApplicationContextUtil.getApplicationContext();
		
		if(null == LotteryCodeEnum.findByState(lotterycode)) {
			new CommonException(ICommon.LOTTERY_CODE_ERROR, PropertiesUtils.get(ICommon.LOTTERY_CODE_ERROR));
		}
		switch (LotteryCodeEnum.findByState(lotterycode)) {
		case UNIONLOTTO:
			return (LotteryPoolService) context.getBean("unionLottoPoolService");
			
		case QUICK3:
			return (LotteryPoolService) context.getBean("quick3PoolService");
		default:
			break;
		}
		return null;
	}
}
