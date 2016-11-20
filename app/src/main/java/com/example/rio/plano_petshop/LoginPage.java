package com.example.rio.plano_petshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by almantera on 10/11/16.
 */

public class LoginPage extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    // Declaring input for process
    TextInputLayout txtPassword, txtUsername;
    Button btnSignUp, btnSignIn;

    @Override
    public void onCreate(Bundle bundle) {
        // Show login screen
        super.onCreate(bundle);
        setContentView(R.layout.login_page);
        // Add database
        databaseHelper = new DatabaseHelper(this);
        forLollipop();

        // Declaring Button with the id from xml file
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // What button will do if it pressed
                Intent intent = new Intent(getApplicationContext(), SignUpPage.class); // Bring up Sign up Page
                startActivity(intent);
                finish();
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // What button will do if it pressed
                // Input from EditText and make it to string
                String username, password;
                username = txtUsername.getEditText().getText().toString();
                password = txtPassword.getEditText().getText().toString();
                // Save result value from running the goLogin method from DatabaseHelper class
                int result = databaseHelper.goLogin(username, password);
                // You know what is this (lol)
                if (result == -1) {
                    Toast.makeText(getApplicationContext(), "Username not found", Toast.LENGTH_LONG).show();
                } else if (result == 0) {
                    Toast.makeText(getApplicationContext(),  "Your password doesn't match", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void forLollipop() {
        // Declaring TextInputLayout with the id from xml file
        txtUsername = (TextInputLayout) findViewById(R.id.txtUsername);
        txtPassword = (TextInputLayout) findViewById(R.id.txtPassword);
    }

//    public void underLollipop() {
//        // Declaring TextInputLayout with the id from xml file
//        etUsername = (EditText) findViewById(R.id.etUsername);
//        etPassword = (EditText) findViewById(R.id.etPassword);
//    }
}
