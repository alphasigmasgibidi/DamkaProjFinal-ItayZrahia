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

        if (colNew < 0 || rowNew > 7 || rowNew < 0 || colNew > 7) //condition 1- coin is placed inside the board
        {
            return MOVE_ILEGAL;
        }

        if ((rowNew + colNew) % 2 == 0) //condition 2- if coin is placed on Dark squares
        {
            return MOVE_ILEGAL;
        }

        for (int i = 0; i < all.size(); i++) //condition 3- if coin is placed on empty squares
        {
            if (all.get(i).row == rowNew)
            {
                if (all.get(i).col == colNew)
                {
                    return MOVE_ILEGAL;
                }
            }
        }

        //getting ready to catch if the coin ate another one or just made a regular move
        int rowCurrent = c.row;
        int colCurrent = c.col;
        
        
        int rowHefresh = rowNew - rowCurrent;
        int colHefresh = colNew - colCurrent;


        if (c.team == Coin.TEAM_BANANA) //condition 4- if banana is going forward
        {
            if (rowHefresh >= 0)
            {
                return MOVE_ILEGAL;
            }
        }

        if (c.team == Coin.TEAM_STRAWBERRY) //condition 4- if strawberry is going forward
        {
            if (rowHefresh <= 0)
            {
                return MOVE_ILEGAL;
            }
        }

        // now that we know they are going to the correct side,
        //the number is the only thing that matters to analyze the move type.
        //so we check if coin is taking a regular step

        if (Math.abs(rowHefresh) == 1)
        {
            if (Math.abs(colHefresh) == 1)
            {
                return MOVE_REGULAR;
            }
        }

        //we check if coin is taking a two block move (meaning maybe it ate someone)
        if (Math.abs(rowHefresh) == 2)
        {
            if (Math.abs(colHefresh) == 2)
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

        //lastly we are left with the understanding that,
        //coin made a two block move without eating anyone
        //this is ilegal
        return MOVE_ILEGAL;
    }

    //every turn we check if someone won
    //we check if a team is left with no coins
    public int checkWinner(ArrayList<Coin> allCoins)
    {
        boolean doesBananaExist = false;
        boolean doesStrawberryExist = false;

        for (Coin c : allCoins) {
            if (c.team == Coin.TEAM_BANANA) doesBananaExist = true;
            if (c.team == Coin.TEAM_STRAWBERRY) doesStrawberryExist = true;
        }

        if (!doesStrawberryExist) return Coin.TEAM_BANANA;
        if (!doesBananaExist) return Coin.TEAM_STRAWBERRY;

        return 0;
    }
}