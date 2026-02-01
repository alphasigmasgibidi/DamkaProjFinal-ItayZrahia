package com.example.damkaprojfinal_itayzrahia;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity
{
    FbModule fbModule;
    BoardGame boardGame;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        String mode = getIntent().getStringExtra("mode");
        boardGame = new BoardGame(this, mode);
        setContentView(boardGame);

        fbModule = new FbModule(this);
        fbModule.setPositionInFirebase(new Position(1,2,3,4));
    }

    public void setPositionFromFb(Position position) {
        boardGame.setPositionFromFb(position);
    }
}