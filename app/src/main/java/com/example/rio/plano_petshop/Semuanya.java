package com.example.rio.plano_petshop;

/**
 * Created by almanalfaruq on 18/12/2016.
 */

public class Semuanya {

    private int all_id;
    private String name;
    private String ani_name;
    private String ani_type;
    private int ani_age;
    private String ani_sex;
    private String anamnesa;
    private String teraphy;
    private String date;

    public Semuanya(int all_id, String name, String ani_name, String ani_type, int ani_age, String ani_sex, String anamnesa, String teraphy, String date) {
        this.all_id = all_id;
        this.name = name;
        this.ani_name = ani_name;
        this.ani_type = ani_type;
        this.ani_age = ani_age;
        this.ani_sex = ani_sex;
        this.anamnesa = anamnesa;
        this.teraphy = teraphy;
        this.date = date;
    }

    public Semuanya(String name, String ani_name, String ani_type, int ani_age, String ani_sex, String anamnesa, String teraphy, String date) {
        this.name = name;
        this.ani_name = ani_name;
        this.ani_type = ani_type;
        this.ani_age = ani_age;
        this.ani_sex = ani_sex;
        this.anamnesa = anamnesa;
        this.teraphy = teraphy;
        this.date = date;
    }

    public int getAll_id() {
        return all_id;
    }

    public void setAll_id(int all_id) {
        this.all_id = all_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAnamnesa() {
        return anamnesa;
    }

    public void setAnamnesa(String anamnesa) {
        this.anamnesa = anamnesa;
    }

    public String getTeraphy() {
        return teraphy;
    }

    public void setTeraphy(String teraphy) {
        this.teraphy = teraphy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

