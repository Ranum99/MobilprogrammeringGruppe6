package com.example.mainactivity;

public class HandlelisteVarerModel {
    private String id, vare, uke;

    public HandlelisteVarerModel(String id, String vare, String uke) {
        this.id = id;
        this.vare = vare;
        this.uke = uke;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVare() {
        return vare;
    }

    public void setVare(String vare) {
        this.vare = vare;
    }

    public String getUke() {
        return uke;
    }

    public void setUke(String uke) {
        this.uke = uke;
    }
}
