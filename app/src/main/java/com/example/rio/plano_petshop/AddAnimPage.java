package com.example.rio.plano_petshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

/**
 * Created by almanalfaruq on 22/11/2016.
 */

public class AddAnimPage extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fabSave;
    TextInputLayout txtAniType, txtAniAge;
    DatabaseHelper dbHelper;
    String[] spinnerList = {"Male", "Female"};
    String stAniSex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_animal);
        dbHelper = new DatabaseHelper(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ADD ANIMAL");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, spinnerList);
        MaterialBetterSpinner txtAniSex = (MaterialBetterSpinner)
                findViewById(R.id.txtAniSex);

        txtAniType = (TextInputLayout) findViewById(R.id.txtAniType);
        txtAniAge = (TextInputLayout) findViewById(R.id.txtAniAge);
        txtAniSex.setAdapter(arrayAdapter);
        txtAniSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stAniSex = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        txtAniSex = (TextInputLayout) findViewById(R.id.txtAniSex);

        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ani_type = txtAniType.getEditText().getText().toString();
                int ani_age = Integer.parseInt(txtAniAge.getEditText().getText().toString());
                String ani_sex = stAniSex;
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

