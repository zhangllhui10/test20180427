/**
 * @Title: SendMsgUtils.java
 * @Package com.hui10.user.common.util.message
 * @Description: 
 * Copyright: Copyright (c) 2016 
 * Company:汇拾
 * 
 * @author 汇拾-zhangll
 * @date 2016年8月5日 下午1:10:53
 * @version V1.0
 */
package com.hui10.app.common.utils;


import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.common.cache.RedisSingleUtil;
import com.hui10.common.constants.ICommon;
import com.hui10.common.pubservice.PubserviceUtil;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.enrypt.AesUtil;
import com.hui10.enums.common.ProductTypeEnum;
import com.hui10.exception.CommonException;

/**
 * @ClassName: SendMsgUtils
 * @Description:
 * @author zhangll
 * @date 2016年8月5日 下午1:10:53
 *
 */
public class SendMsgUtils {
	/**
	 * 模板KEY值
	 */
	private static String KEY = "code";
	
	private static String NUM = "minute";
	
	private static String PLATFORM = "platform";
	
	private static String HUI_CAI_BAO ="汇彩宝";

	public static String  SMSCODE =  PropertiesUtils.get("smscode.powerful");

	/**
	 * 
	 * sendSMS @Title: sendSMS @Description: 发送验证码 @param @param phone
	 * 手机号 @param @param smstpl 模板标识 @param @param seesionID
	 * sessionID标识 @param @param request @param @return 设定文件 @return boolean
	 * 返回类型 @throws
	 */
	public static boolean sendSMS(int num, String phone, String smstpl, String key) {
		RedisSingleUtil instance = RedisSingleUtil.getInstance();
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null != instance.get(key + PropertiesUtils.get("sms.key"))) {
			String sms_time = instance.get(key + PropertiesUtils.get("sms.key") + "time");
			if (StringUtils.isNotBlank(sms_time)) {
				Long redis_time = null;
				try {
					redis_time = sdf.parse(sms_time).getTime();
				} catch (ParseException e) {
				}
				Long surplus_time = Long.parseLong(PropertiesUtils.get("sms.code.sendtime")) * 60 * 1000 - (new Date().getTime() - redis_time);
				throw new CommonException(ICommon.SMS_SEND_LATER,
						PropertiesUtils.get(ICommon.SMS_SEND_LATER, surplus_time = surplus_time > 0 ? surplus_time / 1000 : 1));
			}
		}

		// 生成num位验证码
		String sms_code = getRandomNum(num);
		params.put(KEY, sms_code);
		params.put(NUM, PropertiesUtils.get("sms.code.verifytime"));
		params.put(PLATFORM, HUI_CAI_BAO);
		if (send(phone, smstpl, params)) {
			instance.setex(key + PropertiesUtils.get("sms.key"), Integer.parseInt(PropertiesUtils.get("sms.code.verifytime")) * 60, sms_code);
			instance.setex(key + PropertiesUtils.get("sms.key") + "time", Integer.parseInt(PropertiesUtils.get("sms.code.sendtime")) * 60,
					sdf.format(new Date()));
			return true;
		} else {
			throw new CommonException(ICommon.SMS_CODE_SEND_IS_ERROR, PropertiesUtils.get(ICommon.SMS_CODE_SEND_IS_ERROR));
		}
	}

	/**
	 * 
	 * @param code
	 * @param key
	 * @return
	 * @user 4074
	 * @date 2016年9月20日 下午2:12:57
	 */
	public static boolean checkCode(String code, String key) {
		if(null !=  SMSCODE && "true".equals(SMSCODE) && "111111".equals(code)){
			return true;
		}
		RedisSingleUtil instance = RedisSingleUtil.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long  errornum = 0l;
		if(StringUtils.isBlank(instance.get(key + PropertiesUtils.get("sms.key")))){
			throw new CommonException(ICommon.SMS_CODE_IN_IS_ERROR, PropertiesUtils.get(ICommon.SMS_CODE_IN_IS_ERROR));
		}else{
			String errortime=  instance.get(key + PropertiesUtils.get("sms.key")+"errortime");
			if(StringUtils.isNotBlank(errortime)){
				  errornum =  Long.parseLong(instance.get(key + PropertiesUtils.get("sms.key")+"errornum"));
				if(errornum >= 2){
					Long lerrortime = null;
					Long lerrornum = errornum - 1;
					try {
						lerrortime = sdf.parse(errortime).getTime();
					} catch (ParseException e) {
					}
					if((new Date().getTime() -lerrortime)
			    			<=  lerrornum *5 * 1000){
			    		throw new CommonException(ICommon.SMS_CODE_CHECK_LATER, PropertiesUtils.get(ICommon.SMS_CODE_CHECK_LATER,
			    				lerrornum *5));
			    	}
				} 
			}

			String smsCode = instance.get(key + PropertiesUtils.get("sms.key"));
			if (StringUtils.isBlank(smsCode)) {
				return false;
			}
			if (smsCode.equals(code.trim())) {
				instance.remove(key + PropertiesUtils.get("sms.key"));
				instance.remove(key + PropertiesUtils.get("sms.key") + "errornum");
				instance.remove(key + PropertiesUtils.get("sms.key") + "errortime");
				return true;
			} else {
				// 验证失败
				instance.setex(key + PropertiesUtils.get("sms.key") + "errornum", Integer.parseInt(PropertiesUtils.get("sms.code.verifytime")) * 60,
						Long.toString(++errornum));

				instance.setex(key + PropertiesUtils.get("sms.key") + "errortime", Integer.parseInt(PropertiesUtils.get("sms.code.verifytime")) * 60,
						sdf.format(new Date()));
				throw new CommonException(ICommon.SMS_CODE_IN_IS_ERROR, PropertiesUtils.get(ICommon.SMS_CODE_IN_IS_ERROR));
			}
			
			
		}
	}

	public static boolean send(String phone, String templateId, Map<String, String> params) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", phone);
        jsonObject.put("params", params);
        jsonObject.put("smstmpl", templateId);
        jsonObject.put("platfrom", ProductTypeEnum.CARD.getCode());

        Object paramStr = null;
        try {
            paramStr = AesUtil.encryptString(jsonObject.toJSONString(), Constants.PUBSERVICESECRET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = PropertiesUtils.get("pubservice.host") + PropertiesUtils.get("pubservice.send.sms");
        String responseStr = PubserviceUtil.postRequest(url, paramStr, Constants.PUBSERVICESECRET);

        if (StringUtils.isBlank(responseStr)) {
            throw new CommonException(com.hui10.app.common.constants.ICommon.PARAMETER_ERR,
                    PropertiesUtils.get(com.hui10.app.common.constants.ICommon.PARAMETER_ERR));
        }

        JSONObject resJson = JSONObject.parseObject(responseStr);
        if ("200".equals(resJson.getString("ec")) && resJson.getBoolean("success")) {
            return true;
        } else {
            throw new CommonException(Integer.parseInt(resJson.getString("ec")), resJson.getString("em"));
        }

    }
	/**
	 * getRandomNum(得到一个随机数支持1到10位的)
	 * @Title: getRandomNum @Description: @param @param count @param @return
	 *         设定文件 @return String 返回类型 @throws
	 */
	public static String getRandomNum(int count) {
		StringBuilder sb = new StringBuilder();
		if (count > 0 && count <= 10) {
			String str = "0123456789";
			Random r = new Random();
			for (int i = 0; i < count; i++) {
				int num = r.nextInt(str.length());
				sb.append(str.charAt(num));
				str = str.replace((str.charAt(num) + ""), "");
			}
		}
		return sb.toString();
	}
}
