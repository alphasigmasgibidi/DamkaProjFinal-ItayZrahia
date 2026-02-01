package com.example.damkaprojfinal_itayzrahia;

public class Position {
    private int lastRow;
    private int lastCol;
    private  int newRow;
    private int newCol;

    public Position() {
    }

    public Position(int lastRow, int lastCol, int newRow, int newCol) {
        this.lastRow = lastRow;
        this.lastCol = lastCol;
        this.newRow = newRow;
        this.newCol = newCol;
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public int getLastCol() {
        return lastCol;
    }

    public void setLastCol(int lastCol) {
        this.lastCol = lastCol;
    }

    public int getNewRow() {
        return newRow;
    }

    public void setNewRow(int newRow) {
        this.newRow = newRow;
    }

    public int getNewCol() {
        return newCol;
    }

    public void setNewCol(int newCol) {
        this.newCol = newCol;
    }
}
