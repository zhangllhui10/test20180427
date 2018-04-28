/**
 *
 */
package com.hui10.app.common.lottery;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.common.constants.Constants;
import com.hui10.common.utils.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author 4074
 * @ClassName: LotteryStewardUtils.java
 * @Description: 调用福彩管家后台
 * @date 2017年1月12日 下午1:56:52
 */
public class LotteryStewardUtils {


    private static final Logger LOG = Logger.getLogger(LotteryStewardUtils.class);


    /**
     * 校验福彩传递的消息信息
     *
     * @param body
     * @return
     * @user yangcb
     * @date 2017年1月11日 下午3:53:30
     */
    public static JSONObject checkBody(String body) {
    	 LOG.info("校验福彩传递的消息信息：" + body );
        HttpResult<String> httpResult = new HttpResult<String>();
        if (StringUtils.isNotBlank(body)) {

            JSONObject jsonObject = JSON.parseObject(body);
            String signature = jsonObject.getString("signature");
            HttpParams<String> httpParams = new HttpParams<String>();
            httpParams.setChannelId(jsonObject.getString("channelId"));
            httpParams.setTimestamp(jsonObject.getLong("timestamp"));
            httpParams.setTransSerialNumber(jsonObject.getString("transSerialNumber"));
            httpParams.setSignature("null");
            httpParams.setTransData(jsonObject.getString("transData"));
            String md5 = Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(httpParams), Constants.LOTTERY_MD5_SUFFIX);

            if (!md5.equals(signature)) {
                LOG.error("验签失败：" + body + " 生成的验签码：" + md5);
                httpResult.setResult("102");
                httpResult.setResultDesc("数据包验签失败");
                httpResult.setTransData("null");
                httpResult.setSignature(Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(httpResult), Constants.LOTTERY_MD5_SUFFIX));
                throw new ResultException(httpResult);
            }

            String transData = jsonObject.getString("transData");
            if (transData == null || "null".equals(transData)) {
                httpResult.setResult("103");
                httpResult.setResultDesc("数据包解析错误");
                httpResult.setTransData("null");
                httpResult.setSignature(Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(httpResult), Constants.LOTTERY_MD5_SUFFIX));
                throw new ResultException(httpResult);
            }
            return jsonObject;

        } else {
            LOG.error("获取的channelToken错误");
            httpResult.setResult("999");
            httpResult.setResultDesc("未知错误");
            httpResult.setTransData("null");
            httpResult.setSignature(Md5Util.getSignature(Constants.LOTTERY_MD5_PREFIX, JSON.toJSONString(httpResult), Constants.LOTTERY_MD5_SUFFIX));
            throw new ResultException(httpResult);
        }
    }


}
