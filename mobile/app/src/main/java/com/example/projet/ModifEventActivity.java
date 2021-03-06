package com.example.projet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet.model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Calendar;
import java.util.Iterator;

public class ModifEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    String eventid = "";
    TextView title, date, place, desc, supp;
    Calendar d = Calendar.getInstance();
    String year;
    String month;
    String day;
    ImageButton retour;
    DatePickerDialog datePickerDialog;
    Button valider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifevent);
        title = findViewById(R.id.txt_nameModif);
        date = findViewById(R.id.txt_dateModif);
        place = findViewById(R.id.txt_adresseModif);
        desc = findViewById(R.id.txt_descriptionModif);
        valider = findViewById(R.id.btnFinalModif);
        retour = findViewById(R.id.btnBack_modifevent);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final Intent intent = getIntent();
        if(intent.hasExtra("eventid")){
            eventid = intent.getStringExtra("eventid");
        }
        datePickerDialog = new DatePickerDialog(
                ModifEventActivity.this, ModifEventActivity.this, d.get(Calendar.YEAR), d.get(Calendar.MONTH), d.get(Calendar.DAY_OF_MONTH));

        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setText("Fermer");
                return true;
            }
        });

        String url = getString(R.string.urlHttp)+"/api/evenement/get/"+eventid;
        Ion.with(ModifEventActivity.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        String nom = result.get("title").getAsString();

                            String descript = result.get("description").getAsString();
                            desc.setText(descript);

                        title.setText(nom);
                        date.setText(result.get("date").getAsString());
                        place.setText(result.get("place").getAsString());
                        desc.setText(result.get("description").getAsString());

                    }});

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View focusView = null;
                boolean cancel = false;
                if (TextUtils.isEmpty(title.getText())) {
                    title.setError("Entrez un nom!");
                    focusView = title;
                    cancel = true;
                }
                if (TextUtils.isEmpty(date.getText())) {
                    date.setError("Entrez une date (yyyy-mm-dd)!");
                    focusView = date;
                    cancel = true;
                }
                if (TextUtils.isEmpty(place.getText())) {
                    place.setError("Entrez une adresse!");
                    focusView = place;
                    cancel = true;
                }
                if (TextUtils.isEmpty(desc.getText())) {
                    desc.setError("Entrez une description!");
                    focusView = desc;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                } else {
                    Log.e("date avant", date.getText().toString());
                    String urlmodif = getString(R.string.urlHttp)+"/api/evenement/update/" + eventid;
                    final JsonObject json = new JsonObject();
                    json.addProperty("userId", prefs.getString("id", ""));
                    json.addProperty("title", title.getText().toString());
                    json.addProperty("date", date.getText().toString());
                    json.addProperty("place", place.getText().toString());
                    json.addProperty("description", desc.getText().toString());
                    Ion.with(ModifEventActivity.this)
                            .load("PUT", urlmodif)
                            .setJsonObjectBody(json)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("ok").getAsBoolean()) {
                                        Log.e("date apres", date.getText().toString());
                                        finish();
                                    } else {
                                        Toast.makeText(ModifEventActivity.this, "Champs incorecte",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Log.i("ON DATA SET", "onDateSet: "+ i + " *** "+ i1 + " *** "+ i2);
        i1 ++;
        if (i1 < 10){
            month = "0"+i1;
        } else {
            month = ""+i1;
        }

        if(i2<10){
            day = "0"+i2;
        } else {
            day = ""+i2;
        }

        year = ""+i;


        Log.i("ON DATA SET", "onDateSet: "+ year + " *** "+ month + " *** "+ day);
        date.setText(year+"-"+month+"-"+day);
    }
}
