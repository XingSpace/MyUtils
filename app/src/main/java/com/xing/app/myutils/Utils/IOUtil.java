package com.xing.app.myutils.Utils;

import java.io.File;
import java.nio.charset.Charset;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public class IOUtil {

    /**
     * @param data 要被写入文件的数据
     * @param file 目标文件
     */
    public static void writeToFile(String data, File file){
        try {
            Sink sink = Okio.sink(file);
            BufferedSink bs = Okio.buffer(sink);
            bs.writeUtf8(data);
            bs.flush();
            bs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param file 要读取的文件
     * @return 以字符串的形式返回
     */
    public static String readFile(File file){
        try {
            Source source = Okio.source(file);
            BufferedSource buffer = Okio.buffer(source);
            return buffer.readString(Charset.forName("UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
