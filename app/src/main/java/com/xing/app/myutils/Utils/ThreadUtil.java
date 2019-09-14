package com.xing.app.myutils.Utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class ThreadUtil {

    private static final int DelayedRunChildThread = 1;

    @SuppressLint("HandlerLeak")
    private static Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case DelayedRunChildThread:
                    runOnChildThread((Runnable) msg.obj);
                    break;
            }
        }
    };

    public static boolean isRunMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static void runOnMainThread(Runnable runnable) {
        if (isRunMainThread()) {
            runnable.run();
        } else {
            handler.post(runnable);
        }
    }

    public static void runOnChildThread(Runnable runnable) {
        if (isRunMainThread()) {
            new Thread(runnable).start();
        } else {
            runnable.run();
        }
    }

    /**
     * @param time 延迟时间，以毫秒为单位
     */
    public static void runOnMainThreadDelayed(Runnable runnable, long time) {
        handler.postDelayed(runnable, time);
    }

    public static void runOnChildThreadDelayed(Runnable runnable, long time) {
        Message message = Message.obtain();
        message.what = DelayedRunChildThread;
        message.obj = runnable;
        handler.sendMessageDelayed(message, time);
    }

}
