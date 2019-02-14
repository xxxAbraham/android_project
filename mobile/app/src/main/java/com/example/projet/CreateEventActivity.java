package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);

        final TextView name = (TextView) findViewById(R.id.txt_name);
        final TextView date = (TextView) findViewById(R.id.txt_date);
        final TextView adresse = (TextView) findViewById(R.id.txt_adresse);
        final TextView codePost = (TextView) findViewById(R.id.txt_cp);
        final TextView ville = (TextView) findViewById(R.id.txt_ville);
        final TextView desc = (TextView) findViewById(R.id.txt_description);

        final Button validate = (Button) findViewById(R.id.btnFinalCreate);
        final Intent creating = new Intent(this, ListEventAdminActivity.class);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                creating.putExtra("theName", name.getText());
                creating.putExtra("theDate", date.getText());
                creating.putExtra("theAdress", adresse.getText());
                creating.putExtra("theCP", codePost.getText());
                creating.putExtra("theVille", ville.getText());
                creating.putExtra("theDesc", desc.getText());

                startActivity(creating);



            }
        });

    }


}