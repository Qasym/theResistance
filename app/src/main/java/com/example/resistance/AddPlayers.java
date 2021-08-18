package com.example.resistance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
            playerName.setText("");
            playerName.setHint("PlayerName");
            return;
        }
        else if (playersList.contains(playerName.getText().toString().trim())) { // if playerName is repeated
            Toast.makeText(this, "Please, enter a different name!\nProvided name already exists", Toast.LENGTH_SHORT).show();
            playerName.setText("");
            playerName.setHint("PlayerName");
            return;
        }
        else if (playersList.size() == 9) { // if maximum # of players is reached
            Toast.makeText(this, "Maximum players limit reached!", Toast.LENGTH_LONG).show();
            playerName.setText("");
            playerName.setHint("PlayerName");
            return;
        }
        playersList.add(playerName.getText().toString().trim());

        playerName.setText("");
        playerName.setHint("PlayerName"); //This is needed to avoid user from manual erasing
        //////////////////////////////////////////////////////////////////////////

        // Let's display playersList
        //////////////////////////////////////////////////////////////////////////
        ListView listView = findViewById(R.id.playersListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playersList);
        listView.setAdapter(adapter);
        //////////////////////////////////////////////////////////////////////////

        // Let's make players removable, for now we just need to click on them
        //////////////////////////////////////////////////////////////////////////
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                playersList.remove(position); // removes the player on position

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddPlayers.this, android.R.layout.simple_list_item_1, playersList);
                listView.setAdapter(adapter);
            }
        });
        //////////////////////////////////////////////////////////////////////////

    }

    /*
    * This is a playButton Callback
    * We need to pass information from this activity to the next
    *
    *
    * */
    public void playButtonClick(View view) {
        if (playersList.size() < 5) {
            Toast.makeText(this, "Add more players to proceed!", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}