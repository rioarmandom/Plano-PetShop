package com.example.rio.plano_petshop;

import android.content.Intent;
import android.os.Bundle;
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
    TextInputLayout txtOwner, txtAddress, txtPhone;
    DatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);
        dbHelper = new DatabaseHelper(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ADD ID");

        txtOwner = (TextInputLayout) findViewById(R.id.txtOwner);
        txtAddress = (TextInputLayout) findViewById(R.id.txtAddress);
        txtPhone = (TextInputLayout) findViewById(R.id.txtPhone);

        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String owner = txtOwner.getEditText().getText().toString();
                String address = txtAddress.getEditText().getText().toString();
                String phone = txtPhone.getEditText().getText().toString();
                if (dbHelper.createCustomer(new Customer(owner, address, phone))) {
                    Toast.makeText(getApplicationContext(), "Customer added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCustPage.this, AddAnimPage.class);
                    intent.putExtra("phone_no", phone);
                    startActivity(intent);
                    finish();
                } else Toast.makeText(getApplicationContext(), "Can't add new customer", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
