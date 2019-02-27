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
import android.widget.ListView;
import android.widget.TextView;

import com.example.projet.model.Evenement;
import com.example.projet.model.EventListAdminAdapter;
import com.example.projet.model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ListPseudoAdminActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    ListView listViewPseudo;
    private ArrayList pseudoList;
    String eventid = "";
    TextView pseudo_creator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpseudoadmin);
        final Intent intent = getIntent();
        if(intent.hasExtra("eventid")){
            eventid = intent.getStringExtra("eventid");
        }
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String pseudo = prefs.getString("id", "");
        pseudo_creator = (TextView) findViewById(R.id.pseudo_createur);
        pseudo_creator.setText(pseudo);

        pseudoList  = new ArrayList<>();
        listViewPseudo = (ListView) findViewById(R.id.listPseudo);
        String url = "http://localhost:8080/api/evenement/get/"+eventid;
        Ion.with(ListPseudoAdminActivity.this)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        Iterator<JsonElement> it = result.iterator();
                        while (it.hasNext()){
                            JsonObject event = it.next().getAsJsonObject();
                            JsonArray a = event.getAsJsonArray("listUser");
                            Iterator<JsonElement> iv = a.iterator();
                            while (iv.hasNext()){
                                JsonObject o = iv.next().getAsJsonObject();
                                String m = o.get("username").getAsString();
                                pseudoList.add(m);
                            }
                        }
                    }
                });

        EventListAdminAdapter myAdapter=new EventListAdminAdapter(this,R.layout.item_listpseudoadmin
                ,pseudoList);
        listViewPseudo.setAdapter(myAdapter);


    }
}

