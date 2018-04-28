package com.hui10.h5.service.user.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.service.user.UserInfoService;
import com.hui10.common.cache.CacheManager;
import com.hui10.common.utils.HttpUtil;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.exception.CommonException;
import com.hui10.h5.service.user.UserInfoH5Service;

/**
 * @ClassName: UserInfoServiceImpl.java
 * @Description:
 * @author zhangll
 * @date 2018年1月22日 下午2:19:57
 */
@Service
public class UserInfoH5ServiceImpl implements UserInfoH5Service {
	private static final Logger logger = LoggerFactory.getLogger(UserInfoH5ServiceImpl.class);
	private static final String appId = "4a35fa33d48a4b21921c807f7dcc4ef4";//TODO
	private static final String grantType = "upapi_userinfo";//TODO
	private static final String host = PropertiesUtils.get("unionpay.open.host");
	private static final String appname= PropertiesUtils.get("unionpay.open.app");
	private static final String version = PropertiesUtils.get("unionpay.open.version");
	private static final String backendToken_url = "backendToken";
	private static final String accessToken_url = "token";
	private static final String userInfo_url = "oauth.userInfo";
	private static char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z'};
	private static final String secret = "9fb7762ef09e4c499fc85051bfcf2a83";
	private static final String  h5_user_unionpaycode="h5:user:code:";
	private static final String backend = "backend";
	private static final String access = "access";
	private static final String openid = "openId";
	private static final String unionpay_flat = "11";
	@Autowired
    private CacheManager cacheManager;
	@Autowired
	private UserInfoService userInfoService;

	@Override
	public JSONObject backendToken() {
		JSONObject formParams = new JSONObject();
		String nonceStr = createNonceStr(16);
		Long timestamp = (new Date()).getTime() / 1000;//单位秒
		StringBuilder buffer = new StringBuilder();
		buffer.append("appId="+appId+"&nonceStr="+nonceStr+"&secret="+secret+"&timestamp="+timestamp);
		String signature = getStrSHA256(buffer.toString());
		formParams.put("appId", appId);
		formParams.put("nonceStr", nonceStr);
		formParams.put("timestamp", timestamp);
		formParams.put("signature", signature);
	    String responseMessaage = 	post(String.format(host, appname,version,backendToken_url), formParams.toJSONString(), null);
	    return dealResponseMessage(responseMessaage);
	}
	

	@Override
	public JSONObject accessToken(String backendToken, String code) {
		JSONObject parameters = new JSONObject();
		String result = null;
		parameters.put("appId", appId);
		parameters.put("backendToken", backendToken);
		parameters.put("code", code);
		parameters.put("grantType", grantType);
		result = post(String.format(host, appname,version,accessToken_url), parameters.toJSONString(), null);
		return dealResponseMessage(result);
	}

	@Override
	public JSONObject userMobile(String accessToken, String openId, String backendToken) {
		JSONObject parameters = new JSONObject();
		String result = null;
		parameters.put("appId", appId);
		parameters.put("accessToken", accessToken);
		parameters.put("openId", openId);
		parameters.put("backendToken", backendToken);
		result = post(String.format(host, appname,version,userInfo_url), parameters.toJSONString(), null);
		return dealResponseMessage(result);
	}

	@Override
	public String login(String code, Map<String, String> params) {
		if (StringUtils.isBlank(code)) {
			throw new CommonException(ICommon.PARAMETER_IS_ERROR, PropertiesUtils.get(ICommon.PARAMETER_IS_ERROR, "code"));
		}

		String token = null;

		String backendToken = null;
		JSONObject backendTokenObject = new JSONObject();

		String accessToken = null;
		JSONObject accessTokenObject = new JSONObject();
		String openId = null;
		@SuppressWarnings("unused")
		String scope = null;

		String phone = null;
		JSONObject phoneObject = new JSONObject();

		if (cacheManager.exist(h5_user_unionpaycode + code + backend)) {
			// TODO
			backendToken = (String) cacheManager.get(h5_user_unionpaycode + code + backend);
		} else {
			backendTokenObject = backendToken();
			backendToken = backendTokenObject.getString("backendToken");
			Long bacTokenexpiresIn = backendTokenObject.getLong("expiresIn");
			cacheManager.put(h5_user_unionpaycode + code + backend, backendToken, bacTokenexpiresIn.intValue() - 20);
		}
		// TODO accessToken,openId
		if (cacheManager.exist(h5_user_unionpaycode + code + access)) {
			accessToken = (String) cacheManager.get(h5_user_unionpaycode + code + access);
		} else {
			accessTokenObject = accessToken(backendToken, code);
			accessToken = accessTokenObject.getString("accessToken");
			Long accTokenexpiresIn = accessTokenObject.getLong("expiresIn");
			openId = accessTokenObject.getString("openId");
			scope = accessTokenObject.getString("scope");

			cacheManager.put(h5_user_unionpaycode + code + access, accessToken, accTokenexpiresIn.intValue() - 20);
			cacheManager.put(h5_user_unionpaycode + code + openid, accessToken, accTokenexpiresIn.intValue() - 20);

		}

		// TODO phone
		phoneObject = userMobile(accessToken, openId, backendToken);
		if (!StringUtils.isBlank(phoneObject.getString("mobile"))) {
			phone = phoneObject.getString("mobile");
			JSONObject result = userInfoService.registerAndLogin(phone, params,unionpay_flat);
			token = result.getString("token");

		} else {
			// TODO 是否抛个异常
		}

		return token;
	}
	
	private String createNonceStr(Integer length){
		Random random = new Random();
		StringBuilder stringBuffer = new StringBuilder();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(String.valueOf(codeSequence[random.nextInt(codeSequence.length - 1)]));
		}
		return stringBuffer.toString();
	}
	
	/***
     *  利用Apache的工具类实现SHA-256加密
     * @param str 加密后的报文
     * @return
     */
    private String getStrSHA256(String str){
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }
    
	private String post(String url, String parameters, String authorization) {
		HttpUtil httpUtil = HttpUtil.getInstance();
		try {
			return httpUtil.postBody(url, parameters, authorization);
		} catch (Exception e) {
			logger.error("请求银联钱包错误", e);
		}
		return null;
	}
	
	private JSONObject dealResponseMessage(String messageinfo) {
		JSONObject result = new JSONObject();
		JSONObject resultObject = JSONObject.parseObject(messageinfo);
		if ("00".equals(resultObject.getString("resp"))) {
			result = resultObject.getJSONObject("params");
		} else {
			// TODO
			throw new CommonException(ICommon.USER_UNIONPAY_WALLET_ERROR,
					PropertiesUtils.get(ICommon.USER_UNIONPAY_WALLET_ERROR) + resultObject.getString("msg"));
		}

		return result;
	}
	
	
	
	
	}  
	

