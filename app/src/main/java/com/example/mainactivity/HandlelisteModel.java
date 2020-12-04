package com.example.mainactivity;

public class HandlelisteModel {
    private String tittel, id;
    private Integer familieID;

    public HandlelisteModel(String nr, String id, Integer familieID) {

        // Setter variablene
        this.tittel = nr;
        this.id = id;
        this.familieID = familieID;
    }

    public String getTittel() {
        return tittel;
    }

    public void setTittel(String tittel) {
        this.tittel = tittel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFamilieID() {
        return familieID;
    }

    public void setFamilieID(Integer familieID) {
        this.familieID = familieID;
    }
}

