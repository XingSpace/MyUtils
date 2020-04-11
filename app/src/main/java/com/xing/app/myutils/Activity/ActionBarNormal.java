package com.xing.app.myutils.Activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xing.app.myutils.R;

public class ActionBarNormal extends ActionBar {

    private View root;
    private Button back,setting;
    private TextView title;

    public ActionBarNormal(Context context) {
        this(context,null);
    }

    public ActionBarNormal(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ActionBarNormal(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        root = View.inflate(mContext, R.layout.actionbar_normal, this);

        back = root.findViewById(R.id.back_btn);
        setting = root.findViewById(R.id.setting_btn);
        title = root.findViewById(R.id.title);

        back.setBackground(mContext.getResources().getDrawable(R.drawable.ic_back));
    }

    @Override
    public void setOnBackClickListener(OnClickListener onClickListener) {
        back.setOnClickListener(onClickListener);
    }

    @Override
    public void setOnSettingClickListener(OnClickListener onClickListener) {
        setting.setOnClickListener(onClickListener);
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        title.setText(charSequence);
    }

    @Override
    public void setTitleColor(int color) {
        title.setTextColor(color);
    }

    @Override
    public void setTitleFontSize(float size) {
        title.setTextSize(size);
    }

    @Override
    public void setBackground(int color) {
        root.setBackgroundColor(color);
    }

    @Override
    public void setBackground(Drawable drawable) {
        root.setBackground(drawable);
    }

    @Override
    public void setBackBtnBg(Drawable drawable) {
        back.setBackground(drawable);
    }

    @Override
    public void setSettingBtnBg(Drawable drawable) {
        setting.setBackground(drawable);
    }

    @Override
    public void release() {
        super.release();
        root.setBackground(null);
        back.setBackground(null);
        setting.setBackground(null);
        System.gc();
    }
}
