package com.example.damkaprojfinal_itayzrahia;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Coin extends Shape
{
    public String type = "";
    public float radius;
    private Paint p;
    public float lastX, lastY;
    public int team;
    public int row, col;
    public static final int TEAM_STRAWBERRY = -1;
    public static final int TEAM_BANANA = 1;

    public Coin(float x, float y, float radius, int color, int team, int row, int col)
    {
        super(x, y, color);
        this.type = "Coin";
        this.radius = radius;
        this.team = team;
        this.row = row;
        this.col = col;
        this.lastX = x;
        this.lastY = y;
        this.p = new Paint();
        this.p.setColor(color);
    }

    public Coin(Coin other)
    {
        super(other.x, other.y, other.color);

        this.radius = other.radius;
        this.team = other.team;
        this.row = other.row;
        this.col = other.col;

        this.lastX = other.x;
        this.lastY = other.y;
        this.p = new Paint();
        this.p.setColor(other.color);
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x, y, radius, p);
    }

    public boolean didUserTouchMe(float xTouch, float yTouch)
    {
       double distance = Math.sqrt(Math.pow((x - xTouch), 2) + Math.pow((y - yTouch), 2));

        if (distance < radius)
        {
            return true;
        }

        return false;
    }
}