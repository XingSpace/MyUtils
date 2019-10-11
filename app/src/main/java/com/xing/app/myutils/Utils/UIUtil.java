package com.xing.app.myutils.Utils;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.AttributeSet;
import android.view.Display;

public class UIUtil {

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
     * 未完成
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

}
