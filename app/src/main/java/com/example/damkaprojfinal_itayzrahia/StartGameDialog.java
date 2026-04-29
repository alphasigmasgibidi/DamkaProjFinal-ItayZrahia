package com.example.damkaprojfinal_itayzrahia;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartGameDialog extends Dialog implements View.OnClickListener {

    private Button btnWhite, btnRed;
    private MainActivity mainActivity;

    public StartGameDialog(MainActivity activity) {
        super(activity);
        this.mainActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start_game_);

        btnWhite = findViewById(R.id.btnWhite);
        btnRed = findViewById(R.id.btnRed);

        btnWhite.setOnClickListener(this);
        btnRed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnWhite) {
            mainActivity.startGameByTeam(Coin.TEAM_WHITE);
        } else if (v == btnRed) {
            mainActivity.startGameByTeam(Coin.TEAM_RED);
        }
        dismiss();
    }
}