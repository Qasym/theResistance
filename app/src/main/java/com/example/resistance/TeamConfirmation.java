package com.example.resistance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressLint("DefaultLocale")
public class TeamConfirmation extends AppCompatActivity {
    TextView confirmMessage;
    ListView selectedPlayersView;
    GameEngine gameEngine;
    ArrayList<String> roundGoers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_confirmation);

        // Initializing variables
        confirmMessage = findViewById(R.id.confirm_message);
        selectedPlayersView = findViewById(R.id.selected_players);
        gameEngine = (GameEngine) getIntent().getSerializableExtra("gameEngine");
        roundGoers = getIntent().getStringArrayListExtra("selectedPlayers");

        // Putting the message
        confirmMessage.setText(String.format("%s is the captain\nThese players are selected:", getIntent().getStringExtra("captain")));

        // Displaying selected players in the listview
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roundGoers);
        selectedPlayersView.setAdapter(adapter);
    }

    /*
    * This method switches to next activity
    * */
    public void agreeButtonClick(View view) {
        gameEngine.resetSwitches(); //resetting captainSwitches to zero
        gameEngine.setRoundGoers(roundGoers);
        Intent intent = new Intent(this, VotePhase.class);
        intent.putExtra("gameEngine", gameEngine);
        startActivity(intent);
        finish();
    }

    /*
    * This method switches to PREVIOUS activity
    * since majority disagrees with the pick
    * */
    public void disagreeButtonClick(View view) {
        if (gameEngine.captainSwitched()) { //if captain is switched and round failed
            findViewById(R.id.agree_button).setVisibility(View.GONE);
            findViewById(R.id.disagree_button).setVisibility(View.GONE);
            selectedPlayersView.setVisibility(View.GONE);
            findViewById(R.id.everyone_agrees).setVisibility(View.GONE);

            confirmMessage.setTextSize(24);
            this.getWindow().getDecorView().setBackgroundColor(Color.RED); // Changing color to red

            new CountDownTimer(4000, 1000) { // wait for 2 seconds
                @Override
                public void onTick(long l) {
                    confirmMessage.setText(String.format("Captains switched too many times!" +
                            "\nSpies won the round" +
                            "\nNext round starts in %d seconds", l / 1000));
                }

                @Override
                public void onFinish() {
                    confirmMessage.setTextSize(14);
                    gameEngine.resetSwitches();
                    Intent intent = new Intent(TeamConfirmation.this, TeamFormation.class);
                    intent.putExtra("gameEngine", gameEngine);
                    startActivity(intent);
                    finish();
                }
            }.start();
        }
        else {
            Intent intent = new Intent(this, TeamFormation.class);
            intent.putExtra("gameEngine", gameEngine);
            startActivity(intent);
            finish();
        }

    }
}