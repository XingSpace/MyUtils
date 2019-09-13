package com.xing.app.myutils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    //超时时间
    public static int TIME_OUT = 30;

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build();

    //默认utf-8方式编码传输
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /**
     * 本方法也可以用作普通的请求接口
     * @param url 请求地址
     * @param map 上传时需要被附带的参数集合（可以为空）
     * @param file 需要被上传的文件（可以为空）
     * @param fileKey 上传文件的key值（不上传文件的话，可以为空）
     * @return Response对象
     */
    public static Response uploadFile(String url, Map<String, String> map, File file ,String fileKey) {

        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

        // 上传数据整理
        if (file != null) {
            String fileName = file.getName();
            RequestBody body = RequestBody.create(MediaType.parse(getMimeType(fileName)), file);
            requestBody.addFormDataPart(fileKey, fileName, body);
        }
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                requestBody.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        // 网络超时5秒
        Request request = new Request.Builder().url(url).post(requestBody.build()).build();

        // 同步上传
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * @param sourcePath 要请求的资源路径
     * @param distPath 保存至本地资源路径
     * @param fileName 保存至本地的文件名
     * @param isForce 是否覆盖本地，如果不覆盖，则直接使用原来的素材
     * @return 返回0则下载成功
     */
    public static int downloadFile(String sourcePath, String distPath, String fileName, boolean isForce) {
        File newFile = new File(distPath + "/" + fileName);

        if (newFile.exists()) {
            if (!isForce) { // 如果不覆盖，则直接使用原来的素材
                return 2;
            } else {
                newFile.delete();
            }
        }

        File tmpfile = new File(distPath + "/" + fileName + ".tmp");
        if (tmpfile.exists()) {
            tmpfile.delete();
        }

        Request request = new Request.Builder().url(sourcePath).build();
        try {
            Response response = okHttpClient.newCall(request).execute();

            if (response == null || !response.isSuccessful()) {
                // 当请求链接不存在，则记录该资源并跳过
                return -1;
            }

            InputStream is = null;
            byte[] buf = new byte[1024 * 10];
            int len = 0;
            FileOutputStream fos = null;
            try {
                is = response.body().byteStream();
                fos = new FileOutputStream(tmpfile, true);
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                fos.flush();

                // 重命名
                tmpfile.renameTo(newFile);
                return 0;

            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            } finally {
                try {
                    if (is != null)
                        is.close();
                    if (fos != null)
                        fos.close();
                    if (response != null)
                        response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取传输文件所属媒体类型
     * @param fileName  文件名称
     * @return  文件所属媒体类型
     */
    private static String getMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(fileName);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 用utf-8编码
     * @param str  参数字符串
     * @return  编码结果串
     */
    public static String encode(String str) {
        if (str == null)
            return "";

        try {
            str = java.net.URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 默认用utf-8解码
     * @param str  参数字符串
     * @return  解码结果串
     */
    public static String decode(String str) {
        if (str == null)
            return "";

        try {
            str = java.net.URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
