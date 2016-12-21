package com.example.rio.plano_petshop;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by almantera on 10/11/16.
 */

public class SignUpPage extends AppCompatActivity {

    TextInputLayout txtName, txtUsername, txtAge, txtAddress, txtBirthday
            , txtEmail, txtPhone, txtPassword, txtRePassword;
    Button btnCreate;
    Toolbar toolbar;

    DatabaseHelper databaseHelper;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sign_up);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.button_signup);
        txtName = (TextInputLayout) findViewById(R.id.txtName);
        txtName.requestFocus();
        txtUsername = (TextInputLayout) findViewById(R.id.txtUsername);
        txtAge = (TextInputLayout) findViewById(R.id.txtAge);
        txtAddress = (TextInputLayout) findViewById(R.id.txtAddress);
        txtBirthday = (TextInputLayout) findViewById(R.id.txtBirthday);
        txtEmail = (TextInputLayout) findViewById(R.id.txtEmail);
        txtPhone = (TextInputLayout) findViewById(R.id.txtPhone);
        txtPassword = (TextInputLayout) findViewById(R.id.txtPassword);
        txtRePassword = (TextInputLayout) findViewById(R.id.txtRePassword);
        txtRePassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = txtPassword.getEditText().getText().toString();
                if (!s.equals(password)) {
                    txtRePassword.setError("Not match with password");
                } else {
                    txtRePassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getEditText().getText().toString();
                String username = txtUsername.getEditText().getText().toString();
                String age = txtAge.getEditText().getText().toString();
                String address = txtAddress.getEditText().getText().toString();
                String birthday = txtBirthday.getEditText().getText().toString();
                String email = txtEmail.getEditText().getText().toString();
                String phone = txtPhone.getEditText().getText().toString();
                String password = txtPassword.getEditText().getText().toString();
                String repassword = txtRePassword.getEditText().getText().toString();
                if (password.equals(repassword)) {
                    User user = new User(name, username, Integer.parseInt(age), address,birthday, email, phone);
                    boolean createStat = databaseHelper.createUser(user, password);
                    if (createStat) {
                        Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Can't create user", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Password and Re-Password doesn't match", Toast.LENGTH_LONG).show();
                    txtPassword.getEditText().setText("", TextView.BufferType.EDITABLE);
                    txtRePassword.getEditText().setText("", TextView.BufferType.EDITABLE);
                }
            }
        });
    }
}
