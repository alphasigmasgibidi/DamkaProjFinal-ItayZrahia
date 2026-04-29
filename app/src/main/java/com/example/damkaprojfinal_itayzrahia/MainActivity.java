package com.example.damkaprojfinal_itayzrahia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btnPlay, btnInstructions;

    private final ActivityResultLauncher<Intent> dialogLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String mode = result.getData().getStringExtra("mode");
                    Intent intent = new Intent(this, GameActivity.class);
                    intent.putExtra("mode", mode);
                    startActivity(intent);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        btnInstructions = findViewById(R.id.btnInstructions);

        btnPlay.setOnClickListener(this);
        btnInstructions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == btnPlay)
        {
            Intent intent = new Intent(this, StartGameDialog.class);
            dialogLauncher.launch(intent);
        }
        else if (v == btnInstructions)
        {
            startActivity(new Intent(this, InstructionsActivity.class));
        }
    }
}