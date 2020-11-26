package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HandlelisteModel extends AppCompatActivity {
    private String nr;

    public HandlelisteModel(int i, String nr) {
        this.nr = nr;
    }

    public static List<HandlelisteModel> getData(ArrayList<String> overskriftHandleliste, ArrayList<String> varer) {
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
