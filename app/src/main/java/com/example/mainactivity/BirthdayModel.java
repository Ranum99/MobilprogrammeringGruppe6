package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class BirthdayModel extends AppCompatActivity {

    private String navn, dato, mobil;


    public BirthdayModel(String navn, String dato, String mobil) {
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

    public static ArrayList<BirthdayModel> getData(ArrayList<String> navn, ArrayList <String> mobil, ArrayList<String> dato) {
        ArrayList<BirthdayModel> data = new ArrayList<>();

        data.clear();
        for(int i = 0; i < navn.size(); i++) {
            BirthdayModel enBursdag = new BirthdayModel(navn.get(i), mobil.get(i), dato.get(i));
            data.add(enBursdag);
        }

        return data;

    }

}
