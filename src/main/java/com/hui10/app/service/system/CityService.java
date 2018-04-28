package com.hui10.app.service.system;

import java.util.List;

import com.hui10.app.model.enums.CityTypeEnum;
import com.hui10.app.model.system.City;

/**
 * Created by fanzy on 2017/4/6.
 */
public interface CityService {

	/**
	 * 查询系统城市信息
	 * @param areaid
	 * @param type
	 * @return
	 * @user hui10
	 * @date 2017年4月21日 下午4:20:27
	 */
	City getCityById(String areaid, CityTypeEnum type);

	/**
	 * 查询系统城市子信息 （前端导出用）
	 * @return
	 * @user hui10
	 * @date 2017年4月21日 下午4:20:27
	 */
	String getCityJsonMap();
	
	List<City> getCityByIds(List<String> areaid, CityTypeEnum type);
}
