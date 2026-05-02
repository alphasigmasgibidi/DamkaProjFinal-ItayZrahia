package com.example.damkaprojfinal_itayzrahia;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartGameDialog extends Dialog implements View.OnClickListener {

    private Button btnBanana, btnStrawberry;
    private MainActivity mainActivity;

    public StartGameDialog(MainActivity activity) {
        super(activity);
        this.mainActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start_game_);

        btnBanana = findViewById(R.id.btnBanana);
        btnStrawberry = findViewById(R.id.btnStrawberry);

        btnBanana.setOnClickListener(this);
        btnStrawberry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnBanana) {
            mainActivity.startGameByTeam(Coin.TEAM_BANANA);
        } else if (v == btnStrawberry) {
            mainActivity.startGameByTeam(Coin.TEAM_STRAWBERRY);
        }
        dismiss();
    }
}