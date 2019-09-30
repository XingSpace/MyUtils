package com.xing.app.myutils.Utils;

import android.content.Context;
import android.util.AttributeSet;

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

}
