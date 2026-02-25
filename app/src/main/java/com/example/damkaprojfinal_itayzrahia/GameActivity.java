package com.example.damkaprojfinal_itayzrahia;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    FbModule fbModule;
    GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String mode = getIntent().getStringExtra("mode");
        fbModule = new FbModule(this);

        gameView = new GameView(this, mode, fbModule);
        setContentView(gameView);
    }

    // זו הפעולה ש-FbModule קורא לה כשמגיע מידע חדש
    public void setPositionFromFb(Position position) {
        if (gameView != null) {
            gameView.moveCoin(position);

            if(gameView.isWin() == Coin.TEAM_WHITE)
            {
                if(GameView.myTeam == Coin.TEAM_WHITE)
                {
                    Toast.makeText(this, "YOU WIN", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "YOU LOSE", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                if(GameView.myTeam == Coin.TEAM_RED)
                {
                    Toast.makeText(this, "YOU WIN", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "YOU LOSE", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}