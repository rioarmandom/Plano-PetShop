package com.example.rio.plano_petshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rio on 29/08/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PetShop.db";
    public static final String TABLE_USER = "user_petshop";
    public static final String TABLE_CUST = "cust_petshop";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_USER+" (name TEXT, username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL, age INTEGER, address TEXT, birthday TEXT, phone_no TEXT)");
        db.execSQL("CREATE TABLE "+TABLE_CUST+" (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, phone_no TEXT, " +
                "ani_type TEXT, ani_age INTEGER, ani_sex TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUST);
        onCreate(db);
    }

    public boolean createUser(User user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("username", user.getUsername());
        values.put("password", password);
        values.put("age", user.getAge());
        values.put("address", user.getAddress());
        values.put("birthday", user.getBirthday());
        values.put("phone_no", user.getPhone_no());

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        if (result == -1) return false;
        else return true;
    }

    public int isLogin(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, "username=?", new String[]{username}, null, null, null);
        if (cursor.getCount()<1) {
            return -1;
        } else {
            cursor.moveToFirst();
            String dbPassword = cursor.getString(cursor.getColumnIndex("password"));
            if (dbPassword.equals(password)) {
                return 1;
            } else return 0;
        }
    }

    public boolean createCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", customer.getName());
        values.put("address", customer.getAddress());
        values.put("phone_no", customer.getPhone_no());
        values.put("ani_type", customer.getAni_type());
        values.put("ani_age", customer.getAni_age());
        values.put("ani_sex", customer.getAni_sex());

        long result = db.insert(TABLE_CUST, null, values);
        db.close();
        if (result == -1) return false;
        else return true;
    }

}
