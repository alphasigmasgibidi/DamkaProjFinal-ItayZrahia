package com.example.damkaprojfinal_itayzrahia;

public class Position {
    private int lastLine;
    private int lastCol;
    private  int newLine;
    private int newCol;

    public Position() {
    }

    public Position(int lastLine, int lastCol, int newLine, int newCol) {
        this.lastLine = lastLine;
        this.lastCol = lastCol;
        this.newLine = newLine;
        this.newCol = newCol;
    }

    public int getLastLine() {
        return lastLine;
    }

    public void setLastLine(int lastLine) {
        this.lastLine = lastLine;
    }

    public int getLastCol() {
        return lastCol;
    }

    public void setLastCol(int lastCol) {
        this.lastCol = lastCol;
    }

    public int getNewLine() {
        return newLine;
    }

    public void setNewLine(int newLine) {
        this.newLine = newLine;
    }

    public int getNewCol() {
        return newCol;
    }

    public void setNewCol(int newCol) {
        this.newCol = newCol;
    }
}
