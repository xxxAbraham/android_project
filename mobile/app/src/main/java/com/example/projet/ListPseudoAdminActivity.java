package com.example.projet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet.model.Evenement;
import com.example.projet.model.EventListAdminAdapter;
import com.example.projet.model.PseudoListAdminAdapter;
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
    ArrayList<User> pseudoList = new ArrayList<User>();
    String eventid = "";
    TextView pseudo_creator;
    FloatingActionButton btnajoutpseudo;
    PseudoListAdminAdapter myAdapter;
    EditText pseudoajouter;
    String idpseudo = "";
    String nompseudo = "";
    ImageButton retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpseudoadmin);
        final Intent intent = getIntent();
        if(intent.hasExtra("eventid")){
            eventid = intent.getStringExtra("eventid");
        }

        retour = findViewById(R.id.retour);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String pseudo = prefs.getString("pseudo", "");
        pseudo_creator = (TextView) findViewById(R.id.pseudo_createur);
        pseudo_creator.setText(pseudo);
        pseudoList  = new ArrayList<>();
        listViewPseudo = (ListView) findViewById(R.id.listPseudo);
        String url = getString(R.string.urlHttp)+"/api/evenement/get/"+eventid;
        Ion.with(ListPseudoAdminActivity.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                                 @Override
                                 public void onCompleted(Exception e, JsonObject result) {
                                     JsonArray a = result.get("userList").getAsJsonArray();
                                     Iterator<JsonElement> iv = a.iterator();
                                     while (iv.hasNext()){
                                         JsonObject o = iv.next().getAsJsonObject();
                                         String m = o.get("username").getAsString();
                                         String id = o.get("id").getAsString();
                                         pseudoList.add(new User(m, id));
                                     }
                                 }
                             });
        myAdapter = new PseudoListAdminAdapter(this, R.layout.item_listpseudoadmin
                , pseudoList);
        listViewPseudo.setAdapter(myAdapter);

        listViewPseudo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        ListPseudoAdminActivity.this);
                alertDialog2.setTitle("Confirm Delete...");
                alertDialog2.setMessage("Are you sure you want delete this file?");
                alertDialog2.setIcon(R.drawable.ic_delete_36dp);

                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                final JsonObject json = new JsonObject();
                                json.addProperty("idObject", pseudoList.get(i).getId());
                                json.addProperty("typeObject", "user");
                                String url = getString(R.string.urlHttp)+"/api/evenement/removeUser/"+eventid;
                                Ion.with(ListPseudoAdminActivity.this)
                                        .load("PUT",url)
                                        .setJsonObjectBody(json)
                                        .asJsonObject()
                                        .setCallback(new FutureCallback<JsonObject>() {
                                            @Override
                                            public void onCompleted(Exception e, JsonObject result) {
                                                pseudoList.remove(pseudoList.get(i));
                                                myAdapter.notifyDataSetChanged();

                                            }
                                        });
                            }
                        });
                alertDialog2.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog2.show();
            }
        });




        btnajoutpseudo = (FloatingActionButton) findViewById(R.id.fab);
        btnajoutpseudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(ListPseudoAdminActivity.this);
                    builder.setTitle("Ajout pseudo à l'event");

                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogLayout = inflater.inflate(R.layout.dialog_addpseudoevent, null);
                    builder.setView(dialogLayout);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            pseudoajouter = dialogLayout.findViewById(R.id.ajoutpseudoET);
                            String recup = pseudoajouter.getText().toString();

                            String url2 = getString(R.string.urlHttp)+"/api/membre/get/pseudo/"+recup;
                            Ion.with(ListPseudoAdminActivity.this)
                                    .load("GET",url2)
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            if(!result.get("id").isJsonNull()) {
                                                idpseudo = result.get("id").getAsString();
                                                nompseudo = result.get("username").getAsString();


                                                final JsonObject json = new JsonObject();
                                                json.addProperty("idObject", idpseudo);
                                                json.addProperty("typeObject", "user");
                                                String url = getString(R.string.urlHttp)+"/api/evenement/addUser/" + eventid;
                                                Ion.with(ListPseudoAdminActivity.this)
                                                        .load("PUT", url)
                                                        .setJsonObjectBody(json)
                                                        .asJsonObject()
                                                        .setCallback(new FutureCallback<JsonObject>() {
                                                            @Override
                                                            public void onCompleted(Exception e, JsonObject result) {
                                                                pseudoList.add(new User(nompseudo, idpseudo));
                                                                myAdapter.notifyDataSetChanged();
                                                            }
                                                        });
                                            }
                                            else{
                                                Toast.makeText(ListPseudoAdminActivity.this,"Ce pseudo n'existe pas", Toast.LENGTH_SHORT).show();
                                            }
                                        }});
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            builder.setCancelable(true);
                        }
                    });
                    builder.show();

            }
        });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(123);
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(123);
    }
}

