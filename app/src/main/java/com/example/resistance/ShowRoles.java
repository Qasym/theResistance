package com.example.resistance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

@SuppressLint("SetTextI18n")
public class ShowRoles extends AppCompatActivity {

    TextView passRole; // our TextView, it shows the "pass phone to name1" and the role, hence the name
    Button iGotIt; // button, it changes its text to "I am name1" and "Got it", hence the name
    boolean showingRole = false; // initially it is false, since initially it doesn't show any role
    int currentIndex = 0; //index of the current player
    String currentPlayer; //current player, intuitive

    GameEngine gameEngine; //game engine where everything is stored
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_roles);
        
        // This is how we access the list of players from other activity
        gameEngine = new GameEngine(getIntent().getStringArrayListExtra("players"));

        passRole = findViewById(R.id.passRole);
        iGotIt = findViewById(R.id.iGotIt);

        currentPlayer = gameEngine.getPlayers().get(currentIndex);
        passRole.setText("Pass phone to " + currentPlayer);
        iGotIt.setText("I am " + currentPlayer);
    }

    /*
    * This button callback switches what players see
    * It has 2 "windows"
    * First one contains the text like "Pass phone to *Name1* player
    * Second one shows the role
    * In both windows button text is changed
    * But Callback does not, this method has to behave
    * in 2 different ways depending on what window has showed up
    * Initially it is the first window*/
    public void iGotItButtonClick(View view) {
        if (showingRole) { //if this activity is showing the role
            showingRole = false;

            if (currentIndex == gameEngine.getPlayers().size()) {
                // We will change this soon, it will go to the next activity
                // not to the MainActivity;
                Intent intent = new Intent(this, TeamFormation.class);
                intent.putExtra("gameEngine", gameEngine);
                startActivity(intent);
                finish();
            }

            currentPlayer = gameEngine.getPlayers().get(++currentIndex);

            passRole.setText("Please pass phone to " + currentPlayer);
            iGotIt.setText("I am " + currentPlayer);
        }
        else { //if this activity is not showing the role, *this is executed first*
            showingRole = true;

            if (gameEngine.getSpies().contains(currentPlayer)) {
                StringBuilder otherSpies = new StringBuilder();
                for (String spy : gameEngine.getSpies()) {
                    if (!spy.equals(currentPlayer)) otherSpies.append(spy).append("\n");
                }
                passRole.setText("You are a spy\n" + "Other spies are:\n" + otherSpies);
            }
            else passRole.setText("You are a part of the Resistance");

            iGotIt.setText("Got it");
        }
    }
}