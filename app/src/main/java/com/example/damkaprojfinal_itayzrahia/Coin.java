package com.example.damkaprojfinal_itayzrahia;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Coin extends Shape {
    public float r;
    private Paint p;
    public float lastX, lastY;
    public int team; // 1 = White, -1 = Red
    public int row, col; // Logical position on grid

    // Constants for teams
    public static final int TEAM_RED = -1;
    public static final int TEAM_WHITE = 1;

    public Coin(float x, float y, float r, int color, int team, int row, int col) {
        super(x, y, color);
        this.r = r;
        this.team = team;
        this.row = row;
        this.col = col;

        lastX = x;
        lastY = y;
        p = new Paint();
        p.setColor(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, r, p);
    }

    public boolean didUserTouchMe(float xu, float yu) {
        if (Math.sqrt(Math.pow((x - xu), 2) + Math.pow((y - yu), 2)) < r)
            return true;
        return false;
    }
}