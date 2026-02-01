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

        fbModule = new FbModule(this);
        fbModule.setPositionInFirebase(new Position(1,2,3,4));

        String mode = getIntent().getStringExtra("mode");
        boardGame = new BoardGame(this, mode, fbModule);
        setContentView(boardGame);

    }

    public void setPositionFromFb(Position position) {
        boardGame.setPositionFromFb(position);
    }
}