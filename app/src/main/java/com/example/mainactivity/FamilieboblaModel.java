package com.example.mainactivity;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FamilieboblaModel {
    private String id, navn;

    public FamilieboblaModel(String id, String navn) {
        this.id = id;
        this.navn = navn;
    }

    public String getNavn() {
        return navn;
    }

    public String getIden() {
        return id;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public static ArrayList<FamilieboblaModel> getData(ArrayList<String> ids, ArrayList<String> names) {
        ArrayList<FamilieboblaModel> data = new ArrayList<>();

        for(int i = 0; i < names.size(); i++) {
            FamilieboblaModel enSamtale = new FamilieboblaModel(ids.get(i), names.get(i));
            data.add(enSamtale);
        }

        return data;
    }
}
