package com.example.resistance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TeamFormation extends AppCompatActivity {
    ListView allPlayersView, selectedPlayersView;
    Button propose;
    TextView announcer, teamviewer;
    GameEngine gameEngine = (GameEngine) getIntent().getSerializableExtra("gameEngine");
    ArrayList<String> allPlayersList, selectedPlayersList;
    ArrayAdapter<String> allPlayersAdapter, selectedPlayersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_formation);

        //initializing variables
        allPlayersView = findViewById(R.id.all_players);
        selectedPlayersView = findViewById(R.id.selected_players);
        propose = findViewById(R.id.propose_button);
        announcer = findViewById(R.id.announcer);
        allPlayersList = gameEngine.getPlayers();
        selectedPlayersList = new ArrayList<>();

        //displaying all players in all_players listview
        allPlayersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allPlayersList);
        allPlayersView.setAdapter(allPlayersAdapter);

        //adding callback for all_players listview
        //it will remove players from all_players and add them to selected_players
        allPlayersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //displaying selected players in selected_players listview
                selectedPlayersList.add(allPlayersList.get(position));
                selectedPlayersAdapter = new ArrayAdapter<>(TeamFormation.this, android.R.layout.simple_list_item_1, selectedPlayersList);
                selectedPlayersView.setAdapter(selectedPlayersAdapter);

                //removing clicked players from all_players listview
                allPlayersList.remove(position);
                allPlayersAdapter = new ArrayAdapter<>(TeamFormation.this, android.R.layout.simple_list_item_1, allPlayersList);
                allPlayersView.setAdapter(allPlayersAdapter);
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
        
    }
}