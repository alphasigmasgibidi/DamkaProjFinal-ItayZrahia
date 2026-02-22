package com.example.damkaprojfinal_itayzrahia;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

public class GameView extends View
{
    private Square[][] squares;
    private ArrayList<Coin> coins;
    private Coin activeCoin;
    private boolean isFirstTime = true;

    public static float tileSize;
    public static float BOARD_STARTS_FROM;

    private String mode;
    private int myTeam;
    private GameModule gameModule;
    private FbModule fb;

    public GameView(Context context, String mode, FbModule fb)
    {
        super(context);
        this.mode = mode;
        this.fb = fb;
        this.squares = new Square[8][8];
        this.coins = new ArrayList<Coin>();
        this.gameModule = new GameModule();

        if (mode.equals("playerwhite"))
        {
            myTeam = Coin.TEAM_WHITE;
        }
        else if (mode.equals("playerred"))
        {
            myTeam = Coin.TEAM_RED;
        }
        else
        {
            myTeam = Coin.TEAM_WHITE;
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#1A1A1A"));

        if (isFirstTime)
        {
            initBoard(canvas);
            initCoins();
            isFirstTime = false;
        }

        drawBoard(canvas);
        drawCoins(canvas);
    }

    private void initBoard(Canvas canvas)
    {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        tileSize = w / 8;
        BOARD_STARTS_FROM = (h - (tileSize * 8)) / 2;

        float x = 0;
        float y = BOARD_STARTS_FROM;

        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                int color;
                if ((r + c) % 2 == 0)
                {
                    color = Color.parseColor("#C0C0C0");
                }
                else
                {
                    color = Color.parseColor("#800000");
                }

                squares[r][c] = new Square(x, y, tileSize, tileSize, color);
                x = x + tileSize;
            }
            y = y + tileSize;
            x = 0;
        }
    }

    private void initCoins()
    {
        float radius = tileSize / 3;
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                if ((r + c) % 2 != 0)
                {
                    float cx = c * tileSize + tileSize / 2;
                    float cy = BOARD_STARTS_FROM + r * tileSize + tileSize / 2;

                    if (r < 3)
                    {
                        coins.add(new Coin(cx, cy, radius, Color.RED, Coin.TEAM_RED, r, c));
                    }
                    else if (r > 4)
                    {
                        coins.add(new Coin(cx, cy, radius, Color.WHITE, Coin.TEAM_WHITE, r, c));
                    }
                }
            }
        }
    }

    private void drawBoard(Canvas canvas)
    {
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                squares[r][c].draw(canvas);
            }
        }
    }

    private void drawCoins(Canvas canvas)
    {
        for (int i = 0; i < coins.size(); i++)
        {
            coins.get(i).draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float tx = event.getX();
        float ty = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            for (int i = 0; i < coins.size(); i++)
            {
                Coin c = coins.get(i);
                if (c.didUserTouchMe(tx, ty))
                {
                    if (c.team == myTeam)
                    {
                        if (gameModule.isMyTurn(c.team))
                        {
                            activeCoin = c;
                        }
                    }
                    break;
                }
            }
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE)
        {
            if (activeCoin != null)
            {
                activeCoin.x = tx;
                activeCoin.y = ty;
                invalidate();
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            if (activeCoin != null)
            {
                int targetR = (int) ((ty - BOARD_STARTS_FROM) / tileSize);
                int targetC = (int) (tx / tileSize);

                int moveResult = gameModule.checkMove(activeCoin, targetR, targetC, coins);

                if (moveResult == 0)
                {
                    activeCoin.x = activeCoin.lastX;
                    activeCoin.y = activeCoin.lastY;
                }
                else
                {
                    fb.setPositionInFirebase(new Position(activeCoin.row, activeCoin.col, targetR, targetC));
                }

                activeCoin = null;
                invalidate();
            }
        }
        return true;
    }

    public void moveCoin(Position p)
    {
        Coin coinToMove = null;

        for (int i = 0; i < coins.size(); i++)
        {
            if (coins.get(i).row == p.getLastRow())
            {
                if (coins.get(i).col == p.getLastCol())
                {
                    coinToMove = coins.get(i);
                    break;
                }
            }
        }

        if (coinToMove != null)
        {
            int dr = p.getNewRow() - p.getLastRow();
            int absDr = dr;
            if (absDr < 0)
            {
                absDr = -absDr;
            }

            if (absDr == 2)
            {
                int midR = (p.getLastRow() + p.getNewRow()) / 2;
                int midC = (p.getLastCol() + p.getNewCol()) / 2;

                for (int i = 0; i < coins.size(); i++)
                {
                    if (coins.get(i).row == midR)
                    {
                        if (coins.get(i).col == midC)
                        {
                            animateAndRemoveCoin(coins.get(i));
                            break;
                        }
                    }
                }
            }

            coinToMove.row = p.getNewRow();
            coinToMove.col = p.getNewCol();
            coinToMove.x = coinToMove.col * tileSize + tileSize / 2;
            coinToMove.y = BOARD_STARTS_FROM + coinToMove.row * tileSize + tileSize / 2;
            coinToMove.lastX = coinToMove.x;
            coinToMove.lastY = coinToMove.y;

            gameModule.switchTurn();
            invalidate();
        }
    }

    private void animateAndRemoveCoin(final Coin eatenCoin)
    {
        eatenCoin.row = -1;
        eatenCoin.col = -1;

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                final float speed = 25;
                float targetY;
                float currentSpeed;

                if (eatenCoin.team == Coin.TEAM_WHITE)
                {
                    targetY = -200;
                    currentSpeed = -speed;
                }
                else
                {
                    targetY = getHeight() + 200;
                    currentSpeed = speed;
                }

                while ((currentSpeed < 0 && eatenCoin.y > targetY) || (currentSpeed > 0 && eatenCoin.y < targetY))
                {
                    eatenCoin.y += currentSpeed;
                    postInvalidate();

                    try
                    {
                        Thread.sleep(20);
                    }
                    catch (InterruptedException e)
                    {
                    }
                }

                coins.remove(eatenCoin);
                postInvalidate();
            }
        }).start();
    }
}