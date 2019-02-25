package com.example.projet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.projet.model.Evenement;
import com.example.projet.model.EventListAdminAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;

public class ListEventAdminActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    GridView gridViewList;
    private ArrayList<Evenement> eventList;


    Intent bottomNavigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_accueil:
                    finish();
                    return true;
                case R.id.navigation_creer:
                    bottomNavigation = new Intent(getApplicationContext(), CreateEventActivity.class);
                    startActivity(bottomNavigation);
                    finish();
                    return true;
                case R.id.navigation_les_events:
                    bottomNavigation = new Intent(getApplicationContext(), LesEvenements.class);
                    startActivity(bottomNavigation);
                    finish();
                    return true;
                case R.id.navigation_mes_events:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listeventadmin);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String pseudo = prefs.getString("pseudo", null);
        eventList  = new ArrayList<>();
        gridViewList = (GridView) findViewById(R.id.mygridview);
        String url = "http://10.0.2.2:8080/api/evenement/get/pseudo/"+pseudo;
        Ion.with(ListEventAdminActivity.this)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        Iterator it = result.iterator();
                        while (it.hasNext()){
                            JsonObject event = (JsonObject) it.next();
                            eventList.add(new Evenement(event.get("title").getAsString(),
                                    event.get("date").getAsString(),event.get("place").getAsString(),
                                    "blablabla"));
                        }
                    }
                });

        EventListAdminAdapter myAdapter=new EventListAdminAdapter(this,R.layout.grid_item,eventList);
        gridViewList.setAdapter(myAdapter);









    }
}
