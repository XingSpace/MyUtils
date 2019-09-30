package com.xing.app.myutils.Utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

public class ThreadUtil {

    private static final int DelayedRunChildThread = 1;

    private static Map<String, LoopThread> mapThread = new HashMap<>();

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
        if (runnable == null) return;
        if (isRunMainThread()) {
            runnable.run();
        } else {
            handler.post(runnable);
        }
    }

    public static void runOnChildThread(Runnable runnable) {
        if (runnable == null) return;
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
        if (runnable == null || time < 0) return;
        handler.postDelayed(runnable, time);
    }

    public static void runOnChildThreadDelayed(Runnable runnable, long time) {
        if (runnable == null || time < 0) return;
        Message message = Message.obtain();
        message.what = DelayedRunChildThread;
        message.obj = runnable;
        handler.sendMessageDelayed(message, time);
    }

    /**
     * @param tag  要被执行的循环线程标记,如果tag重复，则不会执行
     * @param time 执行间隔时间  毫秒为单位
     */
    public static void runOnChildThreadLoop(Runnable runnable, String tag, long time) {
        if (runnable == null || time < 0 || StringUtil.isEmpty(tag)) return;
        LoopThread loopThread = mapThread.get(tag);
        if (loopThread == null) {
            loopThread = new LoopThread();
            mapThread.put(tag, loopThread);
            loopThread.setRunnable(runnable)
                    .setSleepTime(time)
                    .start();
        }
    }

    public static void stopChildThreadLoopWithTag(String tag) {
        if (StringUtil.isEmpty(tag) || mapThread.size() == 0) return;
        LoopThread loopThread = mapThread.get(tag);
        if (loopThread != null) {
            loopThread.close();
            mapThread.remove(tag);
        }
    }

    private static class LoopThread extends Thread {
        private boolean isRun = true;
        private long sleepTime = 0;
        private Runnable runnable;

        @Override
        public void run() {
            while (isRun) {
                if (runnable != null) {
                    runnable.run();
                }
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 停止线程
         */
        void close() {
            isRun = false;
        }

        LoopThread setRunnable(Runnable runnable) {
            this.runnable = runnable;
            return this;
        }

        LoopThread setSleepTime(long sleepTime) {
            this.sleepTime = sleepTime;
            return this;
        }
    }

}
