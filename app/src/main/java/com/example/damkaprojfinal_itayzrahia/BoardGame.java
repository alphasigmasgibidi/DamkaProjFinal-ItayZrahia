package com.example.damkaprojfinal_itayzrahia;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

public class BoardGame extends View {
    private Square[][] squares;
    private ArrayList<Coin> coins;
    private Coin activeCoin;
    private boolean firstTime = true;
    private final int NUM_OF_SQUARES = 8;
    private float tileSize;
    private float BOARD_STARTS_FROM;
    private boolean isWhiteTurn = true;
    private String mode;

    public BoardGame(Context context, String mode) {
        super(context);
        this.mode = mode;
        squares = new Square[NUM_OF_SQUARES][NUM_OF_SQUARES];
        coins = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#1A1A1A"));

        if (firstTime) {
            initBoard(canvas);
            initCoins();
            firstTime = false;
        }

        drawBoard(canvas);
        drawCoins(canvas);
    }

    private void initBoard(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        tileSize = width / NUM_OF_SQUARES;
        float boardHeight = tileSize * NUM_OF_SQUARES;
        BOARD_STARTS_FROM = (height - boardHeight) / 2;

        float x = 0;
        float y = BOARD_STARTS_FROM;

        for (int row = 0; row < NUM_OF_SQUARES; row++) {
            for (int col = 0; col < NUM_OF_SQUARES; col++) {
                int color = ((row + col) % 2 == 0) ? Color.parseColor("#C0C0C0") : Color.parseColor("#800000");
                squares[row][col] = new Square(x, y, tileSize, tileSize, color);
                x += tileSize;
            }
            y += tileSize;
            x = 0;
        }
    }

    private void initCoins() {
        float r = tileSize / 3;
        for (int row = 0; row < NUM_OF_SQUARES; row++) {
            for (int col = 0; col < NUM_OF_SQUARES; col++) {
                if ((row + col) % 2 != 0) {
                    float cx = col * tileSize + tileSize / 2;
                    float cy = BOARD_STARTS_FROM + row * tileSize + tileSize / 2;
                    if (row < 3) {
                        coins.add(new Coin(cx, cy, r, Color.parseColor("#FF0000"), Coin.TEAM_RED, row, col));
                    } else if (row > 4) {
                        coins.add(new Coin(cx, cy, r, Color.parseColor("#E0E0E0"), Coin.TEAM_WHITE, row, col));
                    }
                }
            }
        }
    }

    private void drawBoard(Canvas canvas) {
        for (int i = 0; i < NUM_OF_SQUARES; i++) {
            for (int j = 0; j < NUM_OF_SQUARES; j++) {
                squares[i][j].draw(canvas);
            }
        }
    }

    private void drawCoins(Canvas canvas) {
        for (Coin c : coins) {
            c.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (Coin c : coins) {
                    if (c.didUserTouchMe(x, y)) {
                        if ((isWhiteTurn && c.team == Coin.TEAM_WHITE) || (!isWhiteTurn && c.team == Coin.TEAM_RED)) {
                            activeCoin = c;
                        }
                        break;
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (activeCoin != null) {
                    activeCoin.x = x;
                    activeCoin.y = y;
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                if (activeCoin != null) {
                    handleCoinRelease(x, y);
                    activeCoin = null;
                    invalidate();
                }
                break;
        }
        return true;
    }

    private void handleCoinRelease(float x, float y) {
        int newCol = (int) (x / tileSize);
        int newRow = (int) ((y - BOARD_STARTS_FROM) / tileSize);

        if (newRow < 0 || newRow >= NUM_OF_SQUARES || newCol < 0 || newCol >= NUM_OF_SQUARES || (newRow + newCol) % 2 == 0 || getSquareContent(newRow, newCol) != null) {
            resetCoinPosition();
            return;
        }

        int dRow = newRow - activeCoin.row;
        int dCol = newCol - activeCoin.col;
        boolean directionCorrect = (activeCoin.team == Coin.TEAM_WHITE && dRow < 0) || (activeCoin.team == Coin.TEAM_RED && dRow > 0);

        if (!directionCorrect) {
            resetCoinPosition();
            return;
        }

        if (Math.abs(dRow) == 1 && Math.abs(dCol) == 1) {
            moveCoin(newRow, newCol);
            switchTurn();
        } else if (Math.abs(dRow) == 2 && Math.abs(dCol) == 2) {
            int midRow = activeCoin.row + dRow / 2;
            int midCol = activeCoin.col + dCol / 2;
            Coin eaten = getSquareContent(midRow, midCol);
            if (eaten != null && eaten.team != activeCoin.team) {
                moveCoin(newRow, newCol);
                animateAndRemoveCoin(eaten);
                switchTurn();
            } else {
                resetCoinPosition();
            }
        } else {
            resetCoinPosition();
        }
    }

    private void moveCoin(int r, int c) {
        activeCoin.row = r;
        activeCoin.col = c;
        activeCoin.x = c * tileSize + tileSize / 2;
        activeCoin.y = BOARD_STARTS_FROM + r * tileSize + tileSize / 2;
        activeCoin.lastX = activeCoin.x;
        activeCoin.lastY = activeCoin.y;
    }

    private void resetCoinPosition() {
        activeCoin.x = activeCoin.lastX;
        activeCoin.y = activeCoin.lastY;
    }

    private Coin getSquareContent(int r, int c) {
        for (Coin coin : coins) {
            if (coin != activeCoin && coin.row == r && coin.col == c) {
                return coin;
            }
        }
        return null;
    }

    private void switchTurn() {
        isWhiteTurn = !isWhiteTurn;
    }

    private void animateAndRemoveCoin(final Coin eatenCoin) {
        eatenCoin.row = -1;
        eatenCoin.col = -1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final float speed = 25;
                float targetY = isWhiteTurn ? getHeight() + 100 : -100;
                float currentSpeed = isWhiteTurn ? -speed : speed;

                while ((isWhiteTurn && eatenCoin.y < targetY) || (!isWhiteTurn && eatenCoin.y > targetY)) {
                    eatenCoin.y += currentSpeed;
                    postInvalidate();
                    try { Thread.sleep(20); } catch (InterruptedException e) {}
                }
                coins.remove(eatenCoin);
            }
        }).start();
    }
}