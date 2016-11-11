package com.example.rio.plano_petshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by almantera on 10/11/16.
 */

public class LoginPage extends Activity {

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

        // Declaring TextInputLayout with the id from xml file
        txtUsername = (TextInputLayout) findViewById(R.id.txtUsername);
        txtPassword = (TextInputLayout) findViewById(R.id.txtPassword);

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
                String username = txtUsername.getEditText().getText().toString();
                String password = txtPassword.getEditText().getText().toString();
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
}
