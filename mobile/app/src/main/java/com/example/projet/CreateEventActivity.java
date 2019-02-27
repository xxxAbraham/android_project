package com.example.projet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Calendar;
import java.util.Date;

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Intent bottomNavigation;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    Calendar d = Calendar.getInstance();
    TextView date;
    String year;
    String month;
    String day;
    DatePickerDialog datePickerDialog;
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

        datePickerDialog = new DatePickerDialog(
                CreateEventActivity.this, CreateEventActivity.this, d.get(Calendar.YEAR), d.get(Calendar.MONTH), d.get(Calendar.DAY_OF_MONTH));
        final TextView name = (TextView) findViewById(R.id.txt_name);

        date = (TextView) findViewById(R.id.txt_date);
        final TextView adresse = (TextView) findViewById(R.id.txt_adresse);
        final TextView codePost = (TextView) findViewById(R.id.txt_cp);
        final TextView ville = (TextView) findViewById(R.id.txt_ville);
        final TextView desc = (TextView) findViewById(R.id.txt_description);

        final Button validate = (Button) findViewById(R.id.btnFinalCreate);
        final Intent creating = new Intent(this, ListEventAdminActivity.class);

        date.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        datePickerDialog.show();
                                        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setText("Fermer");
                                        return true;
                                    }
                                });

                validate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View focusView = null;
                        boolean cancel = false;
                        if (TextUtils.isEmpty(name.getText())) {
                            name.setError("Entrez un nom!");
                            focusView = name;
                            cancel = true;
                        }
                        if (TextUtils.isEmpty(date.getText())) {
                            date.setError("Entrez une date (yyyy-mm-dd)!");
                            focusView = date;
                            cancel = true;
                        }
                        if (TextUtils.isEmpty(adresse.getText())) {
                            adresse.setError("Entrez une adresse!");
                            focusView = adresse;
                            cancel = true;
                        }
                        if (TextUtils.isEmpty(codePost.getText())) {
                            codePost.setError("Entrez un code postal!");
                            focusView = codePost;
                            cancel = true;
                        }
                        if (TextUtils.isEmpty(ville.getText())) {
                            ville.setError("Entrez un nom de ville!");
                            focusView = ville;
                            cancel = true;
                        }
                        if (cancel) {
                            focusView.requestFocus();
                        } else {
                            String url = "http://10.0.2.2:8080/api/evenement/add";
                            final JsonObject json = new JsonObject();
                            json.addProperty("userId", pseudo);
                            json.addProperty("title", name.getText().toString());
                            json.addProperty("date", date.getText().toString());
                            json.addProperty("place", adresse.getText().toString() + " "
                                    + codePost.getText().toString() + " " + ville.getText().toString());
                            json.addProperty("description", desc.getText().toString());
                            Ion.with(CreateEventActivity.this)
                                    .load(url)
                                    .setJsonObjectBody(json)
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            if (result.get("ok").getAsBoolean()) {
                                                startActivity(creating);
                                                finish();
                                            } else {
                                                Toast.makeText(CreateEventActivity.this, "Champs incorecte",
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