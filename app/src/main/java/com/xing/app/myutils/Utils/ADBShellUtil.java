package com.xing.app.myutils.Utils;

import java.io.DataOutputStream;

/**
 * 学习使用shell命令的工具类
 */
public class ADBShellUtil {

    public static boolean execCmd(String... cmds){
        if (cmds == null || cmds.length == 0){
            return false;
        }

        DataOutputStream dos = null;
        Process process = null;

        try {
            process = Runtime.getRuntime().exec("su");

            dos = new DataOutputStream(process.getOutputStream());

            for (String cmd:cmds){
                dos.writeBytes(cmd+"\n");
            }

            dos.writeBytes("exit \n");
            dos.flush();

            return process.waitFor() == 0;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (dos != null){
                    dos.close();
                }
                if (process != null){
                    process.destroy();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }

}
