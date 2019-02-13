package com.example.projet;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;



import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Iterator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 @author Abraham Gabsi
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView mNoAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mNoAccount = (TextView) findViewById(R.id.no_account);
        mNoAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent connect = new Intent(getApplicationContext(),InscriptionActivity.class);
                connect.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connect);
                finish();
            }
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        final Intent connect = new Intent(getApplicationContext(), MenuActivity.class);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("IDENTIFICATION");
                String url = "http://10.0.2.2/api/membre";
                Ion.with(getApplicationContext())
                        .load(url)
                        .asJsonArray()
                        .setCallback(new FutureCallback<JsonArray>() {
                            @Override
                            public void onCompleted(Exception e, JsonArray result) {
                                String email = mEmailView.getText().toString();
                                if (result == null) {
                                    Toast.makeText(LoginActivity.this, "Error try again", Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println(result);
                                    Iterator<JsonElement> ite = result.iterator();
                                    while (ite.hasNext()) {
                                        JsonObject item = ite.next().getAsJsonObject();
                                        if (item.getAsJsonPrimitive("email").getAsString().equals(email)) {
                                            connect.putExtra("KEY_MAIL", email);
                                            connect.putExtra("KEY_PSEUDO", item.getAsJsonPrimitive("pseudo").getAsString());
                                            finish();
                                            startActivity(connect);
                                        }
                                    }
                                    Toast.makeText(LoginActivity.this, "Error wrong password/mail", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
}
}

