package com.example.mainactivity;

import java.util.ArrayList;

public class HandlelisteModel {
    private String nr;

    public HandlelisteModel(int i, String nr) {
        this.nr = nr;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }
    public static ArrayList<HandlelisteModel> getData() {
        ArrayList<HandlelisteModel> data = new ArrayList<>();

        //Egendefinert. Skal hentes fra database
        String[] nr = {"1","2","3","400","10"};

        for(int i = 0; i < nr.length; i++) {
            HandlelisteModel enListe = new HandlelisteModel(i, nr[i]);
            data.add(enListe);
        }

        return data;
    }
}
