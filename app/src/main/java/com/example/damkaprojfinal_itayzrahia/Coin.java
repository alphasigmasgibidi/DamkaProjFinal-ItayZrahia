package com.example.damkaprojfinal_itayzrahia;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Coin extends Shape
{
    public float radius;
    private Paint p;
    public float lastX, lastY;
    public int team;
    public int row, col;
    public static final int TEAM_RED = -1;
    public static final int TEAM_WHITE = 1;

    public Coin(float x, float y, float radius, int color, int team, int row, int col)
    {
        super(x, y, color);
        this.radius = radius;
        this.team = team;
        this.row = row;
        this.col = col;

        this.lastX = x;
        this.lastY = y;
        this.p = new Paint();
        this.p.setColor(color);
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x, y, radius, p);
    }

    public boolean didUserTouchMe(float xu, float yu)
    {
        double distance = Math.sqrt(Math.pow((x - xu), 2) + Math.pow((y - yu), 2));

        if (distance < radius)
        {
            return true;
        }

        return false;
    }
}