package com.example.rio.plano_petshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rio on 29/08/2016.
 */
// TODO : ANIMAL DETAIL
public class DatabaseHelper extends SQLiteOpenHelper {

    private Customer customer;
    User user;
    private Animal animal;
    private Anamnesa anamnesa;

    private static final String DATABASE_NAME = "PetShop.db";
    private static final String TABLE_USER = "user_petshop";
    private static final String TABLE_CUST = "cust_petshop";
    private static final String TABLE_ANI = "ani_petshop";
    private static final String TABLE_ANAM = "anam_petshop";

    private static final String NAME_USER = "name";
    private static final String UNAME_USER = "username";
    private static final String PASS_USER = "password";
    private static final String AGE_USER = "age";
    private static final String ADDRESS_USER = "address";
    private static final String BIRTH_USER = "birthday";
    private static final String PHONE_USER = "phone_no";
    private static final String LOG_USER = "log_status";

    private static final String ID_CUST = "cust_id";
    private static final String NAME_CUST = "name";
    private static final String ADDRESS_CUST = "address";
    private static final String PHONE_CUST = "phone_no";

    private static final String ID_ANI = "ani_id";
    private static final String NAME_ANI = "ani_name";
    private static final String TYPE_ANI = "ani_type";
    private static final String AGE_ANI = "ani_age";
    private static final String SEX_ANI = "ani_sex";

    private static final String ID_ANAM = "anam_id";
    private static final String ANAMNESA_ANAM = "anamnesa";
    private static final String TERAPHY_ANAM = "teraphy";
    private static final String DATE_ANAM = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_USER+" (name TEXT, username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL, age INTEGER, address TEXT, birthday TEXT, phone_no TEXT, log_status INTEGER)");
        db.execSQL("CREATE TABLE "+TABLE_CUST+" (cust_id INTEGER DEFAULT 1, name TEXT, address TEXT" +
                ", phone_no TEXT PRIMARY KEY)");
        db.execSQL("CREATE TABLE "+TABLE_ANI+" (ani_id INTEGER PRIMARY KEY AUTO_INCREMENT, cust_id INTEGER, " +
                "ani_name TEXT, ani_type TEXT, ani_age INTEGER, ani_sex TEXT)");
        db.execSQL("CREATE TABLE "+TABLE_ANAM+" (anam_id INTEGER PRIMARY KEY AUTO_INCREMENT, ani_id INTEGER, " +
                "date TEXT NOT NULL, anamnesa TEXT, teraphy TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANAM);
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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(cust_id) AS cust_id FROM " + TABLE_CUST, null);
        int i = 0;
        if (cursor.moveToFirst()) {
            i = cursor.getInt(cursor.getColumnIndex("cust_id"));
        }
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_CUST, i+1);
        values.put(NAME_CUST, customer.getName());
        values.put(ADDRESS_CUST, customer.getAddress());
        values.put(PHONE_CUST, customer.getPhone_no());
        long result = db.insert(TABLE_CUST, null, values);
        db.close();
        return result != -1;
    }

    public boolean createAnimal(Animal animal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_CUST, animal.getCust_id());
        values.put(NAME_ANI, animal.getAni_name());
        values.put(TYPE_ANI, animal.getAni_type());
        values.put(AGE_ANI, animal.getAni_age());
        values.put(SEX_ANI, animal.getAni_sex());
        long result = db.insert(TABLE_ANI, null, values);
        db.close();
        return result != -1;
    }

    public boolean createAnamnesa(Anamnesa anamnesa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_ANI, anamnesa.getAni_id());
        values.put(DATE_ANAM, anamnesa.getDate());
        values.put(ANAMNESA_ANAM, anamnesa.getAnamnesa());
        values.put(TERAPHY_ANAM, anamnesa.getTeraphy());
        long result = db.insert(TABLE_ANAM, null, values);
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
                customer = new Customer(cursor.getString(1), cursor.getString(2), cursor.getString(3));
                customerList.add(customer);
            } while (cursor.moveToNext());
        }
        return customerList;
    }

    public List<Animal> getAllAnimal() {
        List<Animal> animalList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ANI;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                animal = new Animal(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(2),
                        cursor.getInt(3), cursor.getString(4));
                animalList.add(animal);
            } while (cursor.moveToNext());
        }
        return animalList;
    }

    public List<Animal> getAnimal(int cust_id) {
        List<Animal> animalList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ANI + " WHERE cust_id=" + String.valueOf(cust_id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                animal = new Animal(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(2),
                        cursor.getInt(3), cursor.getString(4));
                animalList.add(animal);
            } while (cursor.moveToNext());
        }
        return animalList;
    }

    public List<Anamnesa> getAnamnesa(int ani_id) {
        List<Anamnesa> anamnesaList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ANAM + " WHERE ani_id=" + String.valueOf(ani_id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                anamnesa = new Anamnesa(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4));
                anamnesaList.add(anamnesa);
            } while (cursor.moveToNext());
        }
        return anamnesaList;
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

    public int getIdCust(String phone_no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CUST, null, "phone_no=?", new String[]{phone_no}, null, null, null);
        int i=0;
        if (cursor.moveToFirst()) {
            i = cursor.getInt(0);
        }
        return i;
    }

    public String getNameCust(String phone_no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CUST, null, "phone_no=?", new String[]{phone_no}, null, null, null);
        String i="";
        if (cursor.moveToFirst()) {
            i = cursor.getString(cursor.getColumnIndex(NAME_CUST));
        }
        return i;
    }

    public String getAddressCust(String phone_no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CUST, null, "phone_no=?", new String[]{phone_no}, null, null, null);
        String i="";
        if (cursor.moveToFirst()) {
            i = cursor.getString(cursor.getColumnIndex(ADDRESS_CUST));
        }
        return i;
    }

    public List<Customer> searchCustomer(String name) {
        List<Customer> customerList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CUST + " WHERE " + NAME_CUST + " LIKE '%" + name + "%'", null);
        if (cursor.moveToFirst()) {
            do {
                customer = new Customer(cursor.getString(1), cursor.getString(2), cursor.getString(3));
                customerList.add(customer);
            } while (cursor.moveToNext());
        }
        return customerList;
    }

    public int deleteCust(String phone_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_CUST, null, "phone_no=?", new String[]{phone_no}, null, null, null);
        int cust_id = 0;
        if (cursor.moveToFirst()) {
            cust_id = cursor.getInt(cursor.getColumnIndex(ID_CUST));
        }
        db.delete(TABLE_ANI, "cust_id=?", new String[]{String.valueOf(cust_id)});
        int i = db.delete(TABLE_CUST, "phone_no=?", new String[]{phone_no});
        return i;
    }

}
