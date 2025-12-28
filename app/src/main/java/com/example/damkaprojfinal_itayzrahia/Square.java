package com.example.damkaprojfinal_itayzrahia;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Square extends Shape
{
    private float w, h;
    private Paint p;

    public Square(float x, float y, float w, float h, int color)
    {
        super(x, y, color);
        this.w = w;
        this.h = h;
        this.p = new Paint();
        this.p.setColor(color);
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawRect(x, y, x + w, y + h, p);
    }
}