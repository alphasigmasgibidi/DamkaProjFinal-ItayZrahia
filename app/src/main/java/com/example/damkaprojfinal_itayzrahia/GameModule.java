package com.example.damkaprojfinal_itayzrahia;

import java.util.ArrayList;

public class GameModule
{
    public int currentTurn = Coin.TEAM_WHITE;

    public static final int MOVE_ILEGAL = 0;
    public static final int MOVE_REGULAR = 1;
    public static final int MOVE_EAT = 2;


    

    public boolean isMyTurn(int team)
    {
        if (team == currentTurn)
        {
            return true;
        }
        return false;
    }

    public void switchTurn()
    {
        if (currentTurn == Coin.TEAM_WHITE)
        {
            currentTurn = Coin.TEAM_RED;
        }
        else
        {
            currentTurn = Coin.TEAM_WHITE;
        }
    }

    public int checkMove(Coin c, int rNEW, int cNEW, ArrayList<Coin> all)
    {
        if (rNEW < 0 || rNEW > 7 || cNEW < 0 || cNEW > 7)
        {
            return MOVE_ILEGAL;
        }

        if ((rNEW + cNEW) % 2 == 0)
        {
            return MOVE_ILEGAL;
        }

        for (int i = 0; i < all.size(); i++)
        {
            if (all.get(i).row == rNEW)
            {
                if (all.get(i).col == cNEW)
                {
                    return MOVE_ILEGAL;
                }
            }
        }

        int r1 = c.row;
        int c1 = c.col;
        int dr = rNEW - r1;
        int dc = cNEW - c1;

        if (c.team == Coin.TEAM_WHITE)
        {
            if (dr >= 0)
            {
                return MOVE_ILEGAL;
            }
        }

        if (c.team == Coin.TEAM_RED)
        {
            if (dr <= 0)
            {
                return MOVE_ILEGAL;
            }
        }

        int absR = dr;
        if (absR < 0)
        {
            absR = -absR;
        }

        int absC = dc;
        if (absC < 0)
        {
            absC = -absC;
        }

        if (absR == 1)
        {
            if (absC == 1)
            {
                return MOVE_REGULAR;
            }
        }

        if (absR == 2)
        {
            if (absC == 2)
            {
                int midR = (r1 + rNEW) / 2;
                int midC = (c1 + cNEW) / 2;

                for (int i = 0; i < all.size(); i++)
                {
                    if (all.get(i).row == midR)
                    {
                        if (all.get(i).col == midC)
                        {
                            if (all.get(i).team != c.team)
                            {
                                return MOVE_EAT;
                            }
                        }
                    }
                }
            }
        }

        return MOVE_EAT;
    }

    public int isWin() {

        return 1;
    }
}