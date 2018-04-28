package com.hui10.app.service.profit.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.lottery.Md5Util;
import com.hui10.app.dao.frofit.LotteryProfitDao;
import com.hui10.app.model.profit.LotteryProfitDetailProp;
import com.hui10.app.model.profit.LotteryProfitProp;
import com.hui10.app.service.profit.LotteryProfitService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.exception.CommonException;
import com.pay.platform.trunk.pay.sdk.MD5SignAndValidate;

/**
 * @ClassName: LotteryProfitServiceImpl.java
 * @Description:
 * @author zhangll
 * @date 2017年10月22日 下午12:39:01
 */
@Service
public class LotteryProfitServiceImpl implements LotteryProfitService {
	
	@Autowired
	private LotteryProfitDao lotteryProfitDao;
	
	private static final Logger logger = LoggerFactory.getLogger(LotteryProfitServiceImpl.class);

	@Override
	public Map<String, String> queryProfitRate(String body) {
		logger.debug("******支付平台的请求报文" + body);
		String resultStr = null;
		Map<String, String> sourceMap = new HashMap<String, String>();
		JSONObject jsonObject = JSON.parseObject(body);
		for (String key : jsonObject.keySet()) {
			sourceMap.put(key, jsonObject.getString(key));
		}
		if (!MD5SignAndValidate.validateData(sourceMap, Constants.KEY)) {
			throw new CommonException(1000000001, "验签失败");
		}
		String request_content = jsonObject.getString("request_content");
		jsonObject.getString("salt");
		jsonObject.getString("signature");
		Md5Util.encode(request_content, "");
		JSONObject contentObject = JSON.parseObject(request_content);
		String provinceid = contentObject.getString("stationprovince");
		String lotterycode = contentObject.getString("gamecode");
		// 彩票总的分润利率
		List<LotteryProfitProp> profitPropList = lotteryProfitDao.queryProfitProp(lotterycode, provinceid);
		List<LotteryProfitDetailProp> profitDetailList = lotteryProfitDao.queryProfitDetailProp(lotterycode, provinceid);
		JSONArray resultArry = new JSONArray();
		for (LotteryProfitProp prop : profitPropList) {
			JSONObject result = new JSONObject();
			Float totle = prop.getTotalprop();
			result.put("station_provice_code", prop.getProvinceid());
			result.put("total_profit_rate", totle);
			result.put("lottery_type", prop.getLotterycode());
			Map<String, JSONObject> ucMap = new HashMap<String, JSONObject>();
			JSONArray hmArray = new JSONArray();
			logger.info(prop.getProvinceid() + " " + prop.getLotterycode() + " 总的分润比例是：" + totle);
			for (LotteryProfitDetailProp detailProp : profitDetailList) {
				if (prop.getProvinceid().equals(detailProp.getProvinceid()) && prop.getLotterycode().equals(detailProp.getLotterycode())) {
					JSONObject ucObject = new JSONObject();
					JSONObject hmObject = new JSONObject();
					Float unionPay = detailProp.getUnionpayprop();
					Float acq = detailProp.getAcquirerprop();
					Float merc = detailProp.getMerchantprop();
					ucObject.put("up_rate", unionPay);
					ucObject.put("chl_code", detailProp.getAcquirerno());
					ucObject.put("chl_rate", acq);
					ucObject.put("chl_fee_rate", PropertiesUtils.get("chl_fee_rate"));
					ucMap.put(detailProp.getAcquirerno(), ucObject);
					hmObject.put("hui10_rate", (float) (Math.round((totle - unionPay - acq - merc) * 10000)) / 10000);
					hmObject.put("merc_rate", merc);
					hmObject.put("merc_code", detailProp.getMerchantno());
					hmArray.add(hmObject);
					logger.info("渠道编号:" + detailProp.getAcquirerno() + " ,商户编号：" + detailProp.getMerchantno() + " ,银联分润比例:" + unionPay + " ,渠道分润比例:" + acq
							+ " ,商户分润比例:" + merc);
				}

			}
			JSONArray ucArray = dealRepeatChValue(ucMap);
			result.put("uc_profit_rate_details", ucArray);
			result.put("hm_profit_rate_details", hmArray);
			resultArry.add(result);
		}

		resultStr = resultArry.toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = sdf.format(new Date());
		Map<String, String> map = new HashMap<String, String>();
		map.put("salt", MD5SignAndValidate.getRandomString(32));
		map.put("req_time", time);
		map.put("signature", "");
		map.put("all_rate_data", resultStr);
		logger.info("****all_rate_data****: " + resultStr);
		return MD5SignAndValidate.signData(map, Constants.KEY);
	}
	
	/**
	 * 处理重复的收单机构信息
	 * @param ucMap
	 * @return
	 * @user zhangll
	 * @date 2018年4月12日 下午3:00:39
	 */
	private JSONArray dealRepeatChValue(Map<String,JSONObject> ucMap) {
		JSONArray ucArray = new JSONArray();
		for (Entry<String, JSONObject> chl : ucMap.entrySet()) {
			ucArray.add(chl.getValue());
		}
		 return ucArray;
	}

}
