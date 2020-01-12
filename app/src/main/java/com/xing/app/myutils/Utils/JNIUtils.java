package com.xing.app.myutils.Utils;

public class JNIUtils {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * 主要用于图像数组处理，会把图片变成灰色的
     * @param arr 图像数组，例如通过Bitmap.getPixels()获取的数组
     */
    public static native void gray(int[] arr);

    /**
     * 主要用于图像数组处理，会把图片变成灰色的
     * @param arr 图像数组，例如通过Bitmap.getPixels()获取的数组
     * @param alpha 1~0之间，1表示完全透明，0表示完全不透明
     */
    public static native void setAlpha(int[] arr , float alpha);

}
