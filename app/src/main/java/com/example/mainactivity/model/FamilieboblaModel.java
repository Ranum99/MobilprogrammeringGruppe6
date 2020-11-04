package com.example.mainactivity.model;

import java.util.ArrayList;

public class FamilieboblaModel {
    private String navn;

    public FamilieboblaModel(int i, String navn) {
        this.navn = navn;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public static ArrayList<FamilieboblaModel> getData() {
        ArrayList<FamilieboblaModel> data = new ArrayList<>();

        //Egendefinert. Skal hentes fra database
        String[] navn = {"Emilie"};

        for(int i = 0; i < navn.length; i++) {
            FamilieboblaModel enSamtale = new FamilieboblaModel(i, navn[i]);
            data.add(enSamtale);
        }

        return data;
    }
}
