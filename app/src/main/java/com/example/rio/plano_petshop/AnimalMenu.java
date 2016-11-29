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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.setting, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.actionLogout) {
//            new AlertDialog.Builder(AnimalMenu.this)
//                    .setTitle("Logout")
//                    .setMessage("Are you sure want to log out?")
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (databaseHelper.updateLogStatus()) {
//                                Intent intent = new Intent(AnimalMenu.this, LoginPage.class);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                dialog.cancel();
//                            }
//                        }
//                    })
//                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    })
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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