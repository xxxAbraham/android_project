package com.example.projet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet.model.Evenement;
import com.example.projet.model.EventAdapter;
import com.example.projet.model.EventListAdminAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Iterator;

public class LesEvenements extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
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
    ListView mlistView;
    private ArrayList<Evenement> eventList;
    private EventAdapter monAdapter;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_les_evenements);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_les_events);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String pseudo = prefs.getString("pseudo", "");
        eventList  = new ArrayList<Evenement>();
        mlistView = (ListView) findViewById(R.id.dynamic);
        monAdapter = new EventAdapter( this , R.layout.item_les_evenements);
        mlistView.setAdapter(monAdapter);
        final String id = prefs.getString("id", "");


        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evenement tmp = (Evenement) mlistView.getItemAtPosition(position);
                Intent itemIntent = new Intent(LesEvenements.this, Detail_event.class);

                itemIntent.putExtra("eventid", tmp.getId());
                startActivity(itemIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final ArrayList<Evenement> resultEvent = new ArrayList<>();
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait");
        dialog.setMessage("Currently downloading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        String url = "http://10.0.2.2:8080/api/evenement/getAll/user/"+prefs.getString("id","");
        Ion.with(LesEvenements.this)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (result == null) {
                            Toast.makeText(getApplicationContext(), "Pas d'invitations", Toast.LENGTH_SHORT).show();
                        } else {
                            Iterator it = result.iterator();
                            while (it.hasNext()) {
                                JsonObject event = (JsonObject) it.next();
                                System.out.println(event);
                                resultEvent.add(new Evenement(event.get("id").getAsString(),
                                        event.get("title").getAsString(),
                                        event.get("date").getAsString(), event.get("place").getAsString(),
                                        "blablabla", "Quelqu'un" ));
                            }
                        }
                        LesEvenements.this.populate(resultEvent);
                    }
                });
    }

    public void populate(ArrayList<Evenement> listEvent){
        this.monAdapter.clear();
        this.monAdapter.addAll(listEvent);
        this.monAdapter.notifyDataSetChanged();
    }
}
