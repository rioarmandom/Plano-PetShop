package com.example.rio.plano_petshop;

/**
 * Created by almanalfaruq on 13/12/2016.
 */

public class Anamnesa {

    private int anam_id;
    private int ani_id;
    private String date;
    private String anamnesa;
    private String teraphy;

    public Anamnesa()  {

    }

    public Anamnesa(int anam_id, int ani_id, String date, String anamnesa, String teraphy) {
        this.anam_id = anam_id;
        this.ani_id = ani_id;
        this.date = date;
        this.anamnesa = anamnesa;
        this.teraphy = teraphy;
    }

    public Anamnesa(int ani_id, String date, String anamnesa, String teraphy) {
        this.ani_id = ani_id;
        this.date = date;
        this.anamnesa = anamnesa;
        this.teraphy = teraphy;
    }

    public int getAnam_id() {
        return anam_id;
    }

    public void setAnam_id(int anam_id) {
        this.anam_id = anam_id;
    }

    public int getAni_id() {
        return ani_id;
    }

    public void setAni_id(int ani_id) {
        this.ani_id = ani_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

}
