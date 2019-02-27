package com.example.projet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
        setContentView(R.layout.activity_listeventadmin);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_mes_events);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String id = prefs.getString("id", "");
        final String pseudo = prefs.getString("pseudo", "");

        eventList  = new ArrayList<>();
        gridViewList = (GridView) findViewById(R.id.mygridview);
        String url = "http://10.0.2.2:8080/api/evenement/getAll/userCreator/"+id;
        Ion.with(ListEventAdminActivity.this)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        Iterator it = result.iterator();
                        while (it.hasNext()){
                            JsonObject event = (JsonObject) it.next();
                            eventList.add(new Evenement(event.get("id").getAsString(),event.get("title").getAsString(),
                                    event.get("date").getAsString(),event.get("place").getAsString(),
                                    "blablabla",pseudo));

                        }
                    }
                });

        EventListAdminAdapter myAdapter=new EventListAdminAdapter(this,R.layout.grid_item,eventList);
        gridViewList.setAdapter(myAdapter);


        gridViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evenement tmp = (Evenement) gridViewList.getItemAtPosition(position);
                Intent itemIntent = new Intent(ListEventAdminActivity.this, detail_event_admin.class);

                itemIntent.putExtra("eventid", tmp.getId());
                startActivity(itemIntent);
            }
        });

    }
}
