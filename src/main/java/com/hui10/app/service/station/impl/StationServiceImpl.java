package com.hui10.app.service.station.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.common.lottery.HttpUtil;
import com.hui10.app.model.enums.StationTypeEnum;
import com.hui10.app.model.merchant.MerchantInfo;
import com.hui10.app.service.merchant.MerchantInfoService;
import com.hui10.app.service.station.StationService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.uuid.UUIDUtil;
import com.hui10.exception.CommonException;

@Service
public class StationServiceImpl implements StationService{
	
	private static final Logger logger = Logger.getLogger(StationServiceImpl.class);

	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Override
	public String distributeStationCode(String merchantno, String provinceid, String cityid) {
		
		MerchantInfo merchantInfo = merchantInfoService.findMerchantInfoByMerchantNo(merchantno);
		
		if (StringUtils.isEmpty(merchantInfo.getStationno())) {
			String transSerialNumber = UUIDUtil.getUUID();
			String url = String.format(PropertiesUtils.get("lottery_url"),"v*/station/stationcode/generate");
			Map<String, Object> paramMap = new HashMap<>();
			
			paramMap.put("provinceid", provinceid);
			paramMap.put("cityid", cityid);
			paramMap.put("type", StationTypeEnum.HUI10.getCode());
			paramMap.put("merchantno", merchantno);
			
			String response = null;
			String stationcode = null;
			
			try {
				response = HttpUtil.getInstance().doPost(transSerialNumber, url, paramMap);
				
				logger.debug("============请求url为"+url+"=============");
				logger.debug("=======================请求参数"+paramMap+"===============");
				JSONObject jsonObject = JSONObject.parseObject(response);
				if (jsonObject.getInteger("ec") == 200 && jsonObject.getBooleanValue("success") == true) {
					stationcode = jsonObject.getString("result");
					merchantInfoService.updateMerchantStationCode(merchantno, stationcode);
					return stationcode;
				}else {
					logger.error("=====================获取宝乐彩投注站编号失败==================");
					throw new CommonException(ICommon.STATION_DISTRIBUTED_FAILED, PropertiesUtils.get(ICommon.STATION_DISTRIBUTED_FAILED));
				}
			}catch (CommonException e) {
				throw new CommonException(e.getCode(), e.getMessage());
			}catch (Exception e) {
				logger.error("=====================获取宝乐彩投注站编号超时=================="+e);
				throw new CommonException(ICommon.STATION_DISTRIBUTED_OVER_TIME, PropertiesUtils.get(ICommon.STATION_DISTRIBUTED_OVER_TIME));
			}
		}else {
			throw new CommonException(ICommon.STATIONCODE_HAS_BEEN_DISTRIBUTED, PropertiesUtils.get(ICommon.STATIONCODE_HAS_BEEN_DISTRIBUTED));
		}
	}

}
