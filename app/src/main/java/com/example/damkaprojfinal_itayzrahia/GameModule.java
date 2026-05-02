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

    public int checkMove(Coin c, int row_NEW, int col_NEW, ArrayList<Coin> all)
    {

        King k = new King(all.remove(14));
        all.add(5, k);
        King kimk = new King(all.remove(2));
        all.add(2, kimk);

        if (col_NEW < 0 || row_NEW > 7 || row_NEW < 0 || col_NEW > 7) //-*Checks if coin is placed inside the board*-
        {
            return MOVE_ILEGAL;
        }

        if ((row_NEW + col_NEW) % 2 == 0) //-*Checks if coin is placed on Dark squares*-
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


        int index = all.indexOf(c); // For The sake of tidy code we split the moves
        if (all.get(index).type == "king")
        {
            return checkMoveIfKing(c, row_NEW, col_NEW, all, row_HEFRESH_ABSOLUTE, col_HEFRESH_ABSOLUTE, row_CURRENT, col_CURRENT );
        }

        if (c.team == Coin.TEAM_BANANA) // if there's a jump of more than one square for Banana
        {
            if (row_HEFRESH >= 0)
            {
                return MOVE_ILEGAL;
            }
        }

        if (c.team == Coin.TEAM_STRAWBERRY) // if there's a jump of more than one square for Strawberry
        {
            if (row_HEFRESH <= 0)
            {
                return MOVE_ILEGAL;
            }
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

    public int checkMoveIfKing(Coin c, int row_NEW, int col_NEW, ArrayList<Coin> all, int row_HEFRESH_ABSOLUTE, int col_HEFRESH_ABSOLUTE, int row_CURRENT, int col_CURRENT ) {

        if (row_HEFRESH_ABSOLUTE == col_HEFRESH_ABSOLUTE)
        {
                int midR = (row_CURRENT + row_NEW) - 1;
                int midC = (col_CURRENT + col_NEW) - 1;

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


        return MOVE_REGULAR;

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