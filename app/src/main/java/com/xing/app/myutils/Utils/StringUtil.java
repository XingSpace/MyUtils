package com.xing.app.myutils.Utils;

public class StringUtil {

    public static boolean isEmpty(String s){
        return s == null || s.length() == 0;
    }

    /**
     * 传入的字符串中，只要有一个空字符串，就返回true
     */
    public static boolean isEmpty(String...strings){
        for (String s : strings){
            if (isEmpty(s)){
                return true;
            }
        }
        return false;
    }

}
