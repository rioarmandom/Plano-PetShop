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

public class AEAnimal extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fabSave;
    TextInputLayout txtAniType, txtAniAge, txtAniName;
    MaterialBetterSpinner txtAniSex;
    DatabaseHelper dbHelper;
    String[] spinnerList = {"Male", "Female"};
    ArrayAdapter<String> arrayAdapter;
    String stAniSex, stAniID, phone;
    int ani_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_animal);
        dbHelper = new DatabaseHelper(this);
        phone = getIntent().getStringExtra("phone_no");
        stAniID = getIntent().getStringExtra("ani_id");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerList);
        txtAniSex = (MaterialBetterSpinner) findViewById(R.id.txtAniSex);
        txtAniSex.setAdapter(arrayAdapter);
        txtAniName = (TextInputLayout) findViewById(R.id.txtAniName);
        txtAniType = (TextInputLayout) findViewById(R.id.txtAniType);
        txtAniAge = (TextInputLayout) findViewById(R.id.txtAniAge);
        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);

        if (stAniID != null) {
            ani_id = Integer.parseInt(stAniID);
            getSupportActionBar().setTitle("EDIT ANIMAL");
            editAnimal(ani_id);
        } else {
            getSupportActionBar().setTitle("ADD ANIMAL");
            addAnimal();
        }


    }

    private void editAnimal(int ani_id) {
        final Animal animal = dbHelper.getOneAnimal(ani_id);
        txtAniName.getEditText().setText(animal.getAni_name());
        txtAniType.getEditText().setText(animal.getAni_type());
        txtAniAge.getEditText().setText(String.valueOf(animal.getAni_age()));
        txtAniSex.setText(animal.getAni_sex());
        stAniSex = animal.getAni_sex();
        txtAniSex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stAniSex = (String) parent.getItemAtPosition(position);
            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ani_name = txtAniName.getEditText().getText().toString();
                String ani_type = txtAniType.getEditText().getText().toString();
                int ani_age = Integer.parseInt(txtAniAge.getEditText().getText().toString());
                String ani_sex = stAniSex;
                int cust_id = dbHelper.getIdCust(phone);
                if (cust_id != 0) {
                    if (dbHelper.updateAnimal(new Animal(animal.getAni_id(), cust_id, ani_name, ani_type, ani_age, ani_sex))) {
                        Toast.makeText(getApplicationContext(), "Animal edited", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AEAnimal.this, AnimalMenu.class);
                        intent.putExtra("phone_no", phone);
                        startActivity(intent);
                        finish();
                    }
                } else Toast.makeText(getApplicationContext(), "Can't edit animal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addAnimal() {
        txtAniSex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stAniSex = (String) parent.getItemAtPosition(position);
            }
        });
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ani_name = txtAniName.getEditText().getText().toString();
                String ani_type = txtAniType.getEditText().getText().toString();
                int ani_age = Integer.parseInt(txtAniAge.getEditText().getText().toString());
                String ani_sex = stAniSex;
                int cust_id = dbHelper.getIdCust(phone);
                if (cust_id != 0) {
                    if (dbHelper.createAnimal(new Animal(cust_id, ani_name, ani_type, ani_age, ani_sex))) {
                        Toast.makeText(getApplicationContext(), "Animal added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AEAnimal.this, AnimalMenu.class);
                        intent.putExtra("phone_no", phone);
                        startActivity(intent);
                        finish();
                    }
                } else Toast.makeText(getApplicationContext(), "Can't add new animal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AEAnimal.this, AnimalMenu.class);
        intent.putExtra("phone_no", phone);
        startActivity(intent);
        finish();
    }
}

