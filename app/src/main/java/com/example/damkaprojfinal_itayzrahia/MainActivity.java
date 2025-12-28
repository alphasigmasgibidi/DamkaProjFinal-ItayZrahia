package com.example.damkaprojfinal_itayzrahia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPlayWhite, btnPlayRed, btnPlayAI, btnInstructions, btnSettings, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlayWhite = findViewById(R.id.btnPlayWhite);
        btnPlayRed = findViewById(R.id.btnPlayRed);
        btnPlayAI = findViewById(R.id.btnPlayAI);
        btnInstructions = findViewById(R.id.btnInstructions);
        btnSettings = findViewById(R.id.btnSettings);
        btnRegister = findViewById(R.id.btnRegister);

        btnPlayWhite.setOnClickListener(this);
        btnPlayRed.setOnClickListener(this);
        btnPlayAI.setOnClickListener(this);
        btnInstructions.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnPlayWhite || v == btnPlayRed || v == btnPlayAI) {
            Intent intent = new Intent(this, GameActivity.class);
            if (v == btnPlayWhite) intent.putExtra("mode", "playerwhite");
            if (v == btnPlayRed) intent.putExtra("mode", "playerred");
            if (v == btnPlayAI) intent.putExtra("mode", "ai");
            startActivity(intent);
        } else if (v == btnInstructions) {
            startActivity(new Intent(this, InstructionsActivity.class));
        } else if (v == btnSettings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (v == btnRegister) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}