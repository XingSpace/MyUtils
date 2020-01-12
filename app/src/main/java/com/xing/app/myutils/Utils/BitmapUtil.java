package com.xing.app.myutils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class BitmapUtil {

    private BitmapUtil() {}

    /**
     * @return 返回一个圆角的bitmap
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        if (bitmap == null || roundPx < 0) return null;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        //设置图像与图形混合模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
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
}
