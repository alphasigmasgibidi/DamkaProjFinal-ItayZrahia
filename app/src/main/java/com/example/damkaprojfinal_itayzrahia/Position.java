package com.example.damkaprojfinal_itayzrahia;

public class Position
{
    private int lastRow;
    private int lastCol;
    private  int newRow;
    private int newCol;

    public Position() {}

    public Position(int lastRow, int lastCol, int newRow, int newCol)
    {
        this.lastRow = lastRow;
        this.lastCol = lastCol;
        this.newRow = newRow;
        this.newCol = newCol;
    }

    public int getLastRow() {return lastRow;}

    public int getLastCol() {return lastCol;}

    public int getNewRow() {return newRow;}

    public int getNewCol() {return newCol;}

}
