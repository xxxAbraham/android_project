package com.example.projet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class CreateEventActivity extends AppCompatActivity {
    Intent bottomNavigation;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_accueil:
                    finish();
                    return true;
                case R.id.navigation_creer:
                    return true;
                case R.id.navigation_les_events:
                    bottomNavigation = new Intent(getApplicationContext(), LesEvenements.class);
                    startActivity(bottomNavigation);
                    finish();
                    return true;
                case R.id.navigation_mes_events:
                    bottomNavigation = new Intent(getApplicationContext(), ListEventAdminActivity.class);
                    startActivity(bottomNavigation);
                    finish();
                    return true;
            }
            return false;
        }

    };
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            final String pseudo = prefs.getString("id", null);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setSelectedItemId(R.id.navigation_creer);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        final TextView name = (TextView) findViewById(R.id.txt_name);
        final TextView date = (TextView) findViewById(R.id.txt_date);
        final TextView adresse = (TextView) findViewById(R.id.txt_adresse);
        final TextView codePost = (TextView) findViewById(R.id.txt_cp);
        final TextView ville = (TextView) findViewById(R.id.txt_ville);
        final TextView desc = (TextView) findViewById(R.id.txt_description);

        final Button validate = (Button) findViewById(R.id.btnFinalCreate);
        final Intent creating = new Intent(this, ListEventAdminActivity.class);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2:8080/api/evenement/add";
                final JsonObject json = new JsonObject();
                json.addProperty("userId", pseudo);
                json.addProperty("title", name.getText().toString());
                json.addProperty("date", date.getText().toString());
                json.addProperty("place", adresse.getText().toString()+" "
                        + codePost.getText().toString()+" "+ ville.getText().toString());
                Ion.with(CreateEventActivity.this)
                        .load(url)
                        .setJsonObjectBody(json)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (result.get("ok").getAsBoolean()) {
                                    startActivity(creating);
                                }
                                else{
                                    Toast.makeText(CreateEventActivity.this, "Champs incorecte",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });





            }
        });

    }


}