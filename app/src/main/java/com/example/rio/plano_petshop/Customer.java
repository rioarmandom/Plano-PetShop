package com.example.rio.plano_petshop;

/**
 * Created by almantera on 10/11/16.
 */

public class Customer {

    private int cust_id;
    private String name;
    private String address;
    private String phone_no;

    public Customer() {
        this.name = "";
        this.address = "";
        this.phone_no = "";
    }

    public Customer(String name, String address, String phone_no) {
        this.name = name;
        this.address = address;
        this.phone_no = phone_no;
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

}
