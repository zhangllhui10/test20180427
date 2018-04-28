package com.hui10.app.common.encrypt;


public class AesUtils {

    private static final String key = "-*hs@unionpay!";

    public static String encrypt(String text){

        try {
            return AesBase.getInstance().encrypt(text, key);
        } catch (Throwable e) {
            return "";
        }

    }
    
    public static String encrypt(String text, String key){

        try {
            return AesBase.getInstance().encrypt(text, key);
        } catch (Throwable e) {
            return "";
        }

    }
    
    public static String decrypt(String code){

        try {
            return AesBase.getInstance().decrypt(code, key);
        } catch (Throwable e) {
            return "";
        }

    }
    
    public static String decrypt(String code, String key){

        try {
            return AesBase.getInstance().decrypt(code, key);
        } catch (Throwable e) {
            return "";
        }

    }

}
