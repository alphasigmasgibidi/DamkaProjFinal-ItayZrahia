package com.example.damkaprojfinal_itayzrahia;

import java.util.ArrayList;

public class GameModule {
    public int currentTurn = Coin.TEAM_WHITE;

    public boolean isTurnCorrect(int team) {
        return (team == currentTurn);
    }

    public void toggleTurn() {
        if (currentTurn == Coin.TEAM_WHITE) {
            currentTurn = Coin.TEAM_RED;
        } else {
            currentTurn = Coin.TEAM_WHITE;
        }
    }

    public int checkMove(Coin c, int r2, int c2, ArrayList<Coin> all) {
        if (r2 < 0 || r2 > 7 || c2 < 0 || c2 > 7) return 0;
        if ((r2 + c2) % 2 == 0) return 0; // רק משבצות כהות

        // בדיקה אם המקום תפוס
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).row == r2 && all.get(i).col == c2) return 0;
        }

        int r1 = c.row;
        int c1 = c.col;
        int dr = r2 - r1;
        int dc = c2 - c1;

        // כיוון תנועה
        if (c.team == Coin.TEAM_WHITE && dr >= 0) return 0;
        if (c.team == Coin.TEAM_RED && dr <= 0) return 0;

        int absR = dr; if (absR < 0) absR = -absR;
        int absC = dc; if (absC < 0) absC = -absC;

        if (absR == 1 && absC == 1) return 1; // מהלך רגיל

        if (absR == 2 && absC == 2) { // קפיצה (אכילה)
            int midR = (r1 + r2) / 2;
            int midC = (c1 + c2) / 2;
            for (int i = 0; i < all.size(); i++) {
                if (all.get(i).row == midR && all.get(i).col == midC) {
                    if (all.get(i).team != c.team) return 2;
                }
            }
        }
        return 0;
    }
}