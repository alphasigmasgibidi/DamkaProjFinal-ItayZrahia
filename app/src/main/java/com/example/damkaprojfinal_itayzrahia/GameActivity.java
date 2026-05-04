package com.example.damkaprojfinal_itayzrahia;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity
{
    FbModule fbModule;
    GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        String mode = getIntent().getStringExtra("mode");
        fbModule = new FbModule(this);

        gameView = new GameView(this, mode, fbModule);
        setContentView(gameView);
    }

    public void setPositionFromFb(Position position)
    {
        if (gameView != null)
        {
            gameView.moveCoin(position);

            if (gameView.isWin() == Coin.TEAM_BANANA)//after every turn checks if banana won
            {
                if (GameView.myTeam == Coin.TEAM_BANANA) //if im banana, I won
                {
                    Toast.makeText(this, "YOU WIN", Toast.LENGTH_SHORT).show();
                }
                else //if they are banana, I lost
                {
                    Toast.makeText(this, "BANANA WINS", Toast.LENGTH_SHORT).show();
                }
            }
            if (gameView.isWin() == Coin.TEAM_STRAWBERRY)//after every turn checks if strawberry won
            {
                if (GameView.myTeam == Coin.TEAM_STRAWBERRY) //if im strawberry, I won
                {
                    Toast.makeText(this, "YOU WIN", Toast.LENGTH_SHORT).show();
                }
                else //if they are strawberry, I lost
                {
                    Toast.makeText(this, "STRAWBERRY WINS", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}