package com.example.damkaprojfinal_itayzrahia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class StartGameDialog extends AppCompatActivity implements View.OnClickListener {

    private Button btnWhite, btnRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnWhite = findViewById(R.id.btnWhite);
        btnRed = findViewById(R.id.btnRed);

        btnWhite.setOnClickListener(this);
        btnRed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent resultIntent = new Intent();
        if (v == btnWhite) {
            resultIntent.putExtra("mode", "playerwhite");
        } else if (v == btnRed) {
            resultIntent.putExtra("mode", "playerred");
        }
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}