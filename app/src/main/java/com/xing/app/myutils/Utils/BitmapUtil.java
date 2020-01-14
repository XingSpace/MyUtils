package com.xing.app.myutils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Base64;

import com.xing.app.myutils.Views.RoundImageView.RoundedDrawable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitmapUtil {

    private BitmapUtil() {}

    /**
     * 本方法调用的是androidx提供的API，目前发现效果还可以，但是如果将其转换为bitmap，就会出现变形
     * @return 返回一个圆角的Drawable
     */
    public static RoundedDrawable getRoundedBitmapDrawable(Bitmap bitmap, float roundPx) {
        RoundedDrawable drawable = RoundedDrawable.fromBitmap(bitmap);
        drawable.setCornerRadius(roundPx);
        return drawable;
    }

    /**
     * 高斯模糊实现
     * @param image 需要被高斯处理的bitmap，处理之后会被回收
     * @param radius 模糊半径，半径越大越模糊(0 < radius <= 25)
     * @return 被处理过的bitmap
     */
    public static Bitmap gaussBlur(Context context, Bitmap image, float radius) {
        if (radius > 25f || radius < 0f) throw new IllegalArgumentException("The radius must be (0 < radius <= 25)");
        RenderScript rs = RenderScript.create(context);
        Bitmap outputBitmap = Bitmap.createBitmap(image.getHeight(), image.getWidth(), Bitmap.Config.ARGB_8888);
        Allocation in = Allocation.createFromBitmap(rs, image);
        Allocation out = Allocation.createFromBitmap(rs, outputBitmap);

        ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        intrinsicBlur.setRadius(radius);
        intrinsicBlur.setInput(in);
        intrinsicBlur.forEach(out);

        out.copyTo(outputBitmap);
        image.recycle();
        rs.destroy();
        return outputBitmap;
    }

    /**
     * 将图片变为黑白
     * @param bitmap 用完之后会被回收
     */
    public static Bitmap gray(Bitmap bitmap){
        int[] arr = new int[bitmap.getWidth()*bitmap.getHeight()];
        bitmap.getPixels(arr,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        JNIUtils.gray(arr);
        Bitmap outputBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        outputBitmap.setPixels(arr,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        bitmap.recycle();
        return outputBitmap;
    }

    /**
     * 设置图片透明度
     * @param bitmap 使用会被释放掉
     * @param alpha 透明度（0 =< alpha =< 1）
     */
    public static Bitmap setAlpha(Bitmap bitmap,float alpha){
        if (alpha > 1f || alpha < 0f) throw new IllegalArgumentException("The alpha must be (0 =< alpha =< 1)");
        Bitmap outputBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        int[] arr = new int[bitmap.getWidth()*bitmap.getHeight()];
        bitmap.getPixels(arr,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());

        JNIUtils.setAlpha(arr,alpha);
        outputBitmap.setPixels(arr,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        bitmap.recycle();
        return outputBitmap;
    }

    /**
     * 将Bitmap转换为Drawable
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap){
        return new BitmapDrawable(bitmap);
    }

    /**
     * 将Drawable转换为Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable){
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 将Base64字符串转换为Bitmap
     */
    public static Bitmap base64ToBitmap(String base64) {

        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(base64.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 将Bitmap转换为Base64字符串
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        // 要返回的字符串
        String reslut = null;
        ByteArrayOutputStream baos = null;

        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                // 压缩只对保存有效果bitmap还是原来的大小
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                baos.flush();
                baos.close();
                // 转换为字节数组
                byte[] byteArray = baos.toByteArray();

                // 转换为字符串
                reslut = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return reslut;
    }

    /**
     * 回收图片所占内存
     */
    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
            System.gc(); // 提醒系统及时回收
        }
    }

}
