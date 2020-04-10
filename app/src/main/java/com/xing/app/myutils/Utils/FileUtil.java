package com.xing.app.myutils.Utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

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

    /**
     * 在指定的 {@code path} 路径下创建一个 {@code name} 文件
     *
     * @param path 指定路径
     * @param name 要被创建的文件名
     * @return 返回被创建的文件 ps：创建失败会返回null
     */
    public static File createFile(String path, String name) {

        File file = null;

        try {

            file = new File(path + File.separator + name);

            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("FileUtil createFile onError->" + e.fillInStackTrace());
        }

        return file;
    }

    /**
     * 在指定路径创建文件夹
     *
     * @param path 指定路径
     * @param name 文件夹的名字
     * @return 返回创建好的文件夹
     */
    public static File createDirectory(String path, String name) {

        File file = null;

        try {

            file = new File(path + File.separator + name);

            if (!file.exists()) {
                file.mkdirs();
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("FileUtil createDirectory onError->" + e.fillInStackTrace());
        }

        return file;
    }


    public static boolean writeToFile(File file, String text) {
        return writeToFile(file, text, true);
    }

    /**
     * 将字符串写入指定文件中去，统一使用utf-8编码格式
     *
     * @param file     目标文件
     * @param text     要写入的内容
     * @param isAppend true表示末尾添加，false表示覆盖
     * @return 写入流程走完就返回true，否则返回false
     */
    public static boolean writeToFile(File file, String text, boolean isAppend) {
        if (file == null || !file.exists())
            throw new IllegalArgumentException("writeToFile(File file,String text) The file is fault");

        Writer writer = null;

        try {

            writer = new OutputStreamWriter(new FileOutputStream(file, isAppend), "UTF-8");

            writer.write(text);
            writer.flush();

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("FileUtil writeToFile onError->" + e.fillInStackTrace());
            return false;
        }

        return true;
    }

    /**
     * 将文件中的数据读取出来，以 UTF-8 的编码格式读取
     *
     * @param file 指定文件
     * @return 返回读到的字符串
     */
    public static String readFile(File file) {
        if (file == null || !file.exists() || file.isDirectory())
            throw new IllegalArgumentException("readFile(File file) The file is fault");

        StringBuilder sb = new StringBuilder();
        Reader reader = null;

        try {

            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");

            int ch = reader.read();
            while (ch != -1) {
                sb.append((char) ch);
                ch = reader.read();
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("FileUtil readFile onError->" + e.fillInStackTrace());
        }

        return sb.toString();
    }

}
