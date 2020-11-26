package com.example.mainactivity;

public class HandlelisteModel {
    private String nr, id;

    public HandlelisteModel(String nr, String id) {

        // Setter variablene
        this.nr = nr;
        this.id = id;
    }

    public static List<HandlelisteModel> getData(ArrayList<String> overskriftHandleliste, ArrayList<String> varer) {
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

