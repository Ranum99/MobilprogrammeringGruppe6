package com.example.mainactivity.model;

import java.util.ArrayList;

public class OnskelisteModel {

    private String navn;

    public OnskelisteModel(int i, String navn) {
        this.navn = navn;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public static ArrayList<OnskelisteModel> getData() {
        ArrayList<OnskelisteModel> data = new ArrayList<>();

        //Egendefinert. Skal hentes fra database
        String[] navn = {"Emilie"};

        for(int i = 0; i < navn.length; i++) {
            OnskelisteModel onskelister = new OnskelisteModel(i, navn[i]);
            data.add(onskelister);
        }

        return data;
    }
}
