package com.xing.app.myutils.Utils;

import android.util.Log;

public class LogUtil {

    public static String TAG = "MyUtils";

    public static void d(String s){ Log.d(TAG,s); }

    public static void e(String s){ Log.e(TAG,s); }

    public static void i(String s){ Log.i(TAG,s); }

    public static void w(String s){ Log.w(TAG,s); }

    public static void v(String s){ Log.v(TAG,s); }

}
