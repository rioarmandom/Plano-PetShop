package com.example.rio.plano_petshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

/**
 * Created by almanalfaruq on 22/11/2016.
 */

public class AnimalMenu extends AppCompatActivity {

    private static ViewPagerAdapter adapter;

    Toolbar toolbar;
    ViewPager viewPager;
    FloatingActionButton fabAdd;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        databaseHelper = new DatabaseHelper(this);
        final String phone = getIntent().getStringExtra("phone_no");
        Log.d("Phone", phone);
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
                Intent intent = new Intent(AnimalMenu.this, AddAnimPage.class);
                intent.putExtra("phone_no", phone);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AnimalMenu.this, MainMenu.class);
        startActivity(intent);
        finish();
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