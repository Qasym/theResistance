package com.example.resistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TeamConfirmation extends AppCompatActivity {
    TextView confirmMessage;
    ListView selectedPlayersView;
    GameEngine gameEngine;
    ArrayList<String> roundGoers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_comfirmation);

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
        gameEngine.nextRound(); //updating the round
        gameEngine.resetSwitches(); //resetting captainSwitches to zero
        gameEngine.setRoundGoers(roundGoers);
        Toast.makeText(this, "Next Activity", Toast.LENGTH_SHORT).show();
    }

    /*
    * This method switches to PREVIOUS activity
    * since majority disagrees with the pick
    * */
    public void disagreeButtonClick(View view) {
        gameEngine.captainSwitched();
        Intent intent = new Intent(this, TeamFormation.class);
        intent.putExtra("gameEngine", gameEngine);
        startActivity(intent);
        finish();
    }
}