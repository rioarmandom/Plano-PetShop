package com.example.rio.plano_petshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by almanalfaruq on 19/11/2016.
 */

public class AddCustPage extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fabSave;
    TextInputLayout txtOwner, txtAddress, txtPhone, txtAniType, txtAniAge, txtAniSex;
    DatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_id);
        dbHelper = new DatabaseHelper(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ADD ID");

        txtOwner = (TextInputLayout) findViewById(R.id.txtOwner);
        txtAddress = (TextInputLayout) findViewById(R.id.txtAddress);
        txtPhone = (TextInputLayout) findViewById(R.id.txtPhone);
        txtAniType = (TextInputLayout) findViewById(R.id.txtAniType);
        txtAniAge = (TextInputLayout) findViewById(R.id.txtAniAge);
        txtAniSex = (TextInputLayout) findViewById(R.id.txtAniSex);

        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String owner = txtOwner.getEditText().getText().toString();
                String address = txtAddress.getEditText().getText().toString();
                String phone = txtPhone.getEditText().getText().toString();
                String aniType = txtAniType.getEditText().getText().toString();
                Integer aniAge = Integer.parseInt(txtAniAge.getEditText().getText().toString());
                String aniSex = txtAniSex.getEditText().getText().toString();
                if (dbHelper.createCustomer(new Customer(owner, address, phone, aniType, aniAge, aniSex))) {
                    Toast.makeText(getApplicationContext(), "Customer added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCustPage.this, MainMenu.class);
                    startActivity(intent);
                    finish();
                } else Toast.makeText(getApplicationContext(), "Can't add new customer", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
