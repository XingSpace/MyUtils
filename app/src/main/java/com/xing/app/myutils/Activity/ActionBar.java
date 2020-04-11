package com.xing.app.myutils.Activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

/**
 * 一个ActionBar所需要基本功能
 */
public abstract class ActionBar extends FrameLayout {

    protected Context mContext;

    public ActionBar(Context context) {
        super(context);
        mContext = context;
    }

    public ActionBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ActionBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public abstract void setOnBackClickListener(View.OnClickListener onClickListener);

    public abstract void setOnSettingClickListener(View.OnClickListener onClickListener);

    public abstract void setTitle(CharSequence charSequence);

    public abstract void setTitleColor(int color);

    public abstract void setTitleFontSize(float size);

    public abstract void setBackground(int color);

    public abstract void setBackground(Drawable drawable);

    /**
     * 设置返回键的基础样式
     */
    public abstract void setBackBtnBg(Drawable drawable);

    /**
     * 设置返回键的基础样式
     */
    public abstract void setSettingBtnBg(Drawable drawable);

    public void release(){

    }

}
