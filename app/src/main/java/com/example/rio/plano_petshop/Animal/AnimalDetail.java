package com.example.rio.plano_petshop.Animal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rio.plano_petshop.Anamnesa.AnamAdapter;
import com.example.rio.plano_petshop.Anamnesa.AnamMenu;
import com.example.rio.plano_petshop.Model.Anamnesa;
import com.example.rio.plano_petshop.Anamnesa.AnamnesaDetail;
import com.example.rio.plano_petshop.DatabaseHelper;
import com.example.rio.plano_petshop.Model.Animal;
import com.example.rio.plano_petshop.R;
import com.example.rio.plano_petshop.RecyclerItemClickListener;
import com.example.rio.plano_petshop.RecyclerTouchListener;
import com.example.rio.plano_petshop.Anamnesa.ToolbarAnamnesa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almanalfaruq on 13/12/2016.
 */
public class AnimalDetail extends AppCompatActivity {

    Toolbar toolbar;
    Button btnAnamnesa;
    TextInputLayout txtName, txtAddress, txtPhone, txtAniName, txtAniType, txtAniAge, txtAniSex;
    Animal animal;
    DatabaseHelper dbHelper;
    String phone;
    int ani_id, anam_id;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.data_detail);
        dbHelper = new DatabaseHelper(this);
        ani_id = Integer.parseInt(getIntent().getStringExtra("ani_id"));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DETAIL ANIMAL");

        phone = getIntent().getStringExtra("phone_no");
        String nameCust = dbHelper.getNameCust(phone);
        String address = dbHelper.getAddressCust(phone);
        animal = dbHelper.getOneAnimal(ani_id);
        txtName = (TextInputLayout) findViewById(R.id.txtOwner);
        txtName.getEditText().setInputType(InputType.TYPE_NULL);
        txtName.getEditText().setText(nameCust);
        txtAddress = (TextInputLayout) findViewById(R.id.txtAddress);
        txtAddress.getEditText().setInputType(InputType.TYPE_NULL);
        txtAddress.getEditText().setText(address);
        txtPhone = (TextInputLayout) findViewById(R.id.txtPhone);
        txtPhone.getEditText().setInputType(InputType.TYPE_NULL);
        txtPhone.getEditText().setText(phone);
        txtAniName = (TextInputLayout) findViewById(R.id.txtAniName);
        txtAniName.getEditText().setInputType(InputType.TYPE_NULL);
        txtAniName.getEditText().setText(animal.getAni_name());
        txtAniType = (TextInputLayout) findViewById(R.id.txtAniType);
        txtAniType.getEditText().setInputType(InputType.TYPE_NULL);
        txtAniType.getEditText().setText(animal.getAni_type());
        txtAniAge = (TextInputLayout) findViewById(R.id.txtAniAge);
        txtAniAge.getEditText().setInputType(InputType.TYPE_NULL);
        txtAniAge.getEditText().setText(String.valueOf(animal.getAni_age()));
        txtAniSex = (TextInputLayout) findViewById(R.id.txtAniSex);
        txtAniSex.getEditText().setInputType(InputType.TYPE_NULL);
        txtAniSex.getEditText().setText(animal.getAni_sex());
        anam_id = 0;
        btnAnamnesa = (Button) findViewById(R.id.btnAnamnesa);
        btnAnamnesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalDetail.this, AnamMenu.class);
                String stAniId = String.valueOf(ani_id);
                String stAnamId = String.valueOf(anam_id);
                intent.putExtra("phone_no", phone);
                intent.putExtra("ani_id", stAniId);
                intent.putExtra("anam_id", stAnamId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_animal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.edit_animal) {
            Intent intent = new Intent(AnimalDetail.this, AEAnimal.class);
            intent.putExtra("phone_no", phone);
            String stAniID = String.valueOf(ani_id);
            intent.putExtra("ani_id", stAniID);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AnimalDetail.this, AnimalMenu.class);
        intent.putExtra("phone_no", phone);
        startActivity(intent);
        finish();
    }
}
