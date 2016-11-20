package com.example.rio.plano_petshop;

/**
 * Created by almantera on 10/11/16.
 */

public class User {

    private int id;
    private String name;
    private String username;
    private int age;
    private String address;
    private String birthday;
    private String email;
    private String phone_no;

    public User () {
        this.name = "";
        this.username = "";
        this.age = 0;
        this.address = "";
        this.birthday = "";
        this.email = "";
        this.phone_no = "";
    }

    public User (String name, String username, int age, String address, String birthday, String email, String phone_no) {
        this.name = name;
        this.username = username;
        this.age = age;
        this.address = address;
        this.birthday = birthday;
        this.email = email;
        this.phone_no = phone_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

}
