package com.example.projet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Bundle bundle = getIntent().getExtras();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String pseudo = prefs.getString("pseudo", null);
       /* final String pseudo = bundle.getString("KEY_PSEUDO");*/
        TextView name = (TextView) findViewById(R.id.pseudo);
        name.setText(pseudo);

       /*final String mail = bundle.getString("KEY_MAIL");*/

        TextView create = (TextView) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotocreate = new Intent(getApplicationContext(), CreateEventActivity.class);
                startActivity(gotocreate);
            }
        });

        TextView show_event = (TextView) findViewById(R.id.show_event);
        show_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goshowevent = new Intent(getApplicationContext(),LesEvenements.class);
                startActivity(goshowevent);
            }
        });

        TextView my_event = (TextView) findViewById(R.id.my_event);
        my_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goadminevent = new Intent(getApplicationContext(),ListEventAdminActivity.class);
                startActivity(goadminevent);
            }
        });

        TextView modif = (TextView) findViewById(R.id.modif);
        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gomodif = new Intent(getApplicationContext(),Modif_compte.class);
                startActivity(gomodif);
            }
        });

        TextView deconnect = (TextView) findViewById(R.id.déconnexion);
        deconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deconnect = new Intent(getApplicationContext(),MainActivity.class);
                deconnect.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(deconnect);
                finish();
            }
        });
    }
}
