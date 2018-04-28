package com.hui10.app.system;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hui10.app.TestBase;
import com.hui10.app.model.enums.CityTypeEnum;
import com.hui10.app.service.system.CityService;

/**
 * @ClassName: CityServiceTest.java
 * @Description:
 * @author zhangll
 * @date 2018年2月27日 上午11:36:25
 */
public class CityServiceTest extends TestBase {
	@Autowired
	private CityService cityService;
	@Test
	public void getCityByIds_test1(){
		CityTypeEnum type = CityTypeEnum.CITY;
		List<String> areaid = new ArrayList<String>();
		areaid.add("1301");
		areaid.add("13112");
		cityService.getCityByIds(areaid, type);
		
	}

}
