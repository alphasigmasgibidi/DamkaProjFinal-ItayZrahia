package com.example.damkaprojfinal_itayzrahia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btnPlay, btnInstructions;

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
            // במקום לעבור ישר למסך, נפתח דיאלוג לבחירת צבע
            showColorSelectionDialog();
        }
        else if (v == btnInstructions)
        {
            startActivity(new Intent(this, InstructionsActivity.class));
        }
    }

    private void showColorSelectionDialog() {
        String[] colors = {"שחקן לבן", "שחקן אדום"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("בחר באיזה צד אתה");

        builder.setItems(colors, (dialog, which) -> {
            Intent intent = new Intent(this, GameActivity.class);

            if (which == 0) { // לבן
                intent.putExtra("mode", "playerwhite");
            } else { // אדום
                intent.putExtra("mode", "playerred");
            }

            startActivity(intent);
        });

        builder.setNegativeButton("ביטול", null);
        builder.show();
    }

}