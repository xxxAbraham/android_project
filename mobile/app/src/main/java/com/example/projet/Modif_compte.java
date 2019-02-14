package com.example.projet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_compte);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPseudoView = (AutoCompleteTextView) findViewById(R.id.pseudo);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String pseudo = prefs.getString("pseudo", null);
        String mail = prefs.getString("mail", null);
        /*Bundle bundle = getIntent().getExtras();
        final String mail = bundle.getString("KEY_MAIL");
        final String pseudo = bundle.getString("KEY_PSEUDO");*/
        mPseudoView.setText(pseudo);
        mEmailView.setText(mail);
        mPasswordView = (EditText) findViewById(R.id.password);
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

    }

}

