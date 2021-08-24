package com.example.resistance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class ShowRoles extends AppCompatActivity {

    TextView passRole;
    Button iGotIt;
    LinkedList<String> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_roles);

    }

//    @SuppressWarnings("unchecked")
    public void iGotItButtonClick(View view) {
        passRole = findViewById(R.id.passRole);
//        passRole.setText(getIntent().getStringArrayListExtra("players").get(0)); // This is how we access the list of players
    }
}