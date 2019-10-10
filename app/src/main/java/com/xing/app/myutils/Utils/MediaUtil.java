package com.xing.app.myutils.Utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 视频媒体资源工具类
 */
public class MediaUtil {


    private static MediaUtil sMediaUtils;
    private MediaMetadataRetriever retriever;
    private String fileLength;

    private MediaUtil() {

    }

    public static MediaUtil getInstance() {
        if (sMediaUtils == null) {
            sMediaUtils = new MediaUtil();
        }
        return sMediaUtils;
    }

    /**
     * @param filePath 设置视频资源路径
     */
    public void setSource(String filePath) {
        retriever = new MediaMetadataRetriever();

        try {
            FileInputStream inputStream = new FileInputStream(new File(filePath).getAbsolutePath());
            retriever.setDataSource(inputStream.getFD());
            fileLength = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param timeMs 指定某一时间
     * @return 将某一时间的关键帧转换为Bitmap并返回
     */
    public Bitmap decodeFrame(long timeMs) {
        if (retriever == null) {
            return null;
        }
        Bitmap bitmap = retriever.getFrameAtTime(timeMs * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        retriever.release();
        return bitmap;
    }

    /**
     * @return 返回视频资源的长度，单位为毫秒
     */
    public long getFileLength() {
        return Long.parseLong(fileLength);
    }

}
