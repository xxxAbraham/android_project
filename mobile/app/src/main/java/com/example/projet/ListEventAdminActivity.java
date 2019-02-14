package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.projet.model.Evenement;
import com.example.projet.model.EventListAdminAdapter;

import java.util.ArrayList;

public class ListEventAdminActivity extends AppCompatActivity {

    GridView gridViewList;
    ArrayList<Evenement> eventList = new ArrayList<>();


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


        gridViewList = (GridView) findViewById(R.id.mygridview);

        this.eventList.add (new Evenement("event1", "1","2","3","4","5"));
        this.eventList.add (new Evenement("event2", "1","2","3","4","5"));
        this.eventList.add (new Evenement("event3", "1","2","3","4","5"));
        this.eventList.add (new Evenement("event4", "1","2","3","4","5"));
        this.eventList.add (new Evenement("event5", "1","2","3","4","5"));
        this.eventList.add (new Evenement("event6", "1","2","3","4","5"));
        this.eventList.add (new Evenement("event7", "1","2","3","4","5"));
        this.eventList.add (new Evenement("event8", "1","2","3","4","5"));
        this.eventList.add (new Evenement("event9", "1","2","3","4","5"));

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            String name = extras.getString("theName");
            String date = extras.getString("theDate");
            String adresse = extras.getString("theAdress");
            String codePost = extras.getString("theCP");
            String ville = extras.getString("theVille");
            String desc = extras.getString("theDesc");

            eventList.add(new Evenement(name, date, adresse, codePost, ville, desc));
        }


        EventListAdminAdapter myAdapter=new EventListAdminAdapter(this,R.layout.grid_item,eventList);
        gridViewList.setAdapter(myAdapter);









    }
}
