package com.example.resistance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;

public class AddPlayers extends AppCompatActivity {

    public final LinkedList<String> playersList = new LinkedList<>(); // List of all entered players

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
    }

    /*
    * When addButton is clicked we need to add the playerName
    * to our list of players and then display the current players
    * in our listView.
    *
    * Needs handlers such as emptyPlayerName, repeatedPlayerName (and others?)
    * */
    public void addButtonClick(android.view.View view) {
        // Let's handle and add playerName
        //////////////////////////////////////////////////////////////////////////
        EditText playerName = findViewById(R.id.playerName);
        if (playerName.getText().toString().compareTo("") == 0) { // if playerName is an empty string
            Toast.makeText(this, "Please, enter a name!", Toast.LENGTH_LONG).show();
            playerName.setHint("PlayerName");
            return;
        }
        else if (playersList.contains(playerName.getText().toString())) { // if playerName is repeated
            Toast.makeText(this, "Please, enter a different name!\nProvided name already exists", Toast.LENGTH_SHORT).show();
            playerName.setHint("PlayerName");
            return;
        }
        playersList.add(playerName.getText().toString());
        //////////////////////////////////////////////////////////////////////////

        // Let's display playersList
        //////////////////////////////////////////////////////////////////////////
        ListView listView = findViewById(R.id.playersListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playersList);
        listView.setAdapter(adapter);
        //////////////////////////////////////////////////////////////////////////

    }
}