package com.xing.app.myutils.Utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableBuilder {

    private GradientDrawable gradientDrawable;

    public DrawableBuilder(){
        gradientDrawable = new GradientDrawable();
    }

    public GradientDrawable create(){
        return gradientDrawable;
    }

    /**
     * @param radius 设置圆角大小
     */
    public DrawableBuilder setRadius(float radius){
        gradientDrawable.setCornerRadius(radius);
        return this;
    }

    /**
     * @param color 设置单色背景
     */
    public DrawableBuilder setColor(int color){
        gradientDrawable.setColor(color);
        return this;
    }

    /**
     * @param color 设置渐变色
     */
    public DrawableBuilder setColor(int[] color){
        gradientDrawable.setColors(color);
        return this;
    }

    /**
     * @param orientation 设置渐变方式
     */
    public DrawableBuilder setOrientation(GradientDrawable.Orientation orientation){
        gradientDrawable.setOrientation(orientation);
        return this;
    }

    /**
     * @param shape 设置背景形状
     */
    public DrawableBuilder setShape(int shape){
        gradientDrawable.setShape(shape);
        return this;
    }

    /**
     * @param width 设置描边的粗细
     * @param color 设置描边色彩
     */
    public DrawableBuilder setStroke(int width,int color){
        gradientDrawable.setStroke(width,color);
        return this;
    }

    /**
     * 创建一个 响应点击效果的 Drawable
     * @param down 按下时的Drawable
     * @param up 抬起时的Drawable
     */
    public static StateListDrawable createStateDrawable(Drawable down,Drawable up){
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed},down);
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed},up);
        return stateListDrawable;
    }

    /**
     * 创建一个 响应点击效果的 Drawable
     * @param down 按下时的Bitmap
     * @param up 抬起时的Bitmap
     */
    public static StateListDrawable createStateDrawable(Bitmap down, Bitmap up){
        return createStateDrawable(BitmapUtil.bitmapToDrawable(down),BitmapUtil.bitmapToDrawable(up));
    }

    /**
     * 创建一个LayerDrawable (下层覆盖上层)
     */
    public static LayerDrawable createLayer(Drawable...drawables){
        return new LayerDrawable(drawables);
    }

}
