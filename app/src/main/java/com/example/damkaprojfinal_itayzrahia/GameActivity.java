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

    public void setPositionFromFb(Position position) {
        if (gameView != null) {
            gameView.moveCoin(position);

            if (gameView.isWin() == Coin.TEAM_BANANA) {
                if (GameView.myTeam == Coin.TEAM_BANANA) {
                    Toast.makeText(this, "YOU WIN", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "BANANA WINS", Toast.LENGTH_SHORT).show();
                }
            }
            if (gameView.isWin() == Coin.TEAM_STRAWBERRY) {
                if (GameView.myTeam == Coin.TEAM_STRAWBERRY) {
                    Toast.makeText(this, "YOU WIN", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "STRAWBERRY WINS", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}