package com.hui10.app.common.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.app.common.constants.ICommon;
import com.hui10.app.model.enums.CurrencyEnums;
import com.hui10.app.model.pay.Charge;
import com.hui10.app.model.pay.PaySDKNotify;
import com.hui10.app.model.pay.UnifiedOrder;
import com.hui10.app.model.user.WithdrawRecord;
import com.hui10.common.utils.HttpUtil;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.common.utils.StringUtils;
import com.hui10.common.utils.ip.IpUtil;
import com.hui10.exception.CommonException;
import com.pay.platform.trunk.pay.sdk.MD5SignAndValidate;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: PaySdk
 * @Description: 聚合支付报文相关
 * @author yangcb
 * @date 2017年4月12日
 *
 */
public class PaySdkUtil {

	private static Logger LOG = Logger.getLogger(PaySdkUtil.class);


	
	/**
	 * 获取sig参数 @Title: getSigParameter @Description: 交易中心验证参数 @param @param
	 * appId @param @param token @param @param times @param @return @return
	 * String @throws
	 */
	public static String getSigParameter(String appId, String token, String time) {
		String md5 = appId + token + time;
		return DigestUtils.md5Hex(md5);
	}

	public static Map<String, String> objectToMap(Object obj) {
		if (obj == null) {
			return null;
		}

		Map<String, String> map = new HashMap<String, String>();
		try {
			Field[] declaredFields = obj.getClass().getDeclaredFields();
			for (Field field : declaredFields) {
				field.setAccessible(true);

				if (field.get(obj) != null) {
					map.put(field.getName(), field.get(obj).toString());
				}
			}
		} catch (Exception e) {
			LOG.error("转化map出错", e);
		}
		return map;
	}

	/**
	 * 
	 * @Title: getAuthorization @Description: 包头验证信息 @param @param
	 *         appId @param @param times @param @return @return String @throws
	 */
	public static String getAuthorization(String appId, String times) {
		String base64 = appId + ":" + times;
		return Base64Utils.encodeToString(base64.getBytes());
	}

	/***
	 * 
	 * @Title: getResponseBody @Description: 获取响应报文 @param @param
	 *         url @param @param parameters @param @param
	 *         authorization @param @return @return String @throws
	 */
	public static String getResponseBody(String url, String parameters, String authorization) {
		HttpUtil httpUtil = HttpUtil.getInstance();

		try {
			return httpUtil.postBody(url, parameters, authorization);
		} catch (IOException | URISyntaxException e) {
			LOG.error("请求聚合支付错误", e);
		}
		return null;
	}

	/**
	 * 
	 * @Title: getQueryOrderInfo @Description: 查询支付状态 @param @param trade_no
	 *         订单编号 @param @return @return Map<String,Object> //
	 *         "{\"pay_cnl\":\"WX\",\"trade_amt\":0.01,\"currency\":\"CNY\",\"outtradesn
	 *         \":\"124201704101458510000013251200\",\"orderid\":\"1490858649141\"
	 *         ,\"trade_sts\":\"WAITPAY\"}", @throws
	 */
	public static Map<String, String> getQueryOrderInfo(String trade_no) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		String time = sdf.format(new Date());

		Map<String, String> map = new HashMap<String, String>();
		map.put("merc_id", Constants.MERC_ID);
		map.put("salt", MD5SignAndValidate.getRandomString(32));
		map.put("req_time", time);
		map.put("signature", "");
		map.put("trade_no", trade_no);
		String body = getResponseBody(
				String.format(Constants.BASE_URL, Constants.VERSION, Constants.APPID, Constants.QUERY_ORDER) + "?sig="
						+ getSigParameter(Constants.APPID, Constants.TOKEN, time),
				JSON.toJSONString(MD5SignAndValidate.signData(map, Constants.KEY)),
				getAuthorization(Constants.APPID, time));

		LOG.info("查询提现状态返回值：" + body);
		if (StringUtils.isBlank(body)) {
			LOG.info("查询提现状态返回值错误");
			throw new CommonException(ICommon.ORDER_BODY_EEROR, PropertiesUtils.get(ICommon.ORDER_BODY_EEROR));
		}
		JSONObject jsonObject = JSONObject.parseObject(body);
		if (!StringUtils.equals(jsonObject.getString("ret_code"), "000000")) {
			LOG.info("查询提现状态返回错误码：" + jsonObject.getString("ret_msg"));
			throw new CommonException(ICommon.ORDER_RET_CODE, jsonObject.getString("ret_msg"));
		}
		String result = jsonObject.getString("result");

		JSONObject resultJson = JSON.parseObject(result);
        Map<String, String> resultMap = new HashMap<String, String>();

        for (String key : jsonObject.keySet()) {
            resultMap.put(key, jsonObject.getString(key));
        }

