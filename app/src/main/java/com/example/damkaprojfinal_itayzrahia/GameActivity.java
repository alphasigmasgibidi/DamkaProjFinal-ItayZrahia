package com.example.damkaprojfinal_itayzrahia;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mode = getIntent().getStringExtra("mode");
        BoardGame boardGame = new BoardGame(this, mode);
        setContentView(boardGame);
    }
}