package com.example.damkaprojfinal_itayzrahia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btnNewGame, btnInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNewGame = findViewById(R.id.btnNewGame);
        btnInstructions = findViewById(R.id.btnInstructions);

        btnNewGame.setOnClickListener(this);
        btnInstructions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == btnNewGame)
        {
            StartGameDialog dialog = new StartGameDialog(this);
            dialog.show();
        }
        else if (v == btnInstructions)
        {
            startActivity(new Intent(this, InstructionsActivity.class));
        }
    }


    public void startGameByTeam(int team) {
        Intent intent = new Intent(this, GameActivity.class);

        if (team == Coin.TEAM_BANANA) {
            intent.putExtra("mode", "playerbanana");
        } else {
            intent.putExtra("mode", "playerstrawberry");
        }

        startActivity(intent);
    }
}