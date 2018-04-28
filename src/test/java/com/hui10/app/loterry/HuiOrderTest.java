package com.hui10.app.loterry;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hui10.app.TestBase;
import com.hui10.app.model.order.OrderAccountBalanceDetailVo;
import com.hui10.app.model.order.OrderAccountBalanceInfoVo;
import com.hui10.app.service.order.HuiOrderBalanceService;

public class HuiOrderTest extends TestBase{

	@Autowired
	private HuiOrderBalanceService huiOrderBalanceService;
	
	
	@Test
	public void test() {
		huiOrderBalanceService.accountBalance();
	}
	
	@Test
	public void test1() {
		OrderAccountBalanceInfoVo result = huiOrderBalanceService.queryAccountBalanceInfo("1", "20180322", "20180322", 1, 20);
		System.out.println(result);
	}
	
	@Test
	public void test2() {
		OrderAccountBalanceDetailVo result = huiOrderBalanceService.queryAccountBalanceDetails("201803240753353839390002573891", 1, 20);
		System.out.println(result);
	}
}
