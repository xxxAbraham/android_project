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
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailView.getText().toString();
                View focusView = null;
                boolean cancel = false;
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
                    //TODO Identification de l'utilisateur
                    Intent connect = new Intent(getApplicationContext(), MenuActivity.class);
                    connect.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(connect);
                    finish();
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
}

