package com.example.rio.plano_petshop;

import android.os.Parcel;

/**
 * Created by almanalfaruq on 22/11/2016.
 */

public class Animal {

    private int ani_id;
    private int cust_id;
    private String ani_name;
    private String ani_type;
    private int ani_age;
    private String ani_sex;

    public Animal() {

    }

    public Animal(int ani_id, int cust_id, String ani_name, String ani_type, int ani_age, String ani_sex) {
        this.ani_id = ani_id;
        this.cust_id = cust_id;
        this.ani_name = ani_name;
        this.ani_type = ani_type;
        this.ani_age = ani_age;
        this.ani_sex = ani_sex;
    }

    public Animal(int cust_id, String ani_name, String ani_type, int ani_age, String ani_sex) {
        this.cust_id = cust_id;
        this.ani_name = ani_name;
        this.ani_type = ani_type;
        this.ani_age = ani_age;
        this.ani_sex = ani_sex;
    }

    public int getAni_id() {
        return ani_id;
    }

    public void setAni_id(int ani_id) {
        this.ani_id = ani_id;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getAni_name() {
        return ani_name;
    }

    public void setAni_name(String ani_name) {
        this.ani_name = ani_name;
    }

    public String getAni_type() {
        return ani_type;
    }

    public void setAni_type(String ani_type) {
        this.ani_type = ani_type;
    }

    public int getAni_age() {
        return ani_age;
    }

    public void setAni_age(int ani_age) {
        this.ani_age = ani_age;
    }

    public String getAni_sex() {
        return ani_sex;
    }

    public void setAni_sex(String ani_sex) {
        this.ani_sex = ani_sex;
    }

}
