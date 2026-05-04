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
    public static int myTeam;
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

        if (mode.equals("playerbanana")) //taking the intent from the chosen team
        {
            myTeam = Coin.TEAM_BANANA;
        }
        else //now either the player is banana or strawberry
        {
            myTeam = Coin.TEAM_STRAWBERRY;
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#1A1A1A")); //the "black" background I set for every screen

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
        BOARD_STARTS_FROM = (h - (tileSize * 8)) / 2; //trying to have the board in the middle

        float x = 0;
        float y = BOARD_STARTS_FROM;

        for (int r = 0; r < 8; r++) //row=Vertical
        {
            for (int c = 0; c < 8; c++) //col=Horizontal
            {
                int color;
                if ((r + c) % 2 == 0)
                {
                    color = Color.parseColor("#ebd3ac"); //light square
                }
                else
                {
                    color = Color.parseColor("#a17e5d"); //dark square
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

        for (int r = 0; r < 8; r++) //row = Vertical (we go over the whole board)
        {
            for (int c = 0; c < 8; c++) //col = Horizontal
            {
                if ((r + c) % 2 != 0) //only place pieces on dark squares
                {
                    float coinFirstX = c * tileSize + tileSize / 2;
                    float coinFirstY = BOARD_STARTS_FROM + r * tileSize + tileSize / 2;

                    if (r < 3) //top of board (Strawberry Team Pieces)
                    {
                        coins.add(new Coin(coinFirstX, coinFirstY, radius, Color.parseColor("#ffc9d4"), Coin.TEAM_STRAWBERRY, r, c, "regularCoin"));
                    }
                    else if (r > 4) //bottom of board (Banana Team Pieces)
                    {
                        coins.add(new Coin(coinFirstX, coinFirstY, radius, Color.parseColor("#fddc5c"), Coin.TEAM_BANANA, r, c, "regularCoin"));
                    }
                }
            }
        }
    }

    private void drawBoard(Canvas canvas)
    {
        for (int r = 0; r < 8; r++) //row = Vertical
        {
            for (int c = 0; c < 8; c++) //col = Horizontal
            {
                squares[r][c].draw(canvas); //drawing 64 squares
            }
        }
    }

    private void drawCoins(Canvas canvas)
    {
        for (int i = 0; i < coins.size(); i++)
        {
            coins.get(i).draw(canvas); //drawing 24 coins
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) //when touching the board
    {
        float touchX = event.getX(); //x location of touch
        float touchY = event.getY(); //y location of touch

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            for (int i = 0; i < coins.size(); i++)
            {
                //for every coin, check if touch location matches its location
                Coin c = coins.get(i);
                if (c.didUserTouchMe(touchX, touchY))
                {
                    if (c.team == myTeam)
                    {
                        if (gameModule.isMyTurn(c.team))
                        {
                            activeCoin = c; //the chosen coin for this turn
                        }
                    }
                }
            }
        }

        //action of draggnig the coin
        //we follow the movement of the coin
        //until you let go of it
        if (event.getAction() == MotionEvent.ACTION_MOVE)
        {
            if (activeCoin != null)
            {
                activeCoin.x = touchX; //saving the location while moving
                activeCoin.y = touchY;
                invalidate();
            }
        }

        //when dropping the hold on the coin
        if (event.getAction() == MotionEvent.ACTION_UP)
        {

            if (activeCoin != null)
            {
                int targetR = (int) ((touchY - BOARD_STARTS_FROM) / tileSize);
                int targetC = (int) (touchX / tileSize);

                int moveResult = gameModule.checkMove(activeCoin, targetR, targetC, coins);

                if (moveResult == 0) //if move is ilegal, return to last location
                {
                    activeCoin.x = activeCoin.lastX;
                    activeCoin.y = activeCoin.lastY;
                }
                else //attemp to start setting a new location in firebase
                {
                    fb.setPositionInFirebase(new Position(activeCoin.row, activeCoin.col, targetR, targetC));
                }

                activeCoin = null; //turn is over, ready for a new coin to become active
                invalidate(); //draw again the new board
            }
        }
        return true;
    }

    //this command moves the coin in both sides of the screen
    //both androids check which coin has changed from firebase
    //they both change its position for both players
    public void moveCoin(Position p)
    {
        Coin coinToMove = null;

        //find the coin to move based on its location before movement from position (that is taken from the firebase)
        for (int i = 0; i < coins.size(); i++)
        {
            if (coins.get(i).row == p.getLastRow())
            {
                if (coins.get(i).col == p.getLastCol())
                {
                    coinToMove = coins.get(i);

                }
            }
        }

        //we check if the legal move was one where a coin is eaten
        //if a move was passed as "legal" although a coin took a two block step
        //it eats someone, so we need to figure out which coin to remove (for both players)
        int rowHefresh = p.getNewRow() - p.getLastRow();
        if (Math.abs(rowHefresh) == 2)
        {
            int rowMiddle = (p.getLastRow() + p.getNewRow()) / 2;
            int colMiddle = (p.getLastCol() + p.getNewCol()) / 2;

            for (int i = 0; i < coins.size(); i++)
            {
                if (coins.get(i).row == rowMiddle)
                {
                    if (coins.get(i).col == colMiddle)
                    {
                        animateAndRemoveCoin(coins.get(i));
                        //if a coin was in the middle of an "eat" move, it needs to be removed

                    }
                }
            }
        }

        //gives the coin that should move its new location for both players
        coinToMove.row = p.getNewRow();
        coinToMove.col = p.getNewCol();
        coinToMove.x = coinToMove.col * tileSize + tileSize / 2;
        coinToMove.y = BOARD_STARTS_FROM + coinToMove.row * tileSize + tileSize / 2;
        coinToMove.lastX = coinToMove.x;
        coinToMove.lastY = coinToMove.y;

        gameModule.switchTurn(); //turn is over
        invalidate(); //draw the board again so the coin moves to its new location

    }

    private void animateAndRemoveCoin(final Coin eatenCoin)
    {
        //this is the most complicated issue I have encountered in this project:
        //because we summon the command "animateAndRemoveCoin" inside "moveCoin" and-
        //"movecoin" is summoned by "setPositionFromFb" which also summons "checkWin"-
        // when the deleted coin starts to be animated
        //the new "thread" runs at the same time.
        //meaning the command "animate...." sort of ends
        //so "checkWin" activates while the deleted coin is still inside the arraylist
        //so the entire turn will not count as victory even if it's the last turn
        //and if we were to delete the coin before animating it, well you can't animate a deleted coin.
        //note to self: explain how u fixed it

        eatenCoin.ChangeTypeToEatenCoin();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                float targetY;
                float currentSpeed;

                if (eatenCoin.team == Coin.TEAM_BANANA) //if team Banana we want it to float up when eaten
                {
                    targetY = -200;
                    currentSpeed = -25;
                }
                else //if team Strawberry we want it to float down when eaten
                {
                    targetY = getHeight() + 200;
                    currentSpeed = 25;
                }

                while ((currentSpeed < 0 && eatenCoin.y > targetY) || (currentSpeed > 0 && eatenCoin.y < targetY))
                {
                    eatenCoin.y = eatenCoin.y + currentSpeed;
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

        }).start(); //the ")" is to close off the thread: "new Thread(new Runnable() .... ).start();"

    }

    public int isWin()
    {
        return gameModule.checkWinner(this.coins);
    }
}