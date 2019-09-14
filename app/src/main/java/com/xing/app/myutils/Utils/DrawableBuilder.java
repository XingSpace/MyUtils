package com.xing.app.myutils.Utils;

import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;

public class DrawableBuilder {

    private GradientDrawable.Orientation orientation = GradientDrawable.Orientation.TOP_BOTTOM;
    private int type = GradientDrawable.LINEAR_GRADIENT;
    private int shape = GradientDrawable.RECTANGLE;
    private int[] colors;
    private int color = 0xffffffff;
    private float radius = 0f;
    private float[] radii;
    private int alpha = 0xff;// 0x00~0xff
    private Rect rect;

    public GradientDrawable createGradient(){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setAlpha(alpha);
        if (radii == null) {
            gradientDrawable.setCornerRadius(radius);
        } else {
            gradientDrawable.setCornerRadii(radii);
        }
        if (colors == null){
            gradientDrawable.setColor(color);
        }else {
            gradientDrawable.setColors(colors);
        }
        gradientDrawable.setGradientType(type);
        gradientDrawable.setOrientation(orientation);
        gradientDrawable.setShape(shape);
        if (rect != null){
            gradientDrawable.setBounds(rect);
        }
        return gradientDrawable;
    }

    public static LayerDrawable createLayer(GradientDrawable...gradientDrawables){
        return new LayerDrawable(gradientDrawables);
    }

    public DrawableBuilder setAlpha(int alpha){
        this.alpha = alpha;
        return this;
    }

    public DrawableBuilder setRadii(float[] radii){
        this.radii = radii;
        return this;
    }

    public DrawableBuilder setRadius(float radius){
        this.radius = radius;
        return this;
    }

    public DrawableBuilder setOrientation(GradientDrawable.Orientation orientation){
        this.orientation = orientation;
        return this;
    }

    public DrawableBuilder setColor(int color){
        this.color = color;
        return this;
    }

    public DrawableBuilder setColors(int[] colors){
        this.colors = colors;
        return this;
    }

    public DrawableBuilder setShape(int shape){
        this.shape = shape;
        return this;
    }

    public DrawableBuilder setGradientType(int type){
        this.type = type;
        return this;
    }

    public DrawableBuilder setRect(Rect rect){
        this.rect = rect;
        return this;
    }

}
