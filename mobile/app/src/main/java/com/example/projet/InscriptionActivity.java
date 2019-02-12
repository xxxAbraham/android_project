package com.example.projet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * A login screen that offers login via email/password.
 */
public class InscriptionActivity extends AppCompatActivity {
    // UI references.
    private AutoCompleteTextView mPseudoView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        // Set up the login form.
        mPseudoView = (AutoCompleteTextView) findViewById(R.id.pseudo);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mSubmit = (Button) findViewById(R.id.inscription);
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
                    String url = "http://localhost:3000/api/membre";
                    final JsonObject json = new JsonObject();
                    json.addProperty("pseudo", mPseudoView.getText().toString());
                    json.addProperty("password", mPasswordView.getText().toString());
                    json.addProperty("email", mEmailView.getText().toString());
                    Ion.with(InscriptionActivity.this)
                            .load(url)
                            .setHeader("Content-Type: ", "application/json")
                            .setJsonObjectBody(json)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    System.out.println(json.toString());
                                    AlertDialog alertDialog = new AlertDialog.Builder(InscriptionActivity.this).create();
                                    alertDialog.setTitle("Fin inscription");
                                    alertDialog.setMessage("Inscription Complète !");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    Intent connect = new Intent(getApplicationContext(),MainActivity.class);
                                                    startActivity(connect);
                                                }
                                            });
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


