package com.example.rio.plano_petshop;

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
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almanalfaruq on 13/12/2016.
 */
public class AnimalDetail extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fabAdd;
    TextInputLayout txtName, txtAddress, txtPhone, txtAniName, txtAniType, txtAniAge, txtAniSex;
    RecyclerView rvAnamnesa;
    private static List<Anamnesa> anamnesaList = new ArrayList<>();
    ActionMode mActionMode;
    AnamAdapter anamAdapter;
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

        populateRecView();
        implementRecyclerViewClickListeners();
        anam_id = 0;
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalDetail.this, AnamnesaDetail.class);
                String stAniId = String.valueOf(ani_id);
                String stAnamId = String.valueOf(anam_id);
                intent.putExtra("phone_no", phone);
                intent.putExtra("ani_id", stAniId);
                intent.putExtra("anam_id", stAnamId);
                startActivity(intent);
            }
        });
    }

    public void populateRecView() {
        rvAnamnesa = (RecyclerView) findViewById(R.id.rvAnamnesa);
        rvAnamnesa.setHasFixedSize(true);
        rvAnamnesa.setLayoutManager(new LinearLayoutManager(this));
        anamnesaList = dbHelper.getAnamnesa(ani_id);
        anamAdapter = new AnamAdapter(this, anamnesaList);
        rvAnamnesa.setAdapter(anamAdapter);
        anamAdapter.notifyDataSetChanged();
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

    private void implementRecyclerViewClickListeners() {
        rvAnamnesa.addOnItemTouchListener(new RecyclerTouchListener(this, rvAnamnesa, new RecyclerItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //If ActionMode not null select item
                if (mActionMode != null)
                    onListItemSelect(position);
                else {
                    Anamnesa anamnesa = anamnesaList.get(position);
                    anam_id = anamnesa.getAnam_id();
                    String stAniID = String.valueOf(ani_id);
                    String stAnamID = String.valueOf(anam_id);
                    Intent intent = new Intent(AnimalDetail.this, AnamnesaDetail.class);
                    intent.putExtra("phone_no", phone);
                    intent.putExtra("ani_id", stAniID);
                    intent.putExtra("anam_id", stAnamID);
                    startActivity(intent);
//                    Toast.makeText(this, "Anda memilih " + nama, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                //Select item on long click
                onListItemSelect(position);
            }
        }));
    }

    //List item select method
    private void onListItemSelect(int position) {
        anamAdapter.toggleSelection(position);//Toggle the selection

        boolean hasCheckedItems = anamAdapter.getSelectedCount() > 0;//Check if any items are already selected or not


        if (hasCheckedItems && mActionMode == null) {
            // there are some selected items, start the actionMode
            mActionMode = ((AppCompatActivity) this).startSupportActionMode(new ToolbarAnamnesa(this, anamAdapter, anamnesaList, AnimalDetail.this));
        } else if (!hasCheckedItems && mActionMode != null)
            // there no selected items, finish the actionMode
            mActionMode.finish();

        if (mActionMode != null)
            //set action mode title on item selection
            mActionMode.setTitle(String.valueOf(anamAdapter
                    .getSelectedCount()) + " selected");
        anamAdapter.getItemId(position);

    }

    //Set action mode null after use
    public void setNullToActionMode() {
        if (mActionMode != null)
            mActionMode = null;
    }

    //Delete selected rows
    public void deleteRows() {
        SparseBooleanArray selected = anamAdapter.getSelectedIds();//Get selected ids


        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                Anamnesa anamnesa = anamnesaList.get(selected.keyAt(i));
                int anam_id = anamnesa.getAnam_id();
                dbHelper.deletAnam(anam_id);
                anamnesaList.remove(selected.keyAt(i));

                anamAdapter.notifyDataSetChanged();//notify anamAdapter

            }
        }
        Toast.makeText(this, selected.size() + " item deleted.", Toast.LENGTH_SHORT).show();//Show Toast
        mActionMode.finish();//Finish action mode after use

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
