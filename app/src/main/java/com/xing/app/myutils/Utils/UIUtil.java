package com.xing.app.myutils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.display.DisplayManager;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UIUtil {

    private UIUtil() {}

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * todo 未完成
     */
    public static int getLayoutWidthFormAttr(Context context,AttributeSet attributeSet){
        if (context == null || attributeSet == null) return 0;
        String widthStr = attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android","layout_width");

        return 0;
    }

    /**
     * @return 如果返回为1，表示当前设备只有一个显示屏，如果为2表示当前设备有两个屏，以此类推
     */
    public static int getDisplaysLength(Context context){
        return getDisplays(context).length;
    }

    /**
     * @param index 从0开始，需要第几个屏幕就输入几
     * @return 对应的屏幕对象
     */
    public static Display getDisplay(Context context,int index){
        return getDisplays(context)[index];
    }

    public static Display[] getDisplays(Context context){
        DisplayManager manager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        return manager.getDisplays();
    }

    /**
     * 获取屏幕尺寸
     * @return DisplayMetrics
     */
    public static DisplayMetrics getScreenSize(Context context) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        } else {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }

        return displayMetrics;
    }

    /**
     * Base64字符串 转 图片Bitmap
     * @param base64String Base64字符串
     * @return Bitmap
     */
    public static Bitmap base64ToBitmap(String base64String) {

        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(base64String.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 图片Bitmap 转 Base64字符串
     * @param bitmap Bitmap
     * @return Base64字符串
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        // 要返回的字符串
        String reslut = null;
        ByteArrayOutputStream baos = null;

        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                // 压缩只对保存有效果,bitmap还是原来的大小
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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
     * 用于对字符进行高亮处理
     *
     * @param color #ffffff 十六进制颜色格式
     * @return 所有的字母和数字都做特殊颜色处理
     */
    public static Spanned highLightingTextColor(String text, String color) {

        String fontS = "<font color=\"" + color + "\">";
        String fontE = "</font>";

        StringBuilder record = new StringBuilder();

        boolean b = false;

        for (int i = 0; i < text.length(); i++) {
            if (String.valueOf(text.charAt(i)).matches("[0-9a-zA-Z]")) {
                if (!b) {
                    b = true;
                    record.append(fontS);
                }
                record.append(text.charAt(i));
            } else {
                if (b) {
                    b = false;
                    record.append(fontE);
                }
                record.append(text.charAt(i));
            }
        }

        if (String.valueOf(record.charAt(record.length() - 1)).matches("[0-9a-zA-Z]")) {
            record.append(fontE);
        }

        return Html.fromHtml(record.toString());
    }

    /**
     * 等比例拉伸View，以及子View
     */
    public static void setScale(View view, float scale) {

        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            params.width = (int) (view.getWidth() * scale);
            params.height = (int) (view.getHeight() * scale);
            params.leftMargin = (int) (params.leftMargin * scale);
            params.rightMargin = (int) (params.rightMargin * scale);
            params.topMargin = (int) (params.topMargin * scale);
            params.bottomMargin = (int) (params.bottomMargin * scale);
            view.setLayoutParams(params);
        }

        if (view instanceof TextView) {
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, ((TextView) view).getTextSize() * scale);
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                setScale(((ViewGroup) view).getChildAt(i), scale);
            }
        }
    }

}
