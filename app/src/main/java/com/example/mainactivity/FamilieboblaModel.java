package com.example.mainactivity;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FamilieboblaModel {
    private String id, navn, samtaleName;

    public FamilieboblaModel(String id, String navn, String samtaleName) {
        this.id = id;
        this.navn = navn;
        this.samtaleName = samtaleName;
    }

    public String getNavn() {
        return navn;
    }

    public String getIden() {
        return id;
    }

    public String getSamtaleName() {
        return samtaleName;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public static ArrayList<FamilieboblaModel> getData(ArrayList<String> ids, ArrayList<String> names, ArrayList<String> samtaleName) {
        ArrayList<FamilieboblaModel> data = new ArrayList<>();

        for(int i = 0; i < names.size(); i++) {
            FamilieboblaModel enSamtale = new FamilieboblaModel(ids.get(i), names.get(i), samtaleName.get(i));
            data.add(enSamtale);
        }

        return data;
    }
}
