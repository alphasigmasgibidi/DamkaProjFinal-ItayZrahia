package com.example.damkaprojfinal_itayzrahia;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    FbModule fbModule;
    BoardGame boardGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String mode = getIntent().getStringExtra("mode");
        fbModule = new FbModule(this);

        boardGame = new BoardGame(this, mode, fbModule);
        setContentView(boardGame);
    }

    // זו הפעולה ש-FbModule קורא לה כשמגיע מידע חדש
    public void setPositionFromFb(Position position) {
        if (boardGame != null) {
            boardGame.moveCoin(position);
        }
    }
}