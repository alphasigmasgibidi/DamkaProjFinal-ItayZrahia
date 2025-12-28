package com.example.damkaprojfinal_itayzrahia;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class InstructionsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }

    public void onBackClick(View v)
    {
        finish();
    }
}