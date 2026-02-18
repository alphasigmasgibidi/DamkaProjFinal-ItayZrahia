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

    public static float tileSize;
    public static float BOARD_STARTS_FROM;

    private String mode;
    private int myTeam; // מי אני? אדום או לבן
    private GameModule gameModule;
    private FbModule fb;

    public BoardGame(Context context, String mode, FbModule fb) {
        super(context);
        this.mode = mode;
        this.fb = fb;
        this.squares = new Square[8][8];
        this.coins = new ArrayList<Coin>();
        this.gameModule = new GameModule();

        // קביעת הצוות לפי המוד מה-Intent
        if (mode.equals("playerwhite")) {
            myTeam = Coin.TEAM_WHITE;
        } else if (mode.equals("playerred")) {
            myTeam = Coin.TEAM_RED;
        } else {
            myTeam = Coin.TEAM_WHITE; // ברירת מחדל ל-AI או אימון
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#1A1A1A"));

        if (firstTime == true) {
            initBoard(canvas);
            initCoins();
            firstTime = false;
        }

        drawBoard(canvas);
        drawCoins(canvas);
    }

    private void initBoard(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        tileSize = w / 8;
        BOARD_STARTS_FROM = (h - (tileSize * 8)) / 2;

        float x = 0;
        float y = BOARD_STARTS_FROM;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int color;
                if ((i + j) % 2 == 0) {
                    color = Color.parseColor("#C0C0C0");
                } else {
                    color = Color.parseColor("#800000");
                }
                squares[i][j] = new Square(x, y, tileSize, tileSize, color);
                x = x + tileSize;
            }
            y = y + tileSize;
            x = 0;
        }
    }

    private void initCoins() {
        float r = tileSize / 3;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 != 0) {
                    float cx = col * tileSize + tileSize / 2;
                    float cy = BOARD_STARTS_FROM + row * tileSize + tileSize / 2;
                    if (row < 3) {
                        coins.add(new Coin(cx, cy, r, Color.RED, Coin.TEAM_RED, row, col));
                    } else if (row > 4) {
                        coins.add(new Coin(cx, cy, r, Color.WHITE, Coin.TEAM_WHITE, row, col));
                    }
                }
            }
        }
    }

    private void drawBoard(Canvas canvas) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j].draw(canvas);
            }
        }
    }

    private void drawCoins(Canvas canvas) {
        for (int i = 0; i < coins.size(); i++) {
            coins.get(i).draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float tx = event.getX();
        float ty = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < coins.size(); i++) {
                Coin c = coins.get(i);
                if (c.didUserTouchMe(tx, ty) == true) {
                    // בדיקה: האם זה המטבע שלי והאם זה התור שלי?
                    if (c.team == myTeam && gameModule.isTurnCorrect(c.team) == true) {
                        activeCoin = c;
                    }
                    break;
                }
            }
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (activeCoin != null) {
                activeCoin.x = tx;
                activeCoin.y = ty;
                invalidate();
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (activeCoin != null) {
                int targetRow = (int) ((ty - BOARD_STARTS_FROM) / tileSize);
                int targetCol = (int) (tx / tileSize);

                int moveType = gameModule.checkMove(activeCoin, targetRow, targetCol, coins);

                if (moveType == 0) {
                    // מהלך לא חוקי - חזור למקום
                    activeCoin.x = activeCoin.lastX;
                    activeCoin.y = activeCoin.lastY;
                } else {
                    // מהלך חוקי! נעדכן את הפיירבייס והוא כבר יזיז לשנינו
                    Position p = new Position(activeCoin.row, activeCoin.col, targetRow, targetCol);
                    fb.setPositionInFirebase(p);
                }
                activeCoin = null;
                invalidate();
            }
        }
        return true;
    }

    // הפעולה שקורית כשיש שינוי בפיירבייס
    public void moveCoin(Position p) {
        Coin coinToMove = null;

        // מחפשים את המטבע שהיה במיקום הישן
        for (int i = 0; i < coins.size(); i++) {
            if (coins.get(i).row == p.getLastRow() && coins.get(i).col == p.getLastCol()) {
                coinToMove = coins.get(i);
                break;
            }
        }

        if (coinToMove != null) {
            // בודקים אם הייתה אכילה (קפיצה של 2 שורות)
            int rowDiff = p.getNewRow() - p.getLastRow();
            if (rowDiff == 2 || rowDiff == -2) {
                int midR = (p.getLastRow() + p.getNewRow()) / 2;
                int midC = (p.getLastCol() + p.getNewCol()) / 2;

                for (int i = 0; i < coins.size(); i++) {
                    if (coins.get(i).row == midR && coins.get(i).col == midC) {
                        coins.remove(i);
                        // TODO: 18/02/2026 add animation - Israel 
                        break;
                    }
                }
            }

            // עדכון המיקום הלוגי והויזואלי
            coinToMove.row = p.getNewRow();
            coinToMove.col = p.getNewCol();
            coinToMove.x = coinToMove.col * tileSize + tileSize / 2;
            coinToMove.y = BOARD_STARTS_FROM + coinToMove.row * tileSize + tileSize / 2;
            coinToMove.lastX = coinToMove.x;
            coinToMove.lastY = coinToMove.y;

            // החלפת תור ורענון מסך
            gameModule.toggleTurn();
            invalidate();
        }
    }
}