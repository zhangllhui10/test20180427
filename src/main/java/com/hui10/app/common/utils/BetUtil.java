package com.hui10.app.common.utils;

import com.poslot.common.exception.CommonException;
import com.poslot.common.utils.PropertiesUtils;
import com.poslot.enums.lottery.LotteryErrorCode;
import com.poslot.model.lottery.Bet;

import java.util.List;

public class BetUtil {
	public static void checkBetList(List<Bet> betList) {
		if (betList == null || betList.isEmpty()) {
			throw new CommonException(LotteryErrorCode.LOTTERY_BET_EMPTY, PropertiesUtils.get(LotteryErrorCode.LOTTERY_BET_EMPTY));
		}

		String oldLotterycode = null;

		for (Bet bet : betList) {
			String lotterycode = bet.getLotterycode();

			if (oldLotterycode == null) {
				oldLotterycode = lotterycode;
			}

			if (!oldLotterycode.equals(lotterycode)) {
				throw new CommonException(LotteryErrorCode.LOTTERY_DIFF_CODE, PropertiesUtils.get(LotteryErrorCode.LOTTERY_DIFF_CODE));

			}

			if (!bet.validate()) {
				throw new CommonException(LotteryErrorCode.LOTTERY_BETDETAIL_ERROR, PropertiesUtils.get(LotteryErrorCode.LOTTERY_BETDETAIL_ERROR));
			}
		}
	}
	
	public static int getBetNumber(List<Bet> betList)
	{
		int number = 0;
		
		for(Bet bet : betList)
		{
			number = number+ bet.getBetnumber();
		}
		
		return number;
	}

}
