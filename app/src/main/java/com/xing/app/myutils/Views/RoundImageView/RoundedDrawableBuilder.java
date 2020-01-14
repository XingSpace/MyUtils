package com.xing.app.myutils.Views.RoundImageView;

import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.xing.app.myutils.Utils.BitmapUtil;

/**
 * 生成圆角Drawable的Builder
 */
public class RoundedDrawableBuilder {

    private RoundedDrawable mDrawable;

    public RoundedDrawableBuilder(Bitmap bitmap){
        mDrawable = RoundedDrawable.fromBitmap(bitmap);
    }

    public RoundedDrawableBuilder(Drawable drawable){
        this(BitmapUtil.drawableToBitmap(drawable));
    }

    public RoundedDrawable create(){
        return mDrawable;
    }

    /**
     * 设置圆角角度
     * 与 {@link RoundedDrawableBuilder#setRadius(int, float)} 方法互斥，只能选择其中之一
     */
    public RoundedDrawableBuilder setRadius(float radius){
        mDrawable.setCornerRadius(radius);
        return this;
    }

    /**
     * 设置特定角的圆角角度
     * 与 {@link RoundedDrawableBuilder#setRadius(float)} 方法互斥，只能选择其中之一
     * {@see }
     */
    public RoundedDrawableBuilder setRadius(int corner,float radius){
        mDrawable.setCornerRadius(corner,radius);
        return this;
    }

    /**
     * 设置边框颜色
     * @param color 0xffffffff
     */
    public RoundedDrawableBuilder setBorderColor(int color){
        mDrawable.setBorderColor(color);
        return this;
    }

    /**
     * @param width 边框宽度
     */
    public RoundedDrawableBuilder setBorderWidth(float width){
        mDrawable.setBorderWidth(width);
        return this;
    }

    /**
     * @param b 是否椭圆
     */
    public RoundedDrawableBuilder setOval(boolean b){
        mDrawable.setOval(b);
        return this;
    }

    /**
     * @param scaleType 同ImageView的scaleType
     */
    public RoundedDrawableBuilder setScaleType(ImageView.ScaleType scaleType){
        mDrawable.setScaleType(scaleType);
        return this;
    }

    /**
     * @param b 是否防抖动
     */
    public RoundedDrawableBuilder setDither(boolean b){
        mDrawable.setDither(b);
        return this;
    }

    /**
     * @param b 是否设置bitmap滤波
     */
    public RoundedDrawableBuilder setFilterBitmap(boolean b){
        mDrawable.setFilterBitmap(b);
        return this;
    }

    /**
     * @param colorFilter 设置颜色过滤矩阵
     */
    public RoundedDrawableBuilder setColorFilter(ColorFilter colorFilter){
        mDrawable.setColorFilter(colorFilter);
        return this;
    }

}
