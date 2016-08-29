package com.example.rio.plano_petshop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rio on 29/08/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final DATABASE_NAME = "PetShop.db";
    public static final TABLE_USER = "user_petshop;
    public static final TABLE_CUST = "cust_petshop";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE "+TABLE_USER+" (ID INTEGER PRIMARY KEY, username TEXT, password TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        onCreate(db);
    }

    public void custCreate(SQLiteDatabase db) {
        db.execSQL("CREATE "+TABLE_CUST+" (ID INTEGER PRIMARY KEY, nama TEXT, alamat TEXT, nomorhp TEXT, hewan TEXT, umur INTEGER, gender TEXT)");
    }

    public void custUpgrade(SQLiteDatabase db, int OldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CUST);
        custCreate();
    }
}
