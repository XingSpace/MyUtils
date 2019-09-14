package com.xing.app.myutils.Utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileUtil {

    public static final String SD_Path = Environment.getExternalStorageDirectory().getPath();

    /**
     * 删除文件夹（包含文件夹目录下所有资源）
     */
    public static boolean deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (String child : children) {
                    deleteFile(new File(file, child));
                }
            }
            return file.delete();
        }
        return false;
    }

    /**
     * 复制完成后的路径 target.getAbsolutePath() + "/" + original.getName();
     * @param original File original = new File("C://old_path","xxx.txt");
     * @param target File target = new File("C://new_path");
     * @param isCover 如果目标路径已经存在一个同名文件，是否覆盖它
     */
    public static boolean moveFile(File original, File target,boolean isCover) {
        if (!target.exists()){
            target.mkdirs();
        }
        File newPath = new File(target,original.getName());
        Log.e("fuck",newPath.getAbsolutePath());
        if (newPath.exists()){
            if (isCover){
                deleteFile(newPath);
                return original.renameTo(newPath);
            }else {
                return false;
            }
        }else {
            return original.renameTo(newPath);
        }
    }

    /**
     * 此方法用于复制整个文件夹
     * 复制完成后的路径 target.getAbsolutePath() + "/" + original.getName();
     * @param original File original = new File("C://old_path");
     * @param target File target = new File("C://new_path");
     * @param isCover 是否覆盖已有的文件
     */
    public static boolean moveFiles(File original, File target, boolean isCover) {
        if (original.exists()) {
            return moveFile(original,target,isCover);
        }
        return false;
    }

}
