package com.example.rio.plano_petshop;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by almantera on 11/11/16.
 */

public class MainMenu extends Activity {

    DatabaseHelper databaseHelper;

    ImageButton btnID, btnExport, btnSearch;
    Button btnLogout;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_menu);
        databaseHelper = new DatabaseHelper(getApplicationContext());

        btnID = (ImageButton) findViewById(R.id.btnID);
        btnExport = (ImageButton) findViewById(R.id.btnExport);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Logout")
                        .setMessage("Are you sure want to log out?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (databaseHelper.updateLogStatus()) {
                                    Intent intent = new Intent(getApplicationContext(), LoginPage.class);
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
            }
        });
    }
}
