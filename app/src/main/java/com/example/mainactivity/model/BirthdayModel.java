package com.example.mainactivity.model;

import java.util.ArrayList;

public class BirthdayModel {
    private String navn, dato, mobil;

    public BirthdayModel(int i, String navn, String dato, String mobil) {
        this.navn = navn;
        this.dato = dato;
        this.mobil = mobil;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public static ArrayList<BirthdayModel> getData() {
        ArrayList<BirthdayModel> data = new ArrayList<>();

        //Egendefinert. Skal hentes fra database
        String[] navn = {"Emilie"};
        String[] mobil = {"12345678"};
        String[] dato = {"6.januar.1998"};

        for(int i = 0; i < navn.length; i++) {
            BirthdayModel enBursdag = new BirthdayModel(i, navn[i], mobil[i], dato[i]);
            data.add(enBursdag);
        }

        return data;
    }

}
