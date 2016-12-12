package com.example.rio.plano_petshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almanalfaruq on 13/12/2016.
 */

// TODO : Adding anamnesa with builder (popup dialog)
public class AnimalDetail extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fabAdd;
    TextView txtName, txtAddress, txtPhone, txtAniName, txtAniType, txtAniAge, txtAniSex;
    RecyclerView rvAnamnesa;
    private static List<Anamnesa> anamnesaList = new ArrayList<>();
    ActionMode mActionMode;
    AnamAdapter anamAdapter;
    DatabaseHelper dbHelper;
    int ani_id;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.data_detail);
        dbHelper = new DatabaseHelper(this);
        ani_id = Integer.parseInt(getIntent().getStringExtra("ani_id"));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DETAIL ANIMAL");

        String phone = getIntent().getStringExtra("phone_no");
        String name = dbHelper.getNameCust(phone);
        String address = dbHelper.getAddressCust(phone);
        txtName = (TextView) findViewById(R.id.txtName);
        txtName.setText(name);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtAddress.setText(address);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtPhone.setText(phone);
        txtAniName = (TextView) findViewById(R.id.txtAniName);
        txtAniName.setText(getIntent().getStringExtra("ani_name"));
        txtAniType = (TextView) findViewById(R.id.txtAniType);
        txtAniType.setText(getIntent().getStringExtra("ani_type"));
        txtAniAge = (TextView) findViewById(R.id.txtAniAge);
        txtAniAge.setText(getIntent().getStringExtra("ani_age"));
        txtAniSex = (TextView) findViewById(R.id.txtAniSex);
        txtAniSex.setText(getIntent().getStringExtra("ani_sex"));

        populateRecView();
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

    private void implementRecyclerViewClickListeners() {
        rvAnamnesa.addOnItemTouchListener(new RecyclerTouchListener(this, rvAnamnesa, new RecyclerItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //If ActionMode not null select item
                if (mActionMode != null)
                    onListItemSelect(position);
                Anamnesa anamnesa = anamnesaList.get(position);
//                String ani_id = String.valueOf(animal.getAni_id());
//                Intent intent = new Intent(this, AnimalDetail.class);
//                intent.putExtra("phone_no", phone);
//                intent.putExtra("ani_id", ani_id);
//                startActivity(intent);
//                Toast.makeText(this, "Anda memilih " + nama, Toast.LENGTH_SHORT).show();
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
            mActionMode = ((AppCompatActivity) this).startSupportActionMode(new ToolbarAnamnesa(this, anamAdapter, anamnesaList));
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
                anamnesaList.remove(selected.keyAt(i));

                anamAdapter.notifyDataSetChanged();//notify anamAdapter

            }
        }
        Toast.makeText(this, selected.size() + " item deleted.", Toast.LENGTH_SHORT).show();//Show Toast
        mActionMode.finish();//Finish action mode after use

    }
    
}
