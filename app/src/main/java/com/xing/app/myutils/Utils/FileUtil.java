package com.xing.app.myutils.Utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    private FileUtil() {}

    public static final String SD_Path = Environment.getExternalStorageDirectory().getPath();

    /**
     * 删除文件夹（包含文件夹目录下所有资源）
     */
    public static boolean deleteFile(File file) {
        if (file == null) return false;
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
        if (original == null || target == null) return false;
        if (!target.exists()){
            target.mkdirs();
        }
        File newPath = new File(target,original.getName());
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
        if (original == null || target == null) return false;
        if (original.exists()) {
            return moveFile(original,target,isCover);
        }
        return false;
    }

    /**
     * 文件复制
     * @param isConver 是否覆盖
     */
    public static boolean copyFile(File from, File to, boolean isConver) throws IOException {

        if(!isConver && to.exists()){
            return false;
        }

        if(!from.exists() || from.isDirectory()){
            LogUtil.d("The File is not exists or The File is a Directory");
            return false;
        }

        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(from);
            output = new FileOutputStream(to);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
            output.flush();
        }catch(Exception e){
            LogUtil.e("CopyFile Exception:" + e.getMessage());
            return false;
        } finally {
            if(input != null) input.close();
            if(output != null) output.close();
        }
        return true;
    }

}
