package com.example.resistance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playButtonClick(View view) {
        Toast.makeText(this, "Play Button clicked!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddPlayers.class);
        startActivity(intent);
        finish();
    }

    /*
    * This button callback should display rules
    * */
    public void rulesButtonClick(View view) {
        Intent intent = new Intent(this, DisplayRules.class);
        startActivity(intent);

    }

}