package com.example.mainactivity;

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
        String[] navn = {"Emilie", "Pål", "Sara", "Wenche","Emilie", "Pål", "Sara", "Wenche","Emilie", "Pål", "Sara", "Wenche"};
        String[] mobil = {"48055568", "45769179", "90035296", "47058583","48055568", "45769179", "90035296", "47058583","48055568", "45769179", "90035296", "47058583"};
        String[] dato = {"06011998", "27021966", "06111998", "09011967","06011998", "27021966", "06111998", "09011967","06011998", "27021966", "06111998", "09011967"};

        for(int i = 0; i < navn.length; i++) {
            BirthdayModel enBursdag = new BirthdayModel(i, navn[i], mobil[i], dato[i]);
            data.add(enBursdag);
        }

        return data;
    }

}
