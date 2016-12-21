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
public class AECustomer extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fabSave;
    TextInputLayout txtOwner, txtAddress, txtPhone;
    DatabaseHelper dbHelper;
    String phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);
        dbHelper = new DatabaseHelper(this);
        phone = getIntent().getStringExtra("phone_no");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("ADD ID");

        txtOwner = (TextInputLayout) findViewById(R.id.txtOwner);
        txtAddress = (TextInputLayout) findViewById(R.id.txtAddress);
        txtPhone = (TextInputLayout) findViewById(R.id.txtPhone);
        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);

        if (phone != null) {
            getSupportActionBar().setTitle("EDIT ID");
            editCust();
        } else {
            getSupportActionBar().setTitle("ADD ID");
            addCust();
        }
    }

    private void editCust() {
        final Customer customer = dbHelper.getCustomer(phone);
        txtOwner.getEditText().setText(customer.getName());
        txtAddress.getEditText().setText(customer.getAddress());
        txtPhone.getEditText().setText(customer.getPhone_no());
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String owner = txtOwner.getEditText().getText().toString();
                String address = txtAddress.getEditText().getText().toString();
                String phone = txtPhone.getEditText().getText().toString();
                if (dbHelper.updateCust(new Customer(customer.getCust_id(),owner, address, phone))) {
                    Toast.makeText(getApplicationContext(), "Customer edited", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AECustomer.this, MainMenu.class);
                    startActivity(intent);
                    finish();
                } else Toast.makeText(getApplicationContext(), "Can't edit customer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCust() {
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String owner = txtOwner.getEditText().getText().toString();
                String address = txtAddress.getEditText().getText().toString();
                String phone = txtPhone.getEditText().getText().toString();
                if (dbHelper.createCustomer(new Customer(owner, address, phone))) {
                    Toast.makeText(getApplicationContext(), "Customer added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AECustomer.this, AEAnimal.class);
                    intent.putExtra("phone_no", phone);
                    startActivity(intent);
                    finish();
                } else Toast.makeText(getApplicationContext(), "Can't add new customer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AECustomer.this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}
