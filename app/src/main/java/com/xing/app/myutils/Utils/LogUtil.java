package com.xing.app.myutils.Utils;

import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.util.Arrays;

public class LogUtil {

    private LogUtil() {}

    public static String TAG = "MyUtils";

    public static String path = FileUtil.SD_Path;

    private static String appName = "MyUtils";

    private static final long maxsize = 204800;// 200KB 就换文件
    private static final long timeLimit = 7776000000L;// 90天

    /**
     * 使用前应该先初始化
     * @param appName 文件夹的名字
     */
    public static void init(String appName){
        LogUtil.appName = appName;
        LogUtil.TAG = appName;
        File file = new File(path+"/"+ appName);
        if (!file.exists() || !file.isDirectory()){
            file.mkdirs();
        }
    }

    /**
     * 将日志写入本地
     */
    private static void writeLog(final String tag,final String s){
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileWriter fw = null;
                try{

                    String name = getLogFileName();

                    File file = new File(name.substring(0,name.lastIndexOf("/")),
                            name.substring(name.lastIndexOf("/")+1));

                    if (!file.exists()){
                        file.createNewFile();
                    }

                    String time = DateUtil.getYYYYMMDDHHMMSS() + " ";
                    fw = new FileWriter(file,true);

                    fw.write(time + "[" + tag + "]:" + s +"\n");

                    fw.flush();

                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    try{
                        if (fw != null){
                            fw.close();
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void d(String s){
        Log.d(TAG,s);
        writeLog("DEBUG",s);
    }

    public static void e(String s){
        Log.e(TAG,s);
        writeLog("ERROR",s);
    }

    public static void i(String s){
        Log.i(TAG,s);
        writeLog("INFO",s);
    }

    public static void w(String s){
        Log.w(TAG,s);
        writeLog("WARRING",s);
    }

    public static void v(String s){
        Log.v(TAG,s);
        writeLog("VERBOSE",s);
    }

    /**
     * @return 日志的文件名
     */
    private static String getLogFileName(){
        File file = new File(path+"/"+ appName);
        if (!file.exists()){
            file.mkdirs();
        }

        FileFilter fileFilter = new FileFilter() {
            long nowTime = System.currentTimeMillis();

            @Override
            public boolean accept(File file) {
                if (file.getName().endsWith(".log")){
                    if (file.lastModified() < nowTime - timeLimit){
                        file.delete();
                        return false;
                    }else{
                        return file.length() < maxsize;
                    }
                }
                return false;
            }
        };

        File[] files = file.listFiles(fileFilter);

        if (files == null || files.length == 0){
            String fileTime = DateUtil.convert2String(System.currentTimeMillis());
            fileTime = fileTime.replaceAll("-", "");
            fileTime = fileTime.replaceAll(":", "");
            fileTime = fileTime.replaceAll(" ", "");

            return file.getAbsolutePath() +"/"+ fileTime + ".log";
        } else {
            Arrays.sort(files);
            return files[0].getAbsolutePath();
        }
    }

}
