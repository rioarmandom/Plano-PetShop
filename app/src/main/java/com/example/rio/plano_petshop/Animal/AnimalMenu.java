package com.example.rio.plano_petshop.Animal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.rio.plano_petshop.Customer.AECustomer;
import com.example.rio.plano_petshop.DatabaseHelper;
import com.example.rio.plano_petshop.MainMenu;
import com.example.rio.plano_petshop.R;
import com.example.rio.plano_petshop.ViewPagerAdapter;

/**
 * Created by almanalfaruq on 22/11/2016.
 */

public class AnimalMenu extends AppCompatActivity {

    private static ViewPagerAdapter adapter;

    Toolbar toolbar;
    ViewPager viewPager;
    FloatingActionButton fabAdd;
    DatabaseHelper databaseHelper;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        databaseHelper = new DatabaseHelper(this);
        phone = getIntent().getStringExtra("phone_no");
        Bundle bundle = new Bundle();
        bundle.putString("phone_no", phone);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager, bundle);//Set up View Pager

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalMenu.this, AEAnimal.class);
                intent.putExtra("phone_no", phone);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AnimalMenu.this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_cust, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.edit_cust) {
            Intent intent = new Intent(AnimalMenu.this, AECustomer.class);
            intent.putExtra("phone_no", phone);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager, Bundle pageBundle) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), pageBundle);
        adapter.addFrag(new AnimalFragment(), "RecyclerView", pageBundle);
        viewPager.setAdapter(adapter);
    }


    //Return current fragment on basis of Position
    public Fragment getFragment(int pos) {
        return adapter.getItem(pos);
    }


}