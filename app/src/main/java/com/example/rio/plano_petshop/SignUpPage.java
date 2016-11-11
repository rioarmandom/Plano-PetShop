package com.example.rio.plano_petshop;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
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

public class SignUpPage extends Activity {

    TextInputLayout txtName, txtUsername, txtAge, txtAddress, txtBirthday
            , txtEmail, txtPhone, txtPassword, txtRePassword;
    Button btnCreate;

//    DatePickerDialog dpdBirthday;
//    SimpleDateFormat dateFormatter;

    DatabaseHelper databaseHelper;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sign_up);
        databaseHelper = new DatabaseHelper(getApplicationContext());
//        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//        setDateTimeField();

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

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = txtPassword.getEditText().getText().toString();
                String repassword = txtRePassword.getEditText().getText().toString();
                if (!password.equals(repassword)) {
                    txtRePassword.setError("Not match with password");
                }
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
                System.out.println(password);
                System.out.println(repassword);
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

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void setDateTimeField() {
//        txtBirthday.getEditText().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dpdBirthday.show();
//            }
//        });
//        Calendar newCalendar = Calendar.getInstance();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            dpdBirthday = new DatePickerDialog(this, new OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                    Calendar newDate = Calendar.getInstance();
//                    newDate.set(year, month, dayOfMonth);
//                    txtBirthday.getEditText().setText(dateFormatter.format(newDate.getTime()), TextView.BufferType.EDITABLE);
//                }
//            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        }
//    }

}
