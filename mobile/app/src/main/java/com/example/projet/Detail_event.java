package com.example.projet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class Detail_event extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ArrayList<User> invites;
    TextView nom_event, pseudo, description, budget, balance;
    ListView list_invites;
    ArrayAdapterDetail myadpater;
    String eventid = "",idpseudo ="";
    FloatingActionButton plus;
    Button donner;
    ImageButton reglage, retour;
    TextView date, adresse;

    FloatingActionButton participe;

    ArrayList<User> pseudoList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        invites = new ArrayList<User>();
        final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        nom_event = findViewById(R.id.titreEvent);
        pseudo = findViewById(R.id.pseudo_createur);
        description = findViewById(R.id.description);
        budget = findViewById(R.id.budget);
        balance = findViewById(R.id.balance);
        list_invites = findViewById(R.id.recyclerView);
        donner = findViewById(R.id.donner);
        retour = findViewById(R.id.retour);
        date = findViewById(R.id.date);
        adresse = findViewById(R.id.adresse);
        participe = findViewById(R.id.fab);


        idpseudo = prefs.getString("id", "");


        final Intent intent = getIntent();
        if(intent.hasExtra("eventid")){
            eventid = intent.getStringExtra("eventid");
        }

        String url = getString(R.string.urlHttp)+"/api/evenement/get/"+eventid;
        Ion.with(Detail_event.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        String nom = result.get("title").getAsString();

                            String desc = result.get("description").getAsString();
                            description.setText(desc);

                        nom_event.setText(nom);
                        JsonArray userList = result.get("userList").getAsJsonArray();
                        Iterator it = userList.iterator();
                        JsonObject creator = result.get("user").getAsJsonObject();
                        String namecreator = creator.get("username").getAsString();
                        invites.add(new User(namecreator,creator.get("id").getAsString()));
                        while (it.hasNext()){
                            JsonObject jsonUser = (JsonObject) it.next();
                            invites.add(new User(jsonUser.get("username").getAsString(), jsonUser.get("id").getAsString()));
                        }

                        String theDate = result.get("date").getAsString();
                        String theAdresse = result.get("place").getAsString();

                        if (theDate != null){
                            date.setText(theDate);
                        }

                        if (theAdresse != null){
                            adresse.setText(theAdresse);
                        }
                        if(namecreator !=null){
                            pseudo.setText(namecreator);
                        }
                    }});

        myadpater = new ArrayAdapterDetail(this,invites);
        list_invites.setAdapter(myadpater);
        list_invites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User tmp = (User) list_invites.getItemAtPosition(position);
                Intent detailPseudo = new Intent(Detail_event.this, details_pseudo_event.class);
                detailPseudo.putExtra("eventid", eventid);
                detailPseudo.putExtra("pseudoid", tmp.getId());
                startActivity(detailPseudo);
            }
        });
        donner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder dialogDonner = new AlertDialog.Builder(
                        Detail_event.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogLayout = inflater.inflate(R.layout.dialog_adddepense, null);
                dialogDonner.setView(dialogLayout);
                final EditText motif = dialogLayout.findViewById(R.id.motif);
                final EditText montant = dialogLayout.findViewById(R.id.montant);

                dialogDonner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String strMotif = motif.getText().toString();
                        String strMontant = montant.getText().toString();
                        Double dbMontant = Double.parseDouble(strMontant);

                        final JsonObject json = new JsonObject();
                        json.addProperty("amount", dbMontant);
                        json.addProperty("wording", strMotif);

                        json.addProperty("userId", idpseudo);
                        json.addProperty("eventId", eventid);

                        String url2 = getString(R.string.urlHttp)+"/api/depense/add";
                        Ion.with(Detail_event.this)
                                .load("POST",url2)
                                .setJsonObjectBody(json)
                                .asJsonObject()
                                .setCallback(new FutureCallback<JsonObject>() {
                                    @Override
                                    public void onCompleted(Exception e, JsonObject result) {
                                        Detail_event.this.onStart();

                                    }});

                    }
                });

                dialogDonner.show();
            }
        });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        participe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        Detail_event.this);
                alertDialog2.setTitle("Je participe?");
                alertDialog2.setMessage("Participez-vous?");
                alertDialog2.setIcon(R.drawable.ic_delete_36dp);

                alertDialog2.setPositiveButton("NON",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, final int which) {

                                final JsonObject json = new JsonObject();
                                json.addProperty("idObject", prefs.getString("id",""));
                                json.addProperty("typeObject", "user");
                                String url = getString(R.string.urlHttp)+"/api/evenement/removeUser/"+eventid;
                                Ion.with(Detail_event.this)
                                        .load("PUT",url)
                                        .setJsonObjectBody(json)
                                        .asJsonObject()
                                        .setCallback(new FutureCallback<JsonObject>() {
                                            @Override
                                            public void onCompleted(Exception e, JsonObject result) {
                                                myadpater.notifyDataSetChanged();
                                                finish();

                                            }
                                        });
                            }
                        });
                alertDialog2.setNegativeButton("OUI",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog2.show();
                onRestart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String url2 = getString(R.string.urlHttp)+"/api/depense/getExpenseTotal/"+eventid;
        Ion.with(Detail_event.this)
                .load(url2)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Double total = result.get("total").getAsDouble();
                        budget.setText(total.toString()+ " euros");
                    }});

        String url3 = getString(R.string.urlHttp)+"/api/owing/get/"+idpseudo+"/"+eventid;
        Ion.with(Detail_event.this)
                .load(url3)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Double bal = result.get("owing").getAsDouble();
                        bal *= -1;
                        java.text.DecimalFormat df = new java.text.DecimalFormat("0.##");
                        balance.setText(df.format(bal)+ " euros");
                        if(bal>0){
                            balance.setTextColor(Color.RED);
                        }
                        else if (bal <0) {
                            balance.setTextColor(Color.GREEN);
                        }

                    }});
    }
}
