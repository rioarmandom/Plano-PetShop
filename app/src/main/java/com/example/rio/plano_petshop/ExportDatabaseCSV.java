package com.example.rio.plano_petshop;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by almanalfaruq on 18/12/2016.
 */

public class ExportDatabaseCSV extends AsyncTask<String ,String, Boolean> {

    ProgressDialog dialog;
    Context context;
    DatabaseHelper dbHelper;
    String strExportDir;

    public ExportDatabaseCSV(Context context) {
        dialog = new ProgressDialog(context);
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Exporting database...");
        this.dialog.show();
    }

    protected Boolean doInBackground(final String... args){
        boolean success = false, memoryErr = false;
        String currentDateString = new SimpleDateFormat("ddMMyyyy").format(new Date());

        File dbFile = context.getDatabasePath(dbHelper.DATABASE_NAME);
        Log.v("MainMenu : ", "DB path is: " + dbFile);

        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        strExportDir = exportDir.toString();
        long freeBytesInternal = new File(context.getFilesDir().getAbsoluteFile().toString()).getFreeSpace();
        long megAvailable = freeBytesInternal / 1048576;

        if (megAvailable < 0.1) {
            System.out.println("Please check"+megAvailable);
            memoryErr = true;
        } else {
            Log.v("MainMenu", "exportDir path::"+exportDir);
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            File file = new File(exportDir, "PianoPetShop_" + currentDateString +".csv");
            try {

                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                //data
                List<Semuanya> semuanyaList = new ArrayList<>();
                semuanyaList = dbHelper.getSemuanya();
                if (semuanyaList.size() > 0) {
                    //Headers
                    String arrStr1[] ={"Customer Name"
                            , "Animal Name"
                            , "Animal Type"
                            , "Animal Age"
                            , "Animal Sex"
                            , "Anamnesa"
                            , "Teraphy"
                            , "Date"};
                    csvWrite.writeNext(arrStr1);

                    for (int i=0;i<semuanyaList.size();i++) {
                        String arrStr[] ={semuanyaList.get(i).getName()
                                , semuanyaList.get(i).getAni_name()
                                , semuanyaList.get(i).getAni_type()
                                , String.valueOf(semuanyaList.get(i).getAni_age())
                                , semuanyaList.get(i).getAni_sex()
                                , semuanyaList.get(i).getAnamnesa()
                                , semuanyaList.get(i).getTeraphy()
                                , semuanyaList.get(i).getDate()};
                        csvWrite.writeNext(arrStr);
                    }
                    success = true;
                }
                csvWrite.close();
            }catch (IOException e){
                Log.e("MainMenu", e.getMessage(), e);
                return success;
            }
        }
        return success;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPostExecute(Boolean success) {
        if (this.dialog.isShowing()){
            this.dialog.dismiss();
        }
        if (success){
            Toast.makeText(context, "Export successful!", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Exported to: " + strExportDir, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, "Export failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
