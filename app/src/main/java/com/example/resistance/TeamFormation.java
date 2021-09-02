package com.example.resistance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressLint({"SetTextI18n", "DefaultLocale"})
public class TeamFormation extends AppCompatActivity {
    ListView allPlayersView, selectedPlayersView;
    Button proposeButton;
    TextView announcer, teamviewer;
    GameEngine gameEngine;
    ArrayList<String> allPlayersList, selectedPlayersList;
    ArrayAdapter<String> allPlayersAdapter, selectedPlayersAdapter;
    String captain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_formation);

        //initializing variables
        gameEngine = (GameEngine) getIntent().getSerializableExtra("gameEngine");
        allPlayersView = findViewById(R.id.all_players);
        selectedPlayersView = findViewById(R.id.selected_players);
        proposeButton = findViewById(R.id.propose_button);
        announcer = findViewById(R.id.announcer);
        allPlayersList = gameEngine.getPlayersCopy();
        selectedPlayersList = new ArrayList<>();
        teamviewer = findViewById(R.id.teamviewer);

        // Check if anyone won the game
        if (gameEngine.anyWinner()) {
            Intent intent = new Intent(this, Final.class);
            intent.putExtra("gameEngine", gameEngine);
            startActivity(intent);
            finish();
            return;
        }

        // Toast message announcement
        Toast.makeText(this, String.format("Round %d", gameEngine.getCurrentRound() + 1), Toast.LENGTH_LONG).show();

        // Displaying captain and showing player limit
        captain = gameEngine.getNextCaptain();
        announcer.setText(String.format("%s is the captain", captain));
        teamviewer.setText(String.format("Select %d players for your team", gameEngine.getCurrentRoundLimit()));

        // Setting up a rounds history (who won each round)
        roundHistorySetup();

        //displaying all players in all_players listview
        allPlayersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allPlayersList);
        allPlayersView.setAdapter(allPlayersAdapter);

        //adding callback for all_players listview
        //it will remove players from all_players and add them to selected_players
        allPlayersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (selectedPlayersList.size() < gameEngine.getCurrentRoundLimit()) {
                    //displaying selected players in selected_players listview
                    selectedPlayersList.add(allPlayersList.get(position));
                    selectedPlayersAdapter = new ArrayAdapter<>(TeamFormation.this, android.R.layout.simple_list_item_1, selectedPlayersList);
                    selectedPlayersView.setAdapter(selectedPlayersAdapter);

                    //removing clicked players from all_players listview
                    allPlayersList.remove(position);
                    allPlayersAdapter = new ArrayAdapter<>(TeamFormation.this, android.R.layout.simple_list_item_1, allPlayersList);
                    allPlayersView.setAdapter(allPlayersAdapter);
                }
                else {
                    Toast.makeText(TeamFormation.this, "You can't select more players,\nplayer limit is reached", Toast.LENGTH_LONG).show();
                }
            }
        });

        //adding callback for selected_players listview
        //it will remove players from selected_players and add the to the all_players listview
        selectedPlayersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //displaying players in all_players listview (not including those in selected_players, but including the one clicked onto)
                allPlayersList.add(selectedPlayersList.get(position));
                allPlayersAdapter = new ArrayAdapter<>(TeamFormation.this, android.R.layout.simple_list_item_1, allPlayersList);
                allPlayersView.setAdapter(allPlayersAdapter);

                //removing clicked players from selected_players listview
                selectedPlayersList.remove(position);
                selectedPlayersAdapter = new ArrayAdapter<>(TeamFormation.this, android.R.layout.simple_list_item_1, selectedPlayersList);
                selectedPlayersView.setAdapter(selectedPlayersAdapter);
            }
        });

    }

    /*
    * This method will simply start different activity
    * */
    public void proposeButtonClick(View view) {
        if (selectedPlayersList.size() == gameEngine.getCurrentRoundLimit()) {
            Intent intent = new Intent(TeamFormation.this, TeamConfirmation.class);
            intent.putExtra("gameEngine", gameEngine);
            intent.putStringArrayListExtra("selectedPlayers", selectedPlayersList);
            intent.putExtra("captain", captain);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, String.format("Please, select %d players", gameEngine.getCurrentRoundLimit()), Toast.LENGTH_SHORT).show();
        }
    }

    private void roundHistorySetup() {
        TextView roundTextView = null;
        for (int i = 0; i < gameEngine.history.length; i++) {
            //choosing roundTextView
            if (i == 0) {
                roundTextView = findViewById(R.id.round_one);
            }
            else if (i == 1) {
                roundTextView = findViewById(R.id.round_two);
            }
            else if (i == 2) {
                roundTextView = findViewById(R.id.round_three);
            }
            else if (i == 3) {
                roundTextView = findViewById(R.id.round_four);
            }
            else if (i == 4) {
                roundTextView = findViewById(R.id.round_five);
            }
            //setting up a history
            if (gameEngine.history[i] < 0) {
                roundTextView.setText(String.format("Round %d\nSpies", i + 1));
            }
            else if (gameEngine.history[i] > 0) {
                roundTextView.setText(String.format("Round %d\nResistance", i + 1));
            }
            else {
                roundTextView.setText(String.format("Round %d", i + 1));
            }

            if (gameEngine.getCurrentRound() == i) {
                roundTextView.setText(String.format("Round %d\nCurrent", i + 1));
            }
        }
    }
}