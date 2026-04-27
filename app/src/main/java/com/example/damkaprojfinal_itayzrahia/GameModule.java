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

    public int checkMove(Coin c, int row_NEW, int col_NEW, ArrayList<Coin> all)
    {
        int index = all.indexOf(c);
        if (all.get(index).)
        if (col_NEW < 0 || row_NEW > 7 || row_NEW < 0 || col_NEW > 7) //-*Checks if coin is placed inside the board*-
        {
            return MOVE_ILEGAL;
        }

        if ((row_NEW + col_NEW) % 2 == 0) //-*Checks if coin is placed on RED squares*-
        {
            return MOVE_ILEGAL;
        }

        for (int i = 0; i < all.size(); i++) //-*Checks if coin is placed on empty squares*- 
        {
            if (all.get(i).row == row_NEW)
            {
                if (all.get(i).col == col_NEW)
                {
                    return MOVE_ILEGAL;
                }
            }
        }

        int row_CURRENT = c.row;
        
        int col_CURRENT = c.col;
        
        
        int row_HEFRESH = row_NEW - row_CURRENT;
        
        int col_HEFRESH = col_NEW - col_CURRENT;

        if (c.team == Coin.TEAM_WHITE) // if there's a jump of more than one square for WHITE
        {
            if (row_HEFRESH >= 0)
            {
                return MOVE_ILEGAL;
            }
        }

        if (c.team == Coin.TEAM_RED) // if there's a jump of more than one square for RED
        {
            if (row_HEFRESH <= 0)
            {
                return MOVE_ILEGAL;
            }
        }

        int row_HEFRESH_ABSOLUTE = row_HEFRESH; 
        
        
        if (row_HEFRESH_ABSOLUTE < 0) // making the row_HEFRESH a positive number always
        {
            row_HEFRESH_ABSOLUTE = -row_HEFRESH_ABSOLUTE;
        }

        
        int col_HEFRESH_ABSOLUTE = col_HEFRESH; 
        
        
        if (col_HEFRESH_ABSOLUTE < 0) // making the col_HEFRESH a positive number always
        {
            col_HEFRESH_ABSOLUTE = -col_HEFRESH_ABSOLUTE;
        }

        if (row_HEFRESH_ABSOLUTE == 1)
        {
            if (col_HEFRESH_ABSOLUTE == 1)
            {
                return MOVE_REGULAR;
            }
        }

        if (row_HEFRESH_ABSOLUTE == 2)
        {
            if (col_HEFRESH_ABSOLUTE == 2)
            {
                int midR = (row_CURRENT + row_NEW) / 2;
                int midC = (col_CURRENT + col_NEW) / 2;

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

        return MOVE_ILEGAL;
    }

    public int checkWinner(ArrayList<Coin> allCoins) {
        boolean doesWhiteExist = false;
        boolean doesRedExist = false;

        for (Coin c : allCoins) {
            if (c.team == Coin.TEAM_WHITE) doesWhiteExist = true;
            if (c.team == Coin.TEAM_RED) doesRedExist = true;
        }

        if (!doesRedExist) return Coin.TEAM_WHITE;
        if (!doesWhiteExist) return Coin.TEAM_RED;

        return 0;
    }
}