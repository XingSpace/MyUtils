package com.xing.app.myutils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 */
public class DateUtil {

    private DateUtil(){}

    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

    public static long convert2Long(String date){
        return convert2Long(date,yyyyMMddHHmmss);
    }

    /**
     * 将字符串形式的时间，转换为long类型，适用于比较时间
     * @return 以毫秒为单位的date时间
     */
    public static long convert2Long(String date,String format){
        if (StringUtil.isEmpty(date,format)) return 0L;

        try {
            SimpleDateFormat sf = new SimpleDateFormat(format, Locale.getDefault());
            return sf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0L;
    }

    public static Date convert2Date(String date){
        return convert2Date(date,yyyyMMddHHmmss);
    }

    public static Date convert2Date(String date,String format){
        if (StringUtil.isEmpty(date,format)) return null;

        try {
            SimpleDateFormat sf = new SimpleDateFormat(format, Locale.getDefault());
            return sf.parse(date);
        }catch (ParseException e){
            e.printStackTrace();
        }

        return null;
    }

    public static String convert2String(long time){
        return convert2String(time,yyyyMMddHHmmss);
    }

    /**
     * 将毫秒形式的时间，转换为String
     * @return 返回字符串格式的时间戳
     */
    public static String convert2String(long time,String format){
        if (time <= 0 || StringUtil.isEmpty(format)) return "";
        return convert2String(new Date(time),format);
    }


    public static String convert2String(Date date,String format){
        SimpleDateFormat sf = new SimpleDateFormat(format, Locale.getDefault());
        return sf.format(date);
    }

    /**
     * @return 年月日格式的字符串
     */
    public static String getYYYYMMDD(){
        return convert2String(System.currentTimeMillis(),yyyyMMdd);
    }

    /**
     * @return 年月日时分秒格式字符串
     */
    public static String getYYYYMMDDHHMMSS(){
        return convert2String(System.currentTimeMillis());
    }

}
