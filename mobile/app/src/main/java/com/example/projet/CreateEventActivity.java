package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class CreateEventActivity extends AppCompatActivity {
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

            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setSelectedItemId(R.id.navigation_creer);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


}