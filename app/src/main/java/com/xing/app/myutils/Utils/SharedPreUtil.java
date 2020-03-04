package com.xing.app.myutils.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * 轻量级文本缓存工具类
 */
public class SharedPreUtil {

    private final static String SPConfig = "SPConfig";

    private static SharedPreferences sp;
    private static SharedPreferences.Editor spe;

    private static SharedPreUtil sharedPreUtil;

    private SharedPreUtil(Context context) {
        sp = context.getSharedPreferences(SPConfig, Context.MODE_PRIVATE);
        spe = sp.edit();
    }

    public static void init(Context context) {
        if (sharedPreUtil == null) {
            synchronized (SharedPreUtil.class) {
                sharedPreUtil = new SharedPreUtil(context);
            }
        }
    }

    public static void close() {
        synchronized (SharedPreUtil.class) {
            spe = null;
            sp = null;
            sharedPreUtil = null;
            System.gc();
        }
    }

    public static boolean put(String key, String value) {
        spe.putString(key, value);
        return spe.commit();
    }

    public static boolean put(String key, Set<String> value) {
        spe.putStringSet(key, value);
        return spe.commit();
    }

    public static boolean put(String key, int value) {
        spe.putInt(key, value);
        return spe.commit();
    }

    public static boolean put(String key, float value) {
        spe.putFloat(key, value);
        return spe.commit();
    }

    public static boolean put(String key, boolean value) {
        spe.putBoolean(key, value);
        return spe.commit();
    }

    public static boolean put(String key, long value) {
        spe.putLong(key, value);
        return spe.commit();
    }

    public static String getString(String key){
        return getString(key,"");
    }

    public static String getString(String key,String def){
        return sp.getString(key,def);
    }

    public static Set<String> getStringSet(String key){
        return getStringSet(key,null);
    }

    public static Set<String> getStringSet(String key, Set<String> def){
        return sp.getStringSet(key,def);
    }

    public static int getInt(String key){
        return getInt(key,0);
    }

    public static int getInt(String key,int def){
        return sp.getInt(key,def);
    }

    public static long getLong(String key){
        return getLong(key,0L);
    }

    public static long getLong(String key,long def){
        return sp.getLong(key,def);
    }

    public static boolean getBoolean(String key){
        return getBoolean(key,false);
    }

    public static boolean getBoolean(String key,boolean def){
        return sp.getBoolean(key,def);
    }

    public static float getFloat(String key){
        return getFloat(key,0f);
    }

    public static float getFloat(String key,float def){
        return sp.getFloat(key,def);
    }


}
