package com.xing.app.myutils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class CipherUtil {

    private CipherUtil() {}

    /**
     * MD5加密
     * @param dataStr 要加密的字符串
     * @param slat 盐值 （可为空
     */
    public static String encryptMD5(String dataStr,String slat) {
        try {
            dataStr = dataStr + slat;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < s.length; i++) {
                result.append(Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static byte[] exeCrypt(int mode, byte[] src, byte[] key)
            throws RuntimeException {

        String DES = "DES";
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(mode, securekey, sr);
            return cipher.doFinal(src);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对称解密方法
     */
    public static String decryptDES(String data, String key) {
        return data != null ? new String(exeCrypt(Cipher.DECRYPT_MODE,
                hex2byte(data.getBytes()), key.getBytes())) : null;
    }

    /**
     * 对称加密方法
     */
    public static String encryptDES(String data, String key) {
        return data != null ? byte2hex(exeCrypt(Cipher.ENCRYPT_MODE,
                data.getBytes(), key.getBytes())) : null;
    }

    /**
     * 将byte类型数组转为16进制字符串
     */
    private static String byte2hex(byte[] b) {
        if (b == null) return "";
        StringBuilder sb = new StringBuilder();
        String str;
        for (int i = 0;i < b.length; i++) {
            System.out.println(b[i]);
            str = Integer.toHexString(b[i] & 0XFF);
            if (str.length() == 1)
                sb.append('0');
            sb.append(str);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 将16进制字符串转为byte数组
     */
    private static byte[] hex2byte(byte[] b) {
        if (MathUtil.isOddNum(b.length)) return null;
        byte[] result = new byte[b.length / 2];
        for (int i = 0; i < b.length; i += 2) {
            String str = new String(b, i, 2);
            result[i / 2] = (byte) Integer.parseInt(str, 16);
        }
        return result;
    }

    /**
     * 获取指定文件的MD5
     * @param path 文件路径
     * @return MD5 字符串
     */
    public static String getFileMD5(String path) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bi.toString(16);
    }

}
