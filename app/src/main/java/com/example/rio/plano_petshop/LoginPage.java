package com.example.rio.plano_petshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by almantera on 10/11/16.
 */

public class LoginPage extends Activity {

    DatabaseHelper databaseHelper;

    TextInputLayout txtPassword, txtUsername;
    Button btnSignUp, btnSignIn;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.login_page);
        databaseHelper = new DatabaseHelper(this);

        txtUsername = (TextInputLayout) findViewById(R.id.txtUsername);
        txtPassword = (TextInputLayout) findViewById(R.id.txtPassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getEditText().toString();
                String password = txtPassword.getEditText().toString();
                int result = databaseHelper.isLogin(username, password);
                if (result == -1) {
                    Toast.makeText(getApplicationContext(), "Username not found", Toast.LENGTH_LONG).show();
                } else if (result == 0) {
                    Toast.makeText(getApplicationContext(),  "Your password doesn't match", Toast.LENGTH_LONG).show();
                } else {

                }
            }
        });

    }
}
