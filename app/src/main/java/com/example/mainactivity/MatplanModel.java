package com.example.mainactivity;

public class MatplanModel {

    // Variabler
    private String ukenr, id;

    public MatplanModel(String id, String uke) {
        this.id = id;
        this.ukenr = uke;
    }

    // Getters and setters
    public String getUkenr() {
        return ukenr;
    }

    public void setUkenr(String ukenr) {
        this.ukenr = ukenr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
