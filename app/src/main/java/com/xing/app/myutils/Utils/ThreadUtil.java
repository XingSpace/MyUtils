package com.xing.app.myutils.Utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

public class ThreadUtil {

    private ThreadUtil() {}

    private static final int DelayedRunChildThread = 1;

    private static final int DO_DELAYED_MAIN_THREAD = 2;
    private static final int DO_DELAYED_CHILD_THREAD = 3;

    private static Map<String, LoopThread> mapThread = new HashMap<>();

    //延迟任务栈
    private static Map<String,Runnable> delayMap = new HashMap<>();


    @SuppressLint("HandlerLeak")
    private static Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case DelayedRunChildThread:
                    runOnChildThread((Runnable) msg.obj);
                    break;
                case DO_DELAYED_MAIN_THREAD:
                    Runnable main = delayMap.get(msg.obj.toString());
                    if (main != null){
                        main.run();
                        delayMap.remove(msg.obj.toString());
                    }
                    break;
                case DO_DELAYED_CHILD_THREAD:
                    Runnable child = delayMap.get(msg.obj.toString());
                    if (child != null){
                        runOnChildThread(child);
                        delayMap.remove(msg.obj.toString());
                    }
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
     * 在主线程中延迟执行一个Runnable接口
     * @param time 延迟时间，以毫秒为单位
     * @return 每个延迟任务的唯一tag标记
     */
    public static String runOnMainThreadDelayed(Runnable runnable, long time) {
        if (runnable == null || time < 0) return "";
        //生成唯一的tag标识
        String tag = String.valueOf(runnable.hashCode() + System.currentTimeMillis());
        delayMap.put(tag, runnable);
        Message message = Message.obtain();
        message.what = DO_DELAYED_MAIN_THREAD;
        message.obj = tag;
        handler.sendMessageDelayed(message, time);
        return tag;
    }

    /**
     * 在子线程中执行一个Runnable
     * @param time 延迟执行的时间
     * @return 每个延迟任务的唯一tag
     */
    public static String runOnChildThreadDelayed(Runnable runnable, long time) {
        if (runnable == null || time < 0) return "";
        //生成唯一的tag标识
        String tag = String.valueOf(runnable.hashCode() + System.currentTimeMillis());
        delayMap.put(tag, runnable);
        Message message = Message.obtain();
        message.what = DO_DELAYED_CHILD_THREAD;
        message.obj = tag;
        handler.sendMessageDelayed(message, time);
        return tag;
    }

    public static void removeDelayedThread(String tag){
        delayMap.remove(tag);
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
