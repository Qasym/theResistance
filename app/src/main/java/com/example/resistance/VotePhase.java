package com.example.resistance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@SuppressLint("SetTextI18n")
public class VotePhase extends AppCompatActivity {
    Button iAmButton, passButton, failButton;
    TextView passVote;
    GameEngine gameEngine;
    int playerIndex;
    boolean isSpy;

    @Override
    // This method is executed only once, further we just change visibility, do not restart this activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_phase);

        // Initializing variables
        gameEngine = (GameEngine) getIntent().getSerializableExtra("gameEngine");
        iAmButton = findViewById(R.id.i_am_button);
        passButton = findViewById(R.id.pass_button);
        failButton = findViewById(R.id.fail_button);
        passVote = findViewById(R.id.pass_vote);
        playerIndex = 0;

        // Changing visibility
        // Initially we see iAmButton, and do not se the passButton & failButton
        iAmButton.setVisibility(View.VISIBLE);
        passButton.setVisibility(View.GONE);
        failButton.setVisibility(View.GONE);

        // Setting up a proper text
        passVote.setText(String.format("Pass phone to %s", gameEngine.roundGoers.get(playerIndex)));
        iAmButton.setText(String.format("I am %s", gameEngine.roundGoers.get(playerIndex)));
    }

    /*
    * Callback for iAmButton
    * It will change the visibility*/
    public void iAmButtonClick(View view) {
        // Setting up the text
        passVote.setText(String.format("%s choose mission destiny", gameEngine.roundGoers.get(playerIndex)));
        if (gameEngine.getResistance().contains(gameEngine.roundGoers.get(playerIndex))) { //if the current player is part of the Resistance
            failButton.setText("Pass");
            passButton.setText("Pass");
            isSpy = false;
        }
        else {
            // Below code is to make buttons appear randomly
            Random random = new Random(new Date().getTime());
            if (random.nextBoolean()) {
                failButton.setText("Fail");
                passButton.setText("Pass");
            }
            else {
                failButton.setText("Pass");
                passButton.setText("Fail");
            }
            isSpy = true;
        }

        // Changing the visibility
        iAmButton.setVisibility(View.GONE);
        passButton.setVisibility(View.VISIBLE);
        failButton.setVisibility(View.VISIBLE);
    }

    /*
    * Below two methods are almost the same
    * Closely related to each other
    * */
    public void failButtonClick(View view) {
        if (!isSpy) { //if the player is not spy, we direct him/her to passButtonClick
            passButtonClick(view);
        }

        // Counting vote
        gameEngine.roundVotes[playerIndex] = false;
        playerIndex++;


        if (playerIndex < gameEngine.roundVotes.length) {
            //Setting up the text
            passVote.setText(String.format("Pass phone to %s", gameEngine.roundGoers.get(playerIndex)));
            iAmButton.setText(String.format("I am %s", gameEngine.roundGoers.get(playerIndex)));

            // Changing visibility
            iAmButton.setVisibility(View.VISIBLE);
            passButton.setVisibility(View.GONE);
            failButton.setVisibility(View.GONE);
        }
        else {
            Toast.makeText(this, "Next Activity!", Toast.LENGTH_SHORT).show();
        }
    }

    public void passButtonClick(View view) {
        if (isSpy) { //if the player is spy, we direct him/her to failButtonClick
            failButtonClick(view);
        }

        // Counting vote
        gameEngine.roundVotes[playerIndex] = true;
        playerIndex++;


        if (playerIndex < gameEngine.roundVotes.length) {
            //Setting up the text
            passVote.setText(String.format("Pass phone to %s", gameEngine.roundGoers.get(playerIndex)));
            iAmButton.setText(String.format("I am %s", gameEngine.roundGoers.get(playerIndex)));

            // Changing visibility
            iAmButton.setVisibility(View.VISIBLE);
            passButton.setVisibility(View.GONE);
            failButton.setVisibility(View.GONE);
        }
        else {
            Toast.makeText(this, "Next Activity!", Toast.LENGTH_SHORT).show();
        }
    }
}