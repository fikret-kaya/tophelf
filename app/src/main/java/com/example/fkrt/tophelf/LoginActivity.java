package com.example.fkrt.tophelf;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {
    //deneme
    private TextView registerTV;
    private EditText email,password;
    Intent intent;
    Context context = this;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private LoginButton loginButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private CallbackManager mCallbackManager;
    /*private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            if(profile != null){
                mTextDetails.setText("Welcome " + profile.getName());
                intent = new Intent(context, MainActivity.class);
                intent.putExtra("name", profile.getName());
                context.startActivity(intent);
            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.content_login);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends", "email");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                if(profile != null){
                    intent = new Intent(context, MainActivity.class);
                    intent.putExtra("name", profile.getName());
                    context.startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        registerTV = (TextView) findViewById(R.id.registerTV);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {

            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    // login now
    public void onClick(View v) throws IOException, JSONException, ExecutionException, InterruptedException {

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        if(email.getText().toString().equals("") || password.getText().toString().equals("")) {
            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Warning!");
            alertDialog.setMessage("Email or Password cannot be empty!");
            alertDialog.setIcon(R.drawable.tophelf);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        } else {
            boolean b = new LoginConn().execute(email.getText().toString(), password.getText().toString()).get();
            if(!b) {
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Warning!");
                alertDialog.setMessage("Something went wrong, please try again!");
                alertDialog.setIcon(R.drawable.tophelf);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }

    }
    // register now
    public void onClick2(View v) {
        intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


    // forget password
    public void onClick4(View v) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Password Assistance");
        alertDialog.setMessage("Please enter registered email address");
        alertDialog.setIcon(R.drawable.tophelf);

        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        alertDialog.setView(editText);

        // Submit button
        alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = editText.getText().toString();
                Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
            }
        });

        // Cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();

    }

    @Override
    public void onStart() {
        super.onStart();
        Profile profile = Profile.getCurrentProfile();
    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    //  Server connectıon
    class LoginConn extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String email = params[0];
            String password = params[1];

            try {
                URL url = new URL("http://139.179.211.68:3000/"); // 192.168.1.24 --- 10.0.2.2 --- 139.179.211.68
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestProperty("Content-Type", "application/json");
                conn.connect();

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("type", "UserCheck");
                jsonParam.put("email", email);
                jsonParam.put("pass", password);

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
                    // Create an InputStream in order to extract the response object
                    is = conn.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line, responseString;
                    StringBuffer response = new StringBuffer();
                    while((line = rd.readLine()) != null) {
                        response.append(line);
                    }
                    rd.close();
                    responseString = response.toString();
                    responseString =responseString.substring(1,response.length()-1);

                    jsonParam = new JSONObject(responseString);
                    int u_id = Integer.parseInt(jsonParam.getString("u_id"));

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
