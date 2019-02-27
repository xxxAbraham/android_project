package com.example.projet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Detail_event extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        TextView title = (TextView) findViewById(R.id.titreEvent);
        TextView date = (TextView) findViewById(R.id.date);
        TextView adresse = (TextView) findViewById(R.id.adresse);
        TextView creatorName = (TextView) findViewById(R.id.pseudo_createur);
        TextView description = (TextView) findViewById(R.id.description);



    }
}
