package com.example.projet;


import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.HashMap;
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
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private HashMap<String,String> admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.admin = new HashMap<String,String>();
        admin.put("pseudo","admin");
        admin.put("mail","admin");
        admin.put("password","admin");
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
               /* System.out.println(admin.get("mail") +"||"+ admin.get("password"));
                if (mEmailView.getText().toString().equals(admin.get("mail")) && mPasswordView.getText().toString().equals(admin.get("password"))) {
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("pseudo", admin.get("pseudo"));
                    editor.putString("mail", admin.get("mail"));
                    editor.apply();
                    startActivity(connect);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Error wrong password/mail", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
                System.out.println("IDENTIFICATION");
                String url = "http://10.0.2.2:8080/api/membre/get/email/"+mEmailView.getText().toString();
                Ion.with(getApplicationContext())
                        .load(url)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (result == null) {
                                    Toast.makeText(LoginActivity.this, "Error try again", Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println(result.getAsJsonPrimitive("email"));
                                    System.out.println(mEmailView.getText().toString());
                                    if (result.getAsJsonPrimitive("ok").getAsBoolean()==false){
                                        Toast.makeText(LoginActivity.this, "Utilisateur n'existe pas", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(result.getAsJsonPrimitive("password").getAsString().equals(mPasswordView.getText().toString()) && result.getAsJsonPrimitive("email").getAsString().equals(mEmailView.getText().toString())){
                                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                        editor.putString("pseudo", result.getAsJsonPrimitive("pseudo").getAsString());
                                        editor.putString("mail",mEmailView.getText().toString());
                                        editor.apply();
                                        startActivity(connect);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, "Error wrong password/mail", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
}
}

