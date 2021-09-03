package com.example.resistance;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class DisplayRules extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_rules);

        // Let's add back button
        //////////////////////////////////////////////////////////////////////////
        Toolbar toolbar = findViewById(R.id.rulesActionBar);
        setSupportActionBar(toolbar); // This shows toolbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true); // This shows "back-button" in the toolbar
        //////////////////////////////////////////////////////////////////////////

        ((TextView) findViewById(R.id.rules)).setMovementMethod(new ScrollingMovementMethod());
    }

    /*
    * This method is a callback for the
    * "back-button" we added in the toolbar
    *
    * It simply returns us to the MainActivity
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
        finish();
        return true;
    }
}