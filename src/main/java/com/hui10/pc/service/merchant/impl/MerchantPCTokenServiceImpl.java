package com.hui10.pc.service.merchant.impl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.model.merchant.MerchantInfo;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.exception.CommonException;
import com.hui10.pc.service.merchant.MerchantPCTokenService;
import com.poslot.common.utils.AesUtils;
import com.poslot.common.utils.DateUtils;

/**
 * @ClassName: MerchantPCTokenServiceImpl.java
 * @Description:
 * @author zhangll
 * @date 2018年4月18日 下午1:19:43
 */
@Service
public class MerchantPCTokenServiceImpl implements MerchantPCTokenService {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public String setToken(String phone,List<MerchantInfo> list) {
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		if(null != valueOperations.get(phone)){
			deleteToken(phone);
		}
		String token = generateToken(phone);
		JSONArray array = new JSONArray();
		array.addAll(list);
		valueOperations.set(phone, token);
		valueOperations.set(token, array);
		redisTemplate.expire(phone, 7 * 24 * 60 * 60, TimeUnit.SECONDS);
		redisTemplate.expire(token, 7 * 24 * 60 * 60, TimeUnit.SECONDS);
		return token;
	}

	private  String generateToken(String phone) {
		String token = null;
		StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(phone).append("-");
        stringBuilder.append(DateUtils.getCurrentTimestamp()).append("-");
        stringBuilder.append(new Random().nextInt(10000000)).append("-");
        stringBuilder.append(new Random().nextInt(10000000));
        token = AesUtils.encrypt(stringBuilder.toString());
		return token;
	}
	@Override
	public List<MerchantInfo> validateUserAppToken(String token) {
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		JSONArray array = (JSONArray)valueOperations.get(token);
		if(null == array){
			throw new CommonException(ICommon.VALIDATE_TOKEN_FAIL, PropertiesUtils.get(ICommon.VALIDATE_TOKEN_FAIL));
		}
		List<MerchantInfo> list = JSONArray.parseArray(array.toJSONString(), MerchantInfo.class);
		if(null == list || list.isEmpty()){
			throw new CommonException(ICommon.VALIDATE_TOKEN_FAIL, PropertiesUtils.get(ICommon.VALIDATE_TOKEN_FAIL));
		}
		return list;
	}

	@Override
	public void deleteToken(String phone) {
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		if (null != valueOperations.get(phone)) {
			String token = (String) valueOperations.get(phone);
			if (null != valueOperations.get(token)) {
				redisTemplate.delete(token);
			}
		}
	}
	
	

}
