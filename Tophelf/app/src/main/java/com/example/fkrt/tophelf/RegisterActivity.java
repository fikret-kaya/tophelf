package com.example.fkrt.tophelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    private Intent intent;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (Button) findViewById(R.id.register);
    }

    // register now
    public void onClick(View v) {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // check privacy policy
    public void onClick2(View v) {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
