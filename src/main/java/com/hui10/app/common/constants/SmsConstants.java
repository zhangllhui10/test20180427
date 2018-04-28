package com.hui10.app.common.constants;

import com.hui10.common.utils.PropertiesUtils;

/**
 * @ClassName: SmsConstants.java
 * @Description:
 * @author fanht
 * @date 2017年1月13日 上午9:21
 */
public interface SmsConstants {
    /**
     * 平台通用-验证码动态时间
     */
    String COMMON_CODE="smsTpl:23ac8a13-8983-4be8-bd6f-f54cc615923c";

    /**
     * 出票成功短信模板
     */
    String SMS_SUCCESS_TICKET= PropertiesUtils.get("SMS_SUCCESS_TICKET");


}
