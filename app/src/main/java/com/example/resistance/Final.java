package com.example.resistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Final extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        GameEngine gameEngine = (GameEngine) getIntent().getSerializableExtra("gameEngine");

        TextView finalWords = findViewById(R.id.final_words);
        finalWords.setText(String.format("%s won the game", gameEngine.whoWon()));
        //todo: spies win even if resistance wins; fix
    }

    public void finish_button(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}