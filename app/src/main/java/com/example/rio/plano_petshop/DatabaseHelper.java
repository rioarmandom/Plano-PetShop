package com.example.rio.plano_petshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rio.plano_petshop.Model.Anamnesa;
import com.example.rio.plano_petshop.Model.Animal;
import com.example.rio.plano_petshop.Model.Customer;
import com.example.rio.plano_petshop.Model.Semuanya;
import com.example.rio.plano_petshop.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rio on 29/08/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private Customer customer;
    private User user;
    private Animal animal;
    private Anamnesa anamnesa;
    private Semuanya semuanya;

    public static final String DATABASE_NAME = "PetShop.db";
    public static final String TABLE_USER = "user_petshop";
    public static final String TABLE_CUST = "cust_petshop";
    public static final String TABLE_ANI = "ani_petshop";
    public static final String TABLE_ANAM = "anam_petshop";
    public static final String TABLE_ALL = "all_petshop";

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

    public static final String ID_ANI = "ani_id";
    public static final String NAME_ANI = "ani_name";
    public static final String TYPE_ANI = "ani_type";
    public static final String AGE_ANI = "ani_age";
    public static final String SEX_ANI = "ani_sex";

    public static final String ID_ANAM = "anam_id";
    public static final String ANAMNESA_ANAM = "anamnesa";
    public static final String TERAPHY_ANAM = "teraphy";
    public static final String DATE_ANAM = "date";

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
        db.execSQL("CREATE TABLE "+TABLE_ANI+" (ani_id INTEGER PRIMARY KEY AUTOINCREMENT, cust_id INTEGER, " +
                "ani_name TEXT, ani_type TEXT, ani_age INTEGER, ani_sex TEXT)");
        db.execSQL("CREATE TABLE "+TABLE_ANAM+" (anam_id INTEGER PRIMARY KEY AUTOINCREMENT, ani_id INTEGER, " +
                "date TEXT NOT NULL, anamnesa TEXT, teraphy TEXT)");
        db.execSQL("CREATE TABLE "+TABLE_ALL+" (all_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, " +
                "ani_name TEXT, ani_type TEXT, ani_age INTEGER, ani_sex TEXT, anamnesa TEXT, teraphy TEXT, date TEXT)");
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
        cursor.close();
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
        cursor.close();
        return customerList;
    }

    public List<Animal> getAnimal(int cust_id) {
        List<Animal> animalList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ANI + " WHERE cust_id=" + String.valueOf(cust_id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                animal = new Animal(cursor.getInt(cursor.getColumnIndex(ID_ANI)), cursor.getInt(1), cursor.getString(2), cursor.getString(3),
                        cursor.getInt(4), cursor.getString(5));
                animalList.add(animal);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return animalList;
    }

    public Animal getOneAnimal(int ani_id) {
        Animal animal = new Animal();
        String selectQuery = "SELECT * FROM " + TABLE_ANI + " WHERE ani_id=" + String.valueOf(ani_id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            animal = new Animal(cursor.getInt(cursor.getColumnIndex(ID_ANI)), cursor.getInt(1), cursor.getString(2)
                    , cursor.getString(3), cursor.getInt(4), cursor.getString(5));
        }
        return animal;
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
        cursor.close();
        return anamnesaList;
    }

    public Anamnesa getAnamDetail(int ani_id, int anam_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_ANAM, null, "ani_id=? AND anam_id=?"
                , new String[]{String.valueOf(ani_id), String.valueOf(anam_id)},  null, null, null, null);
        if (cursor.moveToFirst()) {
            anamnesa = new Anamnesa(cursor.getInt(0), cursor.getInt(1)
                    , cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }
        return anamnesa;
    }

    public List<Semuanya> getSemuanya() {
        List<Semuanya> semuanyaList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String gabungQuery = "SELECT name,ani_name,ani_type,ani_age,ani_sex,anamnesa,teraphy,date" +
                " FROM " + TABLE_CUST + "," + TABLE_ANI + "," + TABLE_ANAM +
                " WHERE cust_petshop.cust_id=ani_petshop.cust_id AND ani_petshop.ani_id=anam_petshop.ani_id" +
                " ORDER BY name ASC";
        String selectQuery = "SELECT * FROM " + TABLE_ALL +" ORDER BY name ASC";
        Cursor cursor = db.rawQuery(gabungQuery, null);
        if (cursor.moveToFirst()) {
            db = this.getWritableDatabase();
            do {
                semuanya = new Semuanya(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)
                        , cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                ContentValues values = new ContentValues();
                values.put(NAME_CUST, semuanya.getName());
                values.put(NAME_ANI, semuanya.getAni_name());
                values.put(TYPE_ANI, semuanya.getAni_type());
                values.put(AGE_ANI, semuanya.getAni_age());
                values.put(SEX_ANI, semuanya.getAni_sex());
                values.put(ANAMNESA_ANAM, semuanya.getAnamnesa());
                values.put(TERAPHY_ANAM, semuanya.getTeraphy());
                values.put(DATE_ANAM, semuanya.getDate());
                db.insert(TABLE_ALL, null, values);
            } while (cursor.moveToNext());
        }
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                semuanya = new Semuanya(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4)
                        , cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
                semuanyaList.add(semuanya);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.delete(TABLE_ALL, null, null);
        db.close();
        return semuanyaList;
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

    public boolean updateAnimal(Animal animal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_ANI, animal.getAni_name());
        values.put(TYPE_ANI, animal.getAni_type());
        values.put(AGE_ANI, animal.getAni_age());
        values.put(SEX_ANI, animal.getAni_sex());
        int rowAffected = db.update(TABLE_ANI, values, "ani_id=?", new String[]{String.valueOf(animal.getAni_id())});
        return rowAffected == 1;
    }

    public boolean updateAnamnesa(Anamnesa anamnesa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ANAMNESA_ANAM, anamnesa.getAnamnesa());
        values.put(TERAPHY_ANAM, anamnesa.getTeraphy());
        values.put(DATE_ANAM, anamnesa.getDate());
        int rowAffected = db.update(TABLE_ANAM, values, "ani_id=? AND anam_id=?"
                , new String[]{String.valueOf(anamnesa.getAni_id()), String.valueOf(anamnesa.getAnam_id())});
        return rowAffected == 1;
    }

    public int getIdCust(String phone_no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CUST, null, "phone_no=?", new String[]{phone_no}, null, null, null);
        int i=0;
        if (cursor.moveToFirst()) {
            i = cursor.getInt(0);
        }
        cursor.close();
        return i;
    }

    public String getNameCust(String phone_no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CUST, null, "phone_no=?", new String[]{phone_no}, null, null, null);
        String i="";
        if (cursor.moveToFirst()) {
            i = cursor.getString(cursor.getColumnIndex(NAME_CUST));
        }
        cursor.close();
        return i;
    }

    public Customer getCustomer(String phone_no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CUST, null, "phone_no=?", new String[]{phone_no}, null, null, null);
        if (cursor.moveToFirst()) {
            customer = new Customer(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }
        return  customer;
    }

    public boolean updateCust(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_CUST, customer.getName());
        values.put(ADDRESS_CUST, customer.getAddress());
        values.put(PHONE_CUST, customer.getPhone_no());
        int rowAffected = db.update(TABLE_CUST, values, "cust_id=?", new String[]{String.valueOf(customer.getCust_id())});
        return rowAffected == 1;
    }

    public String getAddressCust(String phone_no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CUST, null, "phone_no=?", new String[]{phone_no}, null, null, null);
        String i="";
        if (cursor.moveToFirst()) {
            i = cursor.getString(cursor.getColumnIndex(ADDRESS_CUST));
        }
        cursor.close();
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
        cursor.close();
        return customerList;
    }

    public int deleteCust(String phone_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_CUST, null, "phone_no=?", new String[]{phone_no}, null, null, null);
        int cust_id = 0;
        if (cursor.moveToFirst()) {
            cust_id = cursor.getInt(cursor.getColumnIndex(ID_CUST));
        }
        int ani_id = 0;
        cursor = db.query(TABLE_CUST, null, "cust_id=?", new String[]{phone_no}, null, null, null);
        if (cursor.moveToFirst()) {
            ani_id = cursor.getInt(cursor.getColumnIndex(ID_ANI));
        }
        db.delete(TABLE_ANAM, "ani_id=?", new String[]{String.valueOf(ani_id)});
        db.delete(TABLE_ANI, "cust_id=?", new String[]{String.valueOf(cust_id)});
        int i = db.delete(TABLE_CUST, "phone_no=?", new String[]{phone_no});
        cursor.close();
        return i;
    }

    public int deleteAnimal(int ani_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ANAM, "ani_id=?", new String[]{String.valueOf(ani_id)});
        int i = db.delete(TABLE_ANI, "ani_id=?", new String[]{String.valueOf(ani_id)});
        db.close();
        return i;
    }

    public int deletAnam(int anam_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_ANAM, "anam_id=?", new String[]{String.valueOf(anam_id)});
        return i;
    }
}
