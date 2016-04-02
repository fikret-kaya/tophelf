package com.example.fkrt.tophelf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class VoteActivity extends AppCompatActivity {

    private Intent intent;

    private String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        latitude = bundle.getString("latitude");
        longitude = bundle.getString("longitude");

    }

    // vote
    public void onClick(View v) throws ExecutionException, InterruptedException {
        Toast.makeText(getApplicationContext(), "Thanks Your For Your Contribution", Toast.LENGTH_LONG).show();

        boolean b = new Voteconn().execute("1","12","31","kyma","burger","cilgindi","5").get();

        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    class Voteconn extends AsyncTask<Object, Void, Boolean>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            String u_id = (String) params[0];
            String latitude = (String) params[1];
            String longitude = (String) params[2];
            String placeName = (String) params[3];
            String tagName = (String) params[4];
            String comment = (String) params[5];
            String rating = (String) params[6];

            try {
                URL url = new URL("http://139.179.211.68:3000"); // 192.168.1.24 --- 10.0.2.2
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestProperty("Content-Type", "application/json");
                conn.connect();

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("type", "Vote");
                jsonParam.put("u_id", u_id);
                jsonParam.put("location", latitude+"-"+longitude);
                jsonParam.put("placename", placeName);
                jsonParam.put("tagname", tagName);
                jsonParam.put("comment", comment);
                jsonParam.put("rating", rating);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonParam.toString()); // URLEncoder.encode(jsonParam.toString(), "UTF-8")
                writer.flush();
                writer.close();
                os.close();

                int statusCode = conn.getResponseCode();
                InputStream is = null;

                if (statusCode >= 200 && statusCode < 400) {
                    // zzzzzzzz
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    return true;
                }
                else {
                    is = conn.getErrorStream();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

}
