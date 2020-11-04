package com.example.mainactivity.model;

import java.util.ArrayList;

public class MatplanModel {
    String ukenr;

    public MatplanModel(int i, String uke) {
        this.ukenr = uke;
    }

    public String getUkenr() {
        return ukenr;
    }

    public void setUkenr(String ukenr) {
        this.ukenr = ukenr;
    }
    public static ArrayList<MatplanModel> getData() {
        ArrayList<MatplanModel> data = new ArrayList<>();

        //Egendefinert. Skal hentes fra database
        Integer[] uke = {1,2,3,4,5,6,7,8,9,10};
        for(int i = 0; i < uke.length; i++) {
            MatplanModel enMatplan = new MatplanModel(i, uke[i].toString());
            data.add(enMatplan);
        }

        return data;
    }
}
