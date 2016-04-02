package com.example.fkrt.tophelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class VoteActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    // vote
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "Thanks Your For Your Contribution", Toast.LENGTH_LONG).show();
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
