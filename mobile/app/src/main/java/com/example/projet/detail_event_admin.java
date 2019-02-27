package com.example.projet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projet.model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class detail_event_admin extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ArrayList<User> invites;
    TextView nom_event, pseudo, description, budget, balance;
    ListView list_invites;
    ArrayAdapterDetailAdmin myadpater;
    String eventid = "";
    FloatingActionButton plus;
    Button donner;
    ImageButton reglage, retour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event_admin);
        invites = new ArrayList<User>();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        nom_event = findViewById(R.id.nom_event);
        pseudo = findViewById(R.id.pseudo_createur);
        description = findViewById(R.id.description);
        budget = findViewById(R.id.budget);
        balance = findViewById(R.id.balance);
        list_invites = findViewById(R.id.recyclerView);
        retour = findViewById(R.id.retour);
        donner = findViewById(R.id.donner);
        plus = findViewById(R.id.fab);
        reglage = findViewById(R.id.reglage);

        pseudo.setText(prefs.getString("pseudo", ""));
        final Intent intent = getIntent();
        if(intent.hasExtra("eventid")){
            eventid = intent.getStringExtra("eventid");
        }

        String url = "http://10.0.2.2:8080/api/evenement/get/"+eventid;
        Ion.with(detail_event_admin.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        String nom = result.get("title").getAsString();
                        Object tmp =result.get("decription");
                        if(tmp != null){
                            String desc = result.get("description").getAsString();
                            description.setText(desc);
                        }
                        nom_event.setText(nom);
                        JsonArray userList = result.get("userList").getAsJsonArray();
                        Iterator it = userList.iterator();
                        while (it.hasNext()){
                            JsonObject jsonUser = (JsonObject) it.next();
                            invites.add(new User(jsonUser.get("username").getAsString(), jsonUser.get("id").getAsString()));
                        }
                    }});
      /*  String url2 = "http://10.0.2.2:8080/api/depense/getExpenseTotal/"+eventid;
        Ion.with(detail_event_admin.this)
                .load(url2)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Double total = result.get("total").getAsDouble();
                        budget.setText(total.toString());
                    }});

        String url3 = "http://10.0.2.2:8080/api/expenseManager/getMax/"+prefs.getString("id","")+"/"+eventid;
        Ion.with(detail_event_admin.this)
                .load(url2)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Double bal = result.get("owing").getAsDouble();
                        balance.setText(bal.toString());
                    }});
*/
        myadpater = new ArrayAdapterDetailAdmin(this,invites);
        list_invites.setAdapter(myadpater);

        reglage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentreglage = new Intent(detail_event_admin.this, ModifEventActivity.class);
                intentreglage.putExtra("eventid",eventid);
                startActivity(intentreglage);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentplus = new Intent(detail_event_admin.this,ListPseudoAdminActivity.class);
                intentplus.putExtra("eventid",eventid);
                startActivity(intentplus);
            }
        });

        donner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
