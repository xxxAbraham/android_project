package com.example.projet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class Modif_compte extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mPseudoView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private ImageButton retour;
    private String password;
    private String pseudo;
    private String mail;
    TextView supp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_compte);
        supp= findViewById(R.id.supp);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPseudoView = (AutoCompleteTextView) findViewById(R.id.pseudo);
        final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        pseudo = prefs.getString("pseudo", null);
        mail = prefs.getString("mail", null);
        final String id = prefs.getString("id", null);
        password = prefs.getString("password", null);
        mPseudoView.setText(pseudo);
        mEmailView.setText(mail);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setText(password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        retour  = findViewById(R.id.retour);
        retour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigation = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(navigation);
                finish();
            }
        });
        supp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String sup = "http://10.0.2.2:8080/api/membre/delete/"+prefs.getString("id","");
                Ion.with(Modif_compte.this)
                        .load("DELETE",sup)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                setResult(123);
                                finish();

                            }});
            }
        });
        Button confirmer = (Button) findViewById(R.id.email_sign_in_button);
        confirmer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                View focusView = null;
                boolean cancel = false;
                System.out.println(mPasswordView.getText().toString());
                if (!TextUtils.isEmpty(mPasswordView.getText().toString()) && !isPasswordValid(mPasswordView.getText().toString())) {
                    mPasswordView.setError(getString(R.string.error_invalid_password));
                    focusView = mPasswordView;
                    cancel = true;
                }
                if (TextUtils.isEmpty(mEmailView.getText().toString())) {
                    mEmailView.setError(getString(R.string.error_field_required));
                    focusView = mEmailView;
                    cancel = true;
                } else if (!isEmailValid(mEmailView.getText().toString())) {
                    mEmailView.setError(getString(R.string.error_invalid_email));
                    focusView = mEmailView;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                } else {
                    String url = "http://10.0.2.2:8080/api/membre/update/" + id;
                    final JsonObject json = new JsonObject();
                    json.addProperty("pseudo", mPseudoView.getText().toString());
                    json.addProperty("email", mEmailView.getText().toString());
                    json.addProperty("password", mPasswordView.getText().toString());
                    Ion.with(Modif_compte.this)
                            .load("PUT", url)
                            .setJsonObjectBody(json)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(Modif_compte.this).create();
                                    if (result == null ) {
                                        Toast.makeText(Modif_compte.this, "Error try again", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (result.getAsJsonPrimitive("status") != null && result.getAsJsonPrimitive("status").getAsInt()==500){
                                        Toast.makeText(Modif_compte.this, "Error compte existe déjà", Toast.LENGTH_SHORT).show();

                                    } else if (result.getAsJsonPrimitive("ok") != null && result.getAsJsonPrimitive("ok").getAsBoolean() == true) {
                                        System.out.println(json.toString());
                                        alertDialog.setTitle("Fin modification");
                                        alertDialog.setMessage("modification Complète !");
                                        alertDialog.setCanceledOnTouchOutside(true);
                                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                        editor.putString("pseudo", mPseudoView.getText().toString());
                                        editor.putString("mail", mEmailView.getText().toString());
                                        editor.putString("password",mPasswordView.getText().toString());
                                        editor.commit();
                                        alertDialog.show();
                                        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                                            @Override
                                                                            public void onCancel(DialogInterface dialog) {
                                                                                Intent connect = new Intent(getApplicationContext(), MenuActivity.class);
                                                                                connect.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                                startActivity(connect);
                                                                            }
                                                                        }
                                        );
                                    } else {
                                        alertDialog.setTitle("Problème");
                                        alertDialog.setMessage("Compte existant !");
                                        alertDialog.setCanceledOnTouchOutside(true);
                                        alertDialog.show();
                                    }
                                }
                            });
                }
            }
        });


    }
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
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
}



