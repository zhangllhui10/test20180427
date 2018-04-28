package com.hui10.app.model.profit;
/**
 * @ClassName: Test.java
 * @Description:
 * @author zhangll
 * @date 2017年10月27日 下午2:59:37
 */
public class Test {
	
	
	public static void main(String[] args) {
		Float acquirerprop = 0.014f;
		Float unionpayprop = 0.021f;
		Float merchantprop = 0.0175f;
		Float total = 0.07f;
		Float hui = new Float(total - acquirerprop - unionpayprop - merchantprop);
		System.out.println(hui);
		Float huiprop = (float)(Math.round(hui  * 10000))/10000;
		System.out.println(huiprop);
		
		
		
	}

}
