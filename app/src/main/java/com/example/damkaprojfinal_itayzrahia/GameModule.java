package com.example.damkaprojfinal_itayzrahia;

import java.util.ArrayList;

public class GameModule
{
    public int currentTurn = Coin.TEAM_BANANA;

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
        if (currentTurn == Coin.TEAM_BANANA)
        {
            currentTurn = Coin.TEAM_STRAWBERRY;
        }
        else
        {
            currentTurn = Coin.TEAM_BANANA;
        }
    }

    public int checkMove(Coin c, int rowNew, int colNew, ArrayList<Coin> all)
    {

        if (colNew < 0 || rowNew > 7 || rowNew < 0 || colNew > 7) //-*Checks if coin is placed inside the board*-
        {
            return MOVE_ILEGAL;
        }

        if ((rowNew + colNew) % 2 == 0) //-*Checks if coin is placed on Dark squares*-
        {
            return MOVE_ILEGAL;
        }

        for (int i = 0; i < all.size(); i++) //-*Checks if coin is placed on empty squares*- 
        {
            if (all.get(i).row == rowNew)
            {
                if (all.get(i).col == colNew)
                {
                    return MOVE_ILEGAL;
                }
            }
        }

        int rowCurrent = c.row;
        
        int colCurrent = c.col;
        
        
        int rowHefresh = rowNew - rowCurrent;
        
        int colHefresh = colNew - colCurrent;


        int rowHefreshAbsolute = rowHefresh;


        if (rowHefreshAbsolute < 0) // making the rowHefresh a positive number always
        {
            rowHefreshAbsolute = -rowHefreshAbsolute;
        }


        int colHefreshAbsolute = colHefresh;


        if (colHefreshAbsolute < 0) // making the colHefresh a positive number always
        {
            colHefreshAbsolute = -colHefreshAbsolute;
        }


        if (c.team == Coin.TEAM_BANANA) // if there's a jump of more than one square for Banana
        {
            if (rowHefresh >= 0)
            {
                return MOVE_ILEGAL;
            }
        }

        if (c.team == Coin.TEAM_STRAWBERRY) // if there's a jump of more than one square for Strawberry
        {
            if (rowHefresh <= 0)
            {
                return MOVE_ILEGAL;
            }
        }

        if (rowHefreshAbsolute == 1)
        {
            if (colHefreshAbsolute == 1)
            {
                return MOVE_REGULAR;
            }
        }

        if (rowHefreshAbsolute == 2)
        {
            if (colHefreshAbsolute == 2)
            {
                int midR = (rowCurrent + rowNew) / 2;
                int midC = (colCurrent + colNew) / 2;

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
            if (c.team == Coin.TEAM_BANANA) doesWhiteExist = true;
            if (c.team == Coin.TEAM_STRAWBERRY) doesRedExist = true;
        }

        if (!doesRedExist) return Coin.TEAM_BANANA;
        if (!doesWhiteExist) return Coin.TEAM_STRAWBERRY;

        return 0;
    }
}