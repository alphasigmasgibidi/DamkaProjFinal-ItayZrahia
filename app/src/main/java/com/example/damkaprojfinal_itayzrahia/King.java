package com.example.damkaprojfinal_itayzrahia;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class King extends Coin{


    private Paint p;
    public King(Coin other) {
        super(other);

        p = new Paint();

        if (this.team == TEAM_RED)
        {
            this.color = Color.parseColor("#8bd4e0");
        }
        else
        {
            this.color = Color.parseColor("#e6b941");
        }

        this.type = "King"; //we name them so we can identify them


        p.setColor(color);

    }

    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x, y, radius, p);
    }
}
