package com.example.rio.plano_petshop.Anamnesa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rio.plano_petshop.Animal.AnimalDetail;
import com.example.rio.plano_petshop.Customer.AECustomer;
import com.example.rio.plano_petshop.Customer.CustomerFragment;
import com.example.rio.plano_petshop.DatabaseHelper;
import com.example.rio.plano_petshop.ExportDatabaseCSV;
import com.example.rio.plano_petshop.R;
import com.example.rio.plano_petshop.ViewPagerAdapter;

/**
 * Created by almanalfaruq on 22/01/2017.
 */

public class AnamMenu extends AppCompatActivity {

    private static ViewPagerAdapter adapter;

    Toolbar toolbar;
    ViewPager viewPager;
    FloatingActionButton fabAdd;
    DatabaseHelper databaseHelper;
    ExportDatabaseCSV exportDatabase;
    Bundle bundle;
    String phone;
    int ani_id, anam_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        databaseHelper = new DatabaseHelper(this);
        bundle = new Bundle();
        phone = getIntent().getStringExtra("phone_no");
        Log.v("Phone no", phone);
        ani_id = Integer.parseInt(getIntent().getStringExtra("ani_id"));
        anam_id = Integer.parseInt(getIntent().getStringExtra("anam_id"));
        bundle.putString("phone_no", phone);
        bundle.putInt("ani_id", ani_id);
        bundle.putInt("anam_id", anam_id);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Anamnesa");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager, bundle);//Set up View Pager

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnamMenu.this, AnamnesaDetail.class);
                String stAniId = String.valueOf(ani_id);
                String stAnamId = String.valueOf(anam_id);
                intent.putExtra("phone_no", phone);
                intent.putExtra("ani_id", stAniId);
                intent.putExtra("anam_id", stAnamId);
                startActivity(intent);
                finish();
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.setting, menu);
//        SearchView searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
//        searchView.setOnQueryTextListener(this);
//        return super.onCreateOptionsMenu(menu);
//    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager, Bundle vpBundle) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), vpBundle);
        adapter.addFrag(new AnamnesaFragment(), "RecyclerView", vpBundle);
        viewPager.setAdapter(adapter);
    }


    //Return current fragment on basis of Position
    public Fragment getFragment(int pos) {
        return adapter.getItem(pos);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AnamMenu.this, AnimalDetail.class);
        intent.putExtra("phone_no", phone);
        String stAniId = String.valueOf(ani_id);
        intent.putExtra("ani_id", stAniId);
        startActivity(intent);
        finish();
    }
}
