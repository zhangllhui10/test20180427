package com.hui10.app.dao.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.enums.CityTypeEnum;
import com.hui10.app.model.system.City;

/**
 * @ClassName: CityDao.java
 * @Description:
 * @author fanzy
 * @date 2017年4月5日 下午3:34:53
 */
public interface CityDao {

	/**
	 * 查询系统城市信息
	 * @param areaid
	 * @param type
	 * @return
	 * @user hui10
	 * @date 2017年4月21日 下午4:13:15
	 */
	public City getCityById(@Param("areaid") String areaid, @Param("type") String type);

	/**
	 * 查询系统城市子信息 （前端导出用）
	 * @param areaid
	 * @param type
	 * @return
	 * @user hui10
	 * @date 2017年4月21日 下午4:13:11
	 */
	public List<City> getSubCityList(@Param("areaid") String areaid, @Param("type") CityTypeEnum type);
	
	/**
	 * 查询系统城市信息
	 * @param areaid
	 * @param type
	 * @return
	 * @user hui10
	 * @date 2017年4月21日 下午4:13:15
	 */
	public List<City> getCityByIds(@Param("list") List<String> areaid, @Param("type") String type);

}
