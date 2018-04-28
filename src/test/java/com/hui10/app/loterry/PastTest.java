package com.hui10.app.loterry;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;

import com.hui10.app.TestBase;
import com.hui10.app.service.loterry.LotteryPastService;

/**
 * @ClassName: PastTest.java
 * @Description:
 * @author wengf
 * @date 2017年10月27日 下午12:15:07
 */
public class PastTest extends TestBase {
	
	
	@Autowired
	private LotteryPastService lotteryPastService;
	
	@Test
	@Commit
	public void test00001_getLottery(){
		int count = lotteryPastService.savePastLottery();
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (count == 0) {
				count = lotteryPastService.savePastLottery();
			}else {
				break;
			}
		}
//		lotteryPastService.savePastLottery();
	}

}
