package com.example.rio.plano_petshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by almanalfaruq on 18/11/2016.
 */
public class MainMenu extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static ViewPagerAdapter adapter;

    Toolbar toolbar;
    ViewPager viewPager;
    FloatingActionButton fabAdd;
    DatabaseHelper databaseHelper;
    ExportDatabaseCSV exportDatabase;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        databaseHelper = new DatabaseHelper(this);
        bundle = new Bundle();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);//Set up View Pager

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, AECustomer.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionLogout) {
            new AlertDialog.Builder(MainMenu.this)
                    .setTitle("Logout")
                    .setMessage("Are you sure want to log out?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (databaseHelper.updateLogStatus()) {
                                Intent intent = new Intent(MainMenu.this, LoginPage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                dialog.cancel();
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else if (id == R.id.actionExport) {
            exportDatabase = new ExportDatabaseCSV(MainMenu.this);
            exportDatabase.execute();
//            Toast.makeText(MainMenu.this, "Can't export yet", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), bundle);
        adapter.addFrag(new CustomerFragment(), "RecyclerView", bundle);
        viewPager.setAdapter(adapter);
    }


    //Return current fragment on basis of Position
    public Fragment getFragment(int pos) {
        return adapter.getItem(pos);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Fragment recyclerFragment = new MainMenu().getFragment(0);//Get recycler fragment
        Toast.makeText(MainMenu.this, "Search for " + query, Toast.LENGTH_SHORT).show();
        if (recyclerFragment != null)
            ((CustomerFragment) recyclerFragment).filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Fragment recyclerFragment = new MainMenu().getFragment(0);//Get recycler fragment
        Toast.makeText(MainMenu.this, "Search for " + newText, Toast.LENGTH_SHORT).show();
        if (recyclerFragment != null)
            ((CustomerFragment) recyclerFragment).filter(newText);
        return true;
    }
}
