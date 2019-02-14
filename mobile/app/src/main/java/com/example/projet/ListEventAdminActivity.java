package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListEventAdminActivity extends AppCompatActivity {
    Intent bottomNavigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_accueil:
                    bottomNavigation = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(bottomNavigation);
                    finish();
                    return true;
                case R.id.navigation_creer:
                    bottomNavigation = new Intent(getApplicationContext(), CreateEventActivity.class);
                    startActivity(bottomNavigation);
                    finish();
                    return true;
                case R.id.navigation_les_events:
                    bottomNavigation = new Intent(getApplicationContext(), LesEvenements.class);
                    startActivity(bottomNavigation);
                    finish();
                    return true;
                case R.id.navigation_mes_events:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listeventadmin);

        TextView name = (TextView) findViewById(R.id.txt_name);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listeventadmin,R.id.name,StringArray);



    }
}
