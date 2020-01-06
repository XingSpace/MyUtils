package com.xing.app.myutils.Utils;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

public class ToastUtil {

    private ToastUtil() {}

    private static Toast mToast;
    private static Handler handler = new Handler();

    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mToast != null){
                mToast.cancel();
                mToast = null;
            }
        }
    };

    public synchronized static void show(Context context,String text){
        showToast(context,text,Toast.LENGTH_LONG);
    }

    public synchronized static void show(Context context,int StringID){
        showToast(context, context.getString(StringID),Toast.LENGTH_LONG);
    }

    public synchronized static void showToast(Context context,String text,int duration){
        if (Build.VERSION.SDK_INT >= 27){
            Toast.makeText(context,text,duration).show();
            return;
        }
        handler.removeCallbacks(runnable);
        if (mToast == null){
            mToast = new Toast(context);
        }
        mToast.setText(text);
        mToast.setDuration(duration);
        mToast.show();
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,2000L);
    }

}
