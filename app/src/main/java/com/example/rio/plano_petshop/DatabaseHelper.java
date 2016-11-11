package com.example.rio.plano_petshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

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
                "password TEXT NOT NULL, age INTEGER, address TEXT, birthday TEXT, phone_no TEXT, log_status INTEGER)");
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
        System.out.println(user.getName());
        values.put("username", user.getUsername());
        System.out.println(user.getUsername());
        values.put("password", password);
        System.out.println(password);
        values.put("age", user.getAge());
        System.out.println(user.getAge());
        values.put("address", user.getAddress());
        System.out.println(user.getAddress());
        values.put("birthday", user.getBirthday());
        System.out.println(user.getBirthday());
        values.put("phone_no", user.getPhone_no());
        System.out.println(user.getPhone_no());
        values.put("log_status", 0);

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        if (result == -1) return false;
        else return true;
    }

    public int goLogin(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, "username=?", new String[]{username}, null, null, null);
        ContentValues values = new ContentValues();
        values.put("log_status", 1);
        if (cursor.getCount()<1) {
            return -1;
        } else {
            cursor.moveToFirst();
            String dbPassword = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            if (dbPassword.equals(password)) {
                db = this.getWritableDatabase();
                db.update(TABLE_USER, values, "username=?", new String[]{username});
                db.close();
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

    public boolean logStatus() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(log_status) AS log_status FROM " + TABLE_USER, null);
        cursor.moveToFirst();
        int status = cursor.getInt(cursor.getColumnIndex("log_status"));
        cursor.close();
        if (status == 1) return true;
        else return false;
    }

    public boolean updateLogStatus() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("log_status", 0);
        int rowAffected = db.update(TABLE_USER, values, "log_status=1", null);
        if (rowAffected == 1) return true;
        else return false;
    }
}
