package com.example.projet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListEventAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listeventadmin);

        TextView name = (TextView) findViewById(R.id.txt_name);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listeventadmin,R.id.name,StringArray);



    }
}
