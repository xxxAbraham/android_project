package com.example.projet;


import android.app.AlertDialog;
import android.content.DialogInterface;
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


import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 @author Abraham Gabsi
 */
public class InscriptionActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mPseudoView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mSubmit;
    private TextView mGotAcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        // Set up the login form.
        mPseudoView = (AutoCompleteTextView) findViewById(R.id.pseudo);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mSubmit = (Button) findViewById(R.id.inscription);
        mGotAcount = (TextView) findViewById(R.id.gotaccount);
        mGotAcount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent connect = new Intent(getApplicationContext(),LoginActivity.class);
                connect.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connect);
                finish();
            }
        });
        mSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                View focusView = null;
                boolean cancel = false;
                String password = mPasswordView.getText().toString();
                if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                    mPasswordView.setError(getString(R.string.error_invalid_password));
                    focusView = mPasswordView;
                    cancel = true;
                }

                String email = mEmailView.getText().toString();
                 if (TextUtils.isEmpty(email)) {
                    mEmailView.setError(getString(R.string.error_field_required));
                    focusView = mEmailView;
                    cancel = true;
                } else if (!isEmailValid(email)) {
                    mEmailView.setError(getString(R.string.error_invalid_email));
                    focusView = mEmailView;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                } else {
                   String url = "http://10.0.2.2:8080/api/membre/add";
                    final JsonObject json = new JsonObject();
                    json.addProperty("pseudo", mPseudoView.getText().toString());
                    json.addProperty("email", mEmailView.getText().toString());
                    json.addProperty("password", mPasswordView.getText().toString());
                    Ion.with(InscriptionActivity.this)
                            .load(url)
                            .setJsonObjectBody(json)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(InscriptionActivity.this).create();
                                    if (result.getAsJsonPrimitive("ok").getAsBoolean()==true) {
                                        System.out.println(json.toString());
                                        alertDialog.setTitle("Fin inscription");
                                        alertDialog.setMessage("Inscription Complète !");
                                        alertDialog.setCanceledOnTouchOutside(true);
                                        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                                            @Override
                                                                            public void onCancel(DialogInterface dialog) {
                                                                                Intent connect = new Intent(getApplicationContext(), MainActivity.class);
                                                                                connect.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                                startActivity(connect);
                                                                                finish();
                                                                            }
                                                                        }
                                        );
                                    }
                                    else {
                                        alertDialog.setTitle("Problème");
                                        alertDialog.setMessage("Compte existant !");
                                        alertDialog.setCanceledOnTouchOutside(true);
                                        }
                                    alertDialog.show();
                                }
                            });

                }
            }
        });
    }
    private boolean isEmailValid(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

}


