package com.example.rio.plano_petshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by almanalfaruq on 22/11/2016.
 */

public class AddAnimPage extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fabSave;
    TextInputLayout txtAniType, txtAniAge, txtAniSex;
    DatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_animal);
        dbHelper = new DatabaseHelper(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ADD ANIMAL");

        txtAniType = (TextInputLayout) findViewById(R.id.txtAniType);
        txtAniAge = (TextInputLayout) findViewById(R.id.txtAniAge);
        txtAniSex = (TextInputLayout) findViewById(R.id.txtAniSex);

        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ani_type = txtAniType.getEditText().getText().toString();
                int ani_age = Integer.parseInt(txtAniAge.getEditText().getText().toString());
                String ani_sex = txtAniSex.getEditText().getText().toString();
                String phone = getIntent().getStringExtra("phone_no");
                Log.d("Phone", phone);
                int cust_id = dbHelper.getIdCust(phone);
                if (cust_id != 0) {
                    if (dbHelper.createAnimal(new Animal(cust_id, ani_type, ani_age, ani_sex))) {
                        Toast.makeText(getApplicationContext(), "Animal added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddAnimPage.this, AnimalMenu.class);
                        intent.putExtra("phone_no", phone);
                        startActivity(intent);
                        finish();
                    }
                } else Toast.makeText(getApplicationContext(), "Can't add new animal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

