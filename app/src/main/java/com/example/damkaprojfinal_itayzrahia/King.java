package com.example.damkaprojfinal_itayzrahia;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class King extends Coin {
    private Paint p;
    private Paint borderPaint;

    public King(Coin other) {
        super(other);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(15);
        borderPaint.setColor(Color.parseColor("#FFD700")); // Gold

        if (this.team == TEAM_STRAWBERRY) {
            this.color = Color.parseColor("#ffc9d4"); // strawberry
        } else {
            this.color = Color.parseColor("#fddc5c"); // banana
        }

        this.type = "King";
        p.setColor(this.color);
    }

    @Override
    public void draw(Canvas canvas) {
        p.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, radius, p);
        canvas.drawCircle(x, y, radius, borderPaint);
    }
}