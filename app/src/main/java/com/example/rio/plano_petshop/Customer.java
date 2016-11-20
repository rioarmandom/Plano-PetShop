package com.example.rio.plano_petshop;

/**
 * Created by almantera on 10/11/16.
 */

public class Customer {

    private int cust_id;
    private String name;
    private String address;
    private String phone_no;
    private String ani_type;
    private int ani_age;
    private String ani_sex;

    public Customer() {
        this.name = "";
        this.address = "";
        this.phone_no = "";
        this.ani_type = "";
        this.ani_age = 0;
        this.ani_sex = "";
    }

    public Customer(String name, String address, String phone_no, String ani_type, int ani_age, String ani_sex) {
        this.name = name;
        this.address = address;
        this.phone_no = phone_no;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
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