        if(!MD5SignAndValidate.validateData(resultMap, Constants.KEY)){
            throw new CommonException(1000000001, "查询提现状态验签失败");
        }

        Map<String, String> orderInfoMap = new HashMap<String, String>();
        orderInfoMap.put("trade_no", resultJson.getString("trade_no"));
        orderInfoMap.put("merc_order_no", resultJson.getString("merc_order_no"));
        orderInfoMap.put("trade_sts", resultJson.getString("transfer_status"));
		return orderInfoMap;
	}


	/**
	 * 
	 * @Title: getPaySDKNotify @Description: 支付回调通知解析 @param @param
	 * body @param @return @return PaySDKNotify @throws
	 */
	public static PaySDKNotify getPaySDKNotify(String body) {

		LOG.info("支付回调：" + body);

		if (body == null) {
			throw new CommonException(1000000001, "获取报文错误");
		}

		JSONObject jsonObject = JSONObject.parseObject(body);

		LOG.info("支付回调：signature:" + jsonObject.getString("signature"));

		Map<String, String> resultMap = new HashMap<String, String>();

		for (String key : jsonObject.keySet()) {
			resultMap.put(key, jsonObject.getString(key));
		}

        if(!MD5SignAndValidate.validateData(resultMap, Constants.KEY)){
            throw new CommonException(1000000001, "验签失败");
        }

		if (!jsonObject.getString("ret_code").equals("000000")) {
			LOG.info("支付回调：result:" + jsonObject.getString("result"));
			return null;
		}

		JSONObject resultJson = JSON.parseObject(jsonObject.getString("result"));
		String pay_cnl = resultJson.getString("pay_cnl");// WX：微信支付 ZFB：支付宝
		String currency = resultJson.getString("currency");// 货币类型
		double trade_amt = resultJson.getDoubleValue("trade_amt");// 交易金额
		String trade_no = resultJson.getString("trade_no");// 交易号
		String merc_order_no = resultJson.getString("merc_order_no");// 商户订单号
		String settle_date = resultJson.getString("settle_date");// 清算日期
		String trade_sts = resultJson.getString("trade_sts");// 交易状态
																// SUCCESS：交易成功
																// FAILED：交易失败
		String attach = resultJson.getString("attach");// 附加数据

		PaySDKNotify paySDKNotify = new PaySDKNotify();
		paySDKNotify.setAttach(attach);
		paySDKNotify.setCurrency(currency);
		paySDKNotify.setMerc_order_no(merc_order_no);
		paySDKNotify.setPay_cnl(pay_cnl);
		paySDKNotify.setSettle_date(settle_date);
		paySDKNotify.setTrade_amt(Long.parseLong(AmountUtils.changeY2F(String.valueOf(trade_amt))));
		paySDKNotify.setTrade_no(trade_no);
		paySDKNotify.setTrade_sts(trade_sts);
		return paySDKNotify;
	}


    public static Map<String, String> extractCash(WithdrawRecord record) {

        /************************************************
         * 提现预下单 start
         ************************************************/
        Map<String, String> resultMap = new HashMap<String, String>();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(new Date());
        UnifiedOrder unifiedOrder = new UnifiedOrder();
        unifiedOrder.setMerc_id(Constants.MERC_ID);
        unifiedOrder.setSalt(MD5SignAndValidate.getRandomString(32));
        unifiedOrder.setSignature(getSigParameter(Constants.APPID, Constants.TOKEN, time));// 签名
        unifiedOrder.setReq_time(time);
        unifiedOrder.setMerc_order_no(record.getId());
        unifiedOrder.setTransfer_amount(record.getCash());
        unifiedOrder.setRecipient_account_number(record.getPayeeno());
        unifiedOrder.setRecipient_account_name(record.getPayeename());
        unifiedOrder.setDestination_bank_name(record.getDestinationname());
        unifiedOrder.setDestination_bank_code(record.getDestinationcode());
        unifiedOrder.setRecipient_account_type("2"); // 1对公 2对私
        unifiedOrder.setTrade_desc("奖金提现");
        unifiedOrder.setCurrency(CurrencyEnums.RMB.getCode());
        unifiedOrder.setReq_ip(IpUtil.getIpAddr(request));
        unifiedOrder.setSub_merc_id(Constants.SUB_MERC_ID);

        String body = getResponseBody(
                String.format(Constants.BASE_URL, Constants.VERSION, Constants.APPID, Constants.TRANSFER_UNIFIED) + "?sig="
                        + getSigParameter(Constants.APPID, Constants.TOKEN, time),
                JSON.toJSONString(MD5SignAndValidate.signData(objectToMap(unifiedOrder), Constants.KEY)),
                getAuthorization(Constants.APPID, time));
        LOG.info("聚合支付返回值：" + body);
        if (StringUtils.isBlank(body)) {
            LOG.info("聚合支付返回值错误");
            throw new CommonException(ICommon.ORDER_BODY_EEROR, PropertiesUtils.get(ICommon.ORDER_BODY_EEROR));
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        if (!StringUtils.equals(jsonObject.getString("ret_code"), "000000")) {
            LOG.info("聚合支付返回值返回错误码：" + jsonObject.getString("ret_msg"));
            throw new CommonException(ICommon.ORDER_RET_CODE, jsonObject.getString("ret_msg"));

        }
        Map<String, String> map = new HashMap<String, String>();

        for (String key : jsonObject.keySet()) {
            map.put(key, jsonObject.getString(key));
        }

        if (!MD5SignAndValidate.validateData(map, Constants.KEY)) {
            throw new CommonException(1000000001, "提现创建预交易订单验签失败");
        }

        String tradeNo = JSONObject.parseObject(jsonObject.getString("result")).getString("trade_no");
        if (StringUtils.isBlank(tradeNo)) {
            LOG.info("返回交易号空值");
            throw new CommonException(ICommon.ORDER_TRADENO_ERROR, PropertiesUtils.get(ICommon.ORDER_TRADENO_ERROR));
        }
        /************************************************
         * 提现预下单 end
         ************************************************/

        /************************************************
         * 提现 start
         ************************************************/
        String chargeTime = sdf.format(new Date());
        Charge charge = new Charge();
        charge.setMerc_id(Constants.MERC_ID);
        charge.setSalt(MD5SignAndValidate.getRandomString(32));//
        charge.setSignature(getSigParameter(Constants.APPID, Constants.TOKEN, chargeTime));
        charge.setReq_time(chargeTime);
        charge.setTrade_no(tradeNo);
        charge.setTrade_channel("CCB_YQ_HUI10");// CCB_YQ_HUI10：建行银企直连-汇拾

        body = getResponseBody(
                String.format(Constants.BASE_URL, Constants.VERSION, Constants.APPID, Constants.TRANSFER_PAY) + "?sig="
                        + getSigParameter(Constants.APPID, Constants.TOKEN, time),
                JSON.toJSONString(MD5SignAndValidate.signData(objectToMap(charge), Constants.KEY)),
                getAuthorization(Constants.APPID, time));
        LOG.info("聚合支付返回值：" + body);
        if (StringUtils.isBlank(body)) {
            LOG.info("提现返回值错误");
            throw new CommonException(ICommon.ORDER_BODY_EEROR, PropertiesUtils.get(ICommon.ORDER_BODY_EEROR));
        }
        JSONObject jsonObjectCharge = JSONObject.parseObject(body);
        if (!StringUtils.equals(jsonObjectCharge.getString("ret_code"), "000000")) {
            LOG.info("提现返回值返回错误码：" + jsonObjectCharge.getString("ret_msg"));
            throw new CommonException(ICommon.ORDER_RET_CODE, jsonObjectCharge.getString("ret_msg"));
        }

        Map<String, String> map_sdkparam = new HashMap<String, String>();

        for (String key : jsonObjectCharge.keySet()) {
            map_sdkparam.put(key, jsonObjectCharge.getString(key));
        }
        if (!MD5SignAndValidate.validateData(map_sdkparam, Constants.KEY)) {
            throw new CommonException(1000000001, "提现交易验签失败");
        }

        String trade_no = JSONObject.parseObject(jsonObjectCharge.getString("result")).getString("trade_no");
        if (StringUtils.isBlank(trade_no)) {
            LOG.info("渠道方支付报文空值");
            throw new CommonException(ICommon.ORDER_AROUSESDKPARAM_ERROR,
                    PropertiesUtils.get(ICommon.ORDER_AROUSESDKPARAM_ERROR));
        }
        /************************************************
         * 提现 end
         ************************************************/
        resultMap.put("trade_no", trade_no);

        return resultMap;
    }

	/**
	 * 支付成功的订单信息发送给汇拾支付系统
	 * @param params
	 * @return
	 */
    public static String uplNotify(Map<String,String> params){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = sdf.format(new Date());
		params.put("merc_id", Constants.MERC_ID);
		params.put("sub_merc_id",Constants.SUB_MERC_ID);
		params.put("salt", MD5SignAndValidate.getRandomString(32));
		params.put("req_time", time);
		params.put("signature", "");
		String body = getResponseBody(
				String.format(Constants.BASE_URL, Constants.VERSION, Constants.APPID, Constants.UPLNOTIFY) + "?sig="
						+ getSigParameter(Constants.APPID, Constants.TOKEN, time),
				JSON.toJSONString(MD5SignAndValidate.signData(params, Constants.KEY)),
				getAuthorization(Constants.APPID, time));

		return body;
	}


}
