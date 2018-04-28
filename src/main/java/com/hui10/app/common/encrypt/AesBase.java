package com.hui10.app.common.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import net.iharder.Base64;

public class AesBase {

    private ThreadLocal<IvParameterSpec> ivspec = new ThreadLocal<IvParameterSpec>() {
        protected synchronized IvParameterSpec initialValue() {
            return new IvParameterSpec("XS0lOO7ZJQ%&*vgH".getBytes());
        }
    };

    private ThreadLocal<Cipher> cipher = new ThreadLocal<Cipher>() {
        protected synchronized Cipher initialValue() {
            try {
                return Cipher.getInstance("AES/CBC/PKCS7Padding");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    // private byte[] iv = { 0xD, 91, 0xB, 5, 4, 0xA, 2, 0xF, 7, 11, 0x17, 6, 8, 3, 1, 0xC };
    private AesBase() throws NoSuchAlgorithmException, NoSuchPaddingException {
        // ivspec = new IvParameterSpec("GUgemWNhGTrh6kSM".getBytes());

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // cipher = Cipher.getInstance("AES/CBC/PKCS7Padding"); // java.security.NoSuchAlgorithmException: Cannot find any provider supporting AES/CBC/PKCS7Padding

        // cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

    }

    private static AesBase instance = null;

    private IvParameterSpec getIvspec() {

        IvParameterSpec tmp = ivspec.get();

        return tmp;
    }

    private Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {

        Cipher tmp = cipher.get();

        return tmp;
    }

    public static AesBase getInstance() throws NoSuchAlgorithmException, NoSuchPaddingException {
        if (instance == null) {
            instance = new AesBase();
        }
        return instance;
    }

    public String encrypt(String text, String key){
        try {
            SecretKeySpec keyspec = new SecretKeySpec(MD5(key), "AES");
            getCipher().init(Cipher.ENCRYPT_MODE, keyspec, getIvspec());
            byte[] encrypted = getCipher().doFinal(text.getBytes());

            return Base64.encodeBytes(encrypted);
            // return encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private byte[] encryptBytes(byte[] content, String key) {
        try {
            SecretKeySpec keyspec = new SecretKeySpec(MD5(key), "AES");
            getCipher().init(Cipher.ENCRYPT_MODE, keyspec, getIvspec());
            byte[] encrypted = getCipher().doFinal(content);
            return encrypted;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密失败", e);
        }
    }

    public String decrypt(String code, String key) throws Exception {
        if (code == null || code.length() == 0)
            throw new Exception("Empty string");

        try {
            SecretKeySpec keyspec = new SecretKeySpec(MD5(key), "AES");
            getCipher().init(Cipher.DECRYPT_MODE, keyspec, getIvspec());

            // byte[] bytes = code.getBytes();
            // byte[] decrypted = getCipher().doFinal(Base64.decode(bytes, 0, bytes.length, Base64.NO_OPTIONS));
            byte[] decrypted = getCipher().doFinal(Base64.decode(code));
            // byte[] decrypted = cipher.doFinal(Base64.decode(code.getBytes()));
            return new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解密失败", e);
        }
    }

    private byte[] decryptBytes(byte[] content, String key) {
        try {
            SecretKeySpec keyspec = new SecretKeySpec(MD5(key), "AES");
            getCipher().init(Cipher.DECRYPT_MODE, keyspec, getIvspec());
            byte[] decrypted = getCipher().doFinal(Base64.decode(new String(content)));
            // byte[] decrypted = cipher.doFinal(Base64.decode(content));
            return decrypted;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解密失败", e);
        }
    }

    private static String bytesToHex(byte[] data) {
        if (data == null) {
            return null;
        }

        int len = data.length;
        String str = "";
        for (int i = 0; i < len; i++) {
            if ((data[i] & 0xFF) < 16)
                str = str + "0" + Integer.toHexString(data[i] & 0xFF);
            else
                str = str + Integer.toHexString(data[i] & 0xFF);
        }
        return str;
    }

    private static byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }

    private static byte[] MD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return md.digest();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("hash256失败", e);
        }
    }

}