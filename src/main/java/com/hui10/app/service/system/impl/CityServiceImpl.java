package com.hui10.app.service.system.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.dao.system.CityDao;
import com.hui10.app.model.enums.CityTypeEnum;
import com.hui10.app.model.system.City;
import com.hui10.app.service.system.CityService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.exception.CommonException;

/**
 * 城市信息实现类
 * 
 * @author fanzy
 *
 */
@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDao cityDao;
	
	@Override
	public City getCityById(String areaid, CityTypeEnum type) {
		City city = cityDao.getCityById(areaid, type.getState());
		if (city == null) {
			throw new CommonException(ICommon.SYS_AREA_IS_NOT_FOUND, PropertiesUtils.get(ICommon.SYS_AREA_IS_NOT_FOUND));
		}
		return city;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getCityJsonMap() {
		ArrayList ret1 = new ArrayList<>();
		List<City> provinces = cityDao.getSubCityList(null, CityTypeEnum.PROVINCE);
		for (City province:provinces ) {
			Map a = new LinkedHashMap();
			a.put("value", province.getProvinceid());
			a.put("label", province.getProvincename());
			List<City> cities = cityDao.getSubCityList(province.getProvinceid(), CityTypeEnum.CITY);
			
			ArrayList ret2 = new ArrayList<>();
			for (City city:cities) {
				Map b = new LinkedHashMap();
				b.put("value", city.getProvinceid());
				b.put("label", city.getProvincename());
				List<City> districts = cityDao.getSubCityList(city.getProvinceid(), CityTypeEnum.DISTRICT);
				b.put("children", districts);
				ret2.add(b);
			}
			if (!ret2.isEmpty()) {
				
			}
			a.put("children", ret2);
			ret1.add(a);
		}
		return JSONObject.toJSONString(ret1);
	}

	@Override
	public List<City> getCityByIds(List<String> areaid, CityTypeEnum type) {
		List<City> citys = cityDao.getCityByIds(areaid, type.getState());
		if(null == citys || citys.size() !=  areaid.size()){
			throw new CommonException(ICommon.SYS_AREA_IS_NOT_FOUND, PropertiesUtils.get(ICommon.SYS_AREA_IS_NOT_FOUND));
		}
		return citys;
	}


}
