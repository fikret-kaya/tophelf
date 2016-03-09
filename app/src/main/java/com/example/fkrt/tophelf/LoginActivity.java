package com.example.fkrt.tophelf;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView registerTV;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        registerTV = (TextView) findViewById(R.id.registerTV);
    }

    // login now
    public void onClick(View v) {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // register now
    public void onClick2(View v) {
        intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    // Login with facebook
    public void onClick3(View v) {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // forget password
    public void onClick4(View v) {
        intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
