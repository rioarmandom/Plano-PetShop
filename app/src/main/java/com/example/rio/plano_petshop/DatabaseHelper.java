package com.example.rio.plano_petshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rio on 29/08/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    Customer customer;
    User user;

    public static final String DATABASE_NAME = "PetShop.db";
    public static final String TABLE_USER = "user_petshop";
    public static final String TABLE_CUST = "cust_petshop";

    public static final String NAME_USER = "name";
    public static final String UNAME_USER = "username";
    public static final String PASS_USER = "password";
    public static final String AGE_USER = "age";
    public static final String ADDRESS_USER = "address";
    public static final String BIRTH_USER = "birthday";
    public static final String PHONE_USER = "phone_no";
    public static final String LOG_USER = "log_status";

    public static final String ID_CUST = "cust_id";
    public static final String NAME_CUST = "name";
    public static final String ADDRESS_CUST = "address";
    public static final String PHONE_CUST = "phone_no";
    public static final String TYPE_ANI = "ani_type";
    public static final String AGE_ANI = "ani_age";
    public static final String SEX_ANI = "ani_sex";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_USER+" (name TEXT, username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL, age INTEGER, address TEXT, birthday TEXT, phone_no TEXT, log_status INTEGER)");
        db.execSQL("CREATE TABLE "+TABLE_CUST+" (cust_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT" +
                ", phone_no TEXT, ani_type TEXT, ani_age INTEGER, ani_sex TEXT) ");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUST);
        onCreate(db);
    }

    public boolean createUser(User user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_USER, user.getName());
        values.put(UNAME_USER, user.getUsername());
        values.put(PASS_USER, password);
        values.put(AGE_USER, user.getAge());
        values.put(ADDRESS_USER, user.getAddress());
        values.put(BIRTH_USER, user.getBirthday());
        values.put(PHONE_USER, user.getPhone_no());
        values.put(LOG_USER, 0);

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result != -1;
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
        values.put(NAME_CUST, customer.getName());
        values.put(ADDRESS_CUST, customer.getAddress());
        values.put(PHONE_CUST, customer.getPhone_no());
        values.put(TYPE_ANI, customer.getAni_type());
        values.put(AGE_ANI, customer.getAni_age());
        values.put(SEX_ANI, customer.getAni_sex());
        long result = db.insert(TABLE_CUST, null, values);
        db.close();
        return result != -1;
    }

    public List<Customer> getAllCust() {
        List<Customer> customerList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CUST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                customer = new Customer(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)
                        , cursor.getInt(5), cursor.getString(6));
                customerList.add(customer);
            } while (cursor.moveToNext());
        }
        return customerList;
    }

    public boolean logStatus() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(log_status) AS log_status FROM " + TABLE_USER, null);
        cursor.moveToFirst();
        int status = cursor.getInt(cursor.getColumnIndex("log_status"));
        cursor.close();
        return status == 1;
    }

    public boolean updateLogStatus() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("log_status", 0);
        int rowAffected = db.update(TABLE_USER, values, "log_status=1", null);
        return rowAffected == 1;
    }

    public int getIdCust(String name, String phone_no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, "name=? AND phone_no=?", new String[]{name, phone_no}, null, null, null);
        int i=0;
        if (cursor.moveToFirst()) {
            i = cursor.getInt(cursor.getColumnIndex(ID_CUST));
        }
        return i;
    }

    public int deleteCust(String name, String phone_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i =db.delete(TABLE_CUST, "name=? AND phone=?", new String[]{name, phone_no});
        return i;
    }
}
