package com.example.projet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projet.model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class details_pseudo_event extends AppCompatActivity {
    ArrayList<String> depenses;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_pseudo_event);
       Intent intent = getIntent();
        String eventid = intent.getStringExtra("eventid");
        String pseudoid = intent.getStringExtra("pseudoid");
        String url1 = "http://10.0.2.2:8080/api/membre/get/"+pseudoid;
        final TextView pseudo = (TextView) findViewById(R.id.titreEvent);
        final ListView lview = (ListView) findViewById(R.id.list_dep);
        depenses = new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                depenses);
        lview.setAdapter(adapter);
        Ion.with(details_pseudo_event.this)
                .load(url1)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        pseudo.setText(result.get("username").toString());
                    }
                });
        String url = "http://10.0.2.2:8080/api/depense/get/eventuser/"+pseudoid+"/"+eventid;
        Ion.with(details_pseudo_event.this)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                    Iterator it = result.iterator();
                    while(it.hasNext()){
                        JsonObject item = (JsonObject) it.next();
                        String somme = item.get("wording").getAsString() + " : "+item.get("amount").getAsString();
                        depenses.add(somme);
                    }
                    adapter.notifyDataSetChanged();
                    }
                });
    }
}

