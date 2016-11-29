package com.example.rio.plano_petshop;

/**
 * Created by almanalfaruq on 22/11/2016.
 */

public class Animal {

    private int cust_id;
    private String ani_type;
    private int ani_age;
    private String ani_sex;

    public Animal() {
        this.cust_id = 0;
        this.ani_type = "";
        this.ani_age = 0;
        this.ani_sex = "";
    }

    public Animal(int cust_id, String ani_type, int ani_age, String ani_sex) {
        this.cust_id = cust_id;
        this.ani_type = ani_type;
        this.ani_age = ani_age;
        this.ani_sex = ani_sex;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
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
