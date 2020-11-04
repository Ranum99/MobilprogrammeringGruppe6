package com.example.mainactivity.model;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BirthdayModel extends AppCompatActivity {

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
        SharedPreferences sharedPreferences;
        ArrayList<BirthdayModel> data = new ArrayList<>();
        //sharedPreferences = getSharedPreferences(User.SESSION, MODE_PRIVATE);

        String[] navn = {"Emilie"};
        String[] mobil = {"12345678"};
        String[] dato = {"06011998"};

        for(int i = 0; i < navn.length; i++) {
            BirthdayModel enBursdag = new BirthdayModel(i, navn[i], mobil[i], dato[i]);
            data.add(enBursdag);
        }

        return data;
    }

}
