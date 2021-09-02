package com.example.resistance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@SuppressLint({"DefaultLocale", "SetTextI18n"})
public class ShowResults extends AppCompatActivity {
    TextView resultsIn;
    View view;
    GameEngine gameEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        // Initializing variables
        view = this.getWindow().getDecorView();
        resultsIn = findViewById(R.id.results_in);
        gameEngine = (GameEngine) getIntent().getSerializableExtra("gameEngine");


        // Displaying the results
        countDown();
    }

    public void countDown() {
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long milliSecondsRemaining) {
                resultsIn.setText("Results in " + milliSecondsRemaining / 1000);
            }

            @Override
            public void onFinish() {
                resultsIn.setVisibility(View.GONE);
                int fails = gameEngine.calculateResult();
                int passes = gameEngine.roundGoers.size() - fails;

                showBlues(passes * 2000L, fails * 2000L);
            }
        }.start();
    }

    private void showBlues(long time1, long time2) {
        CountDownTimer timer = new CountDownTimer(time1, 1000) {
            boolean flag = true;
            @Override
            public void onTick(long l) {
                if (flag) {
                    makeScreenBlue();
                    flag = false;
                }
                else {
                    makeScreenWhite();
                    flag = true;
                }
            }

            @Override
            public void onFinish() {
                makeScreenWhite();
                showReds(time2);
            }
        };
        timer.start();
    }

    private void showReds(long time) {
        CountDownTimer timer = new CountDownTimer(time, 1000) {
            boolean flag = true;
            @Override
            public void onTick(long l) {
                if (flag) {
                    makeScreenRed();
                    flag = false;
                }
                else {
                    makeScreenWhite();
                    flag = true;
                }
            }

            @Override
            public void onFinish() {
                makeScreenWhite();
                gameEngine.nextRound();
                Intent intent = new Intent(ShowResults.this, TeamFormation.class);
                intent.putExtra("gameEngine", gameEngine);
                startActivity(intent);
                finish();
            }
        };
        timer.start();
    }

    public void makeScreenRed() {
        view.setBackgroundColor(Color.RED);
    }

    public void makeScreenBlue() {
        view.setBackgroundColor(Color.BLUE);
    }

    public void makeScreenWhite() {
        view.setBackgroundColor(Color.WHITE);
    }

}