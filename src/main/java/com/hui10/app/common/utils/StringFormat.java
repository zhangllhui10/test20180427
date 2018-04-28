package com.hui10.app.common.utils;

import com.hui10.common.utils.StringUtils;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2018年03月08日 10:10
 */
public class StringFormat {

    /**
     * 银行卡号保留后4位其它打*处理
     * @param cardNumber
     * @return
     */
    public static String formatCardNumber(String cardNumber) {
        if (StringUtils.isBlank(cardNumber)) {
            return null;
        }
        int startIndex = cardNumber.length() - 4;
        int endIndex = cardNumber.length();

        String before = cardNumber.substring(0, startIndex);
        String after = cardNumber.substring(startIndex, endIndex);
        before = before.replaceAll("\\d{4}(?!$)", "$0 ").replaceAll("[0-9]", "*");
        String cardNo = before + " " +after;
        return cardNo;
    }

    public static String formatName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        return name.replaceAll(".(?!$)", "*");
    }

    public static String formatPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }

}

