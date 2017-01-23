package com.example.rio.plano_petshop.Anamnesa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rio.plano_petshop.Animal.AnimalDetail;
import com.example.rio.plano_petshop.DatabaseHelper;
import com.example.rio.plano_petshop.Model.Anamnesa;
import com.example.rio.plano_petshop.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by almanalfaruq on 17/12/2016.
 */

public class AnamnesaDetail extends AppCompatActivity {

    Toolbar toolbar;
    TextInputLayout txtAnamnesa, txtTeraphy, txtDate;
    ImageButton btnDate;
    Button btnCancel, btnOK;
    DatabaseHelper dbHelper;
    Anamnesa anamnesa;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date;
    DatePickerDialog datePicker;
    String phone;
    int ani_id;
    int anam_id;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.anamnesa_detail);
        dbHelper = new DatabaseHelper(this);
        phone = getIntent().getStringExtra("phone_no");
        ani_id = Integer.parseInt(getIntent().getStringExtra("ani_id"));
        anam_id = Integer.parseInt(getIntent().getStringExtra("anam_id"));
        Log.d("Phone: ", phone);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        date = dateFormat.format(calendar.getTime());

        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                txtDate.getEditText().setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DETAIL");

        txtAnamnesa = (TextInputLayout) findViewById(R.id.txtAnamnesa);
        txtTeraphy = (TextInputLayout) findViewById(R.id.txtTeraphy);
        txtDate = (TextInputLayout) findViewById(R.id.txtDate);
        txtDate.getEditText().setInputType(InputType.TYPE_NULL);
        txtDate.getEditText().setText(date);

        btnDate = (ImageButton) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnamnesaDetail.this, AnimalDetail.class);
                String stAniId = String.valueOf(ani_id);
                intent.putExtra("ani_id", stAniId);
                intent.putExtra("phone_no", phone);
                startActivity(intent);
                finish();
            }
        });
        btnOK = (Button) findViewById(R.id.btnOK);

        if (anam_id != 0) {
            anamnesa = dbHelper.getAnamDetail(ani_id, anam_id);
            if (anamnesa != null) {
                txtAnamnesa.getEditText().setText(anamnesa.getAnamnesa());
                txtTeraphy.getEditText().setText(anamnesa.getTeraphy());
                txtDate.getEditText().setText(anamnesa.getDate());
            }
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Anamnesa anamnesa = new Anamnesa(anam_id, ani_id, txtDate.getEditText().getText().toString()
                            , txtAnamnesa.getEditText().getText().toString()
                            , txtTeraphy.getEditText().getText().toString());
                    if (dbHelper.updateAnamnesa(anamnesa)) {
                        Intent intent = new Intent(AnamnesaDetail.this, AnamMenu.class);
                        String stAniId = String.valueOf(ani_id);
                        String stAnamId = String.valueOf(anam_id);
                        intent.putExtra("ani_id", stAniId);
                        intent.putExtra("anam_id", stAnamId);
                        intent.putExtra("phone_no", phone);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AnamnesaDetail.this, "Can't update data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            txtDate.getEditText().setText(date);
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Anamnesa anamnesa = new Anamnesa(ani_id, txtDate.getEditText().getText().toString()
                            , txtAnamnesa.getEditText().getText().toString()
                            , txtTeraphy.getEditText().getText().toString());
                    if (dbHelper.createAnamnesa(anamnesa)) {
                        Intent intent = new Intent(AnamnesaDetail.this, AnamMenu.class);
                        String stAniId = String.valueOf(ani_id);
                        String stAnamId = String.valueOf(anam_id);
                        intent.putExtra("ani_id", stAniId);
                        intent.putExtra("anam_id", stAnamId);
                        intent.putExtra("phone_no", phone);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AnamnesaDetail.this, "Can't add data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AnamnesaDetail.this, AnamMenu.class);
        String stAniId = String.valueOf(ani_id);
        String stAnamId = String.valueOf(anam_id);
        intent.putExtra("ani_id", stAniId);
        intent.putExtra("anam_id", stAnamId);
        intent.putExtra("phone_no", phone);
        startActivity(intent);
        finish();
    }
}
