package com.example.mainactivity;

public class BirthdayModel {

    // Variabler
    private String navn, dato, id, familieId, userID;

    public BirthdayModel(String navn, String dato, String id, String familieId, String userID) {

        // Setter variablene
        this.navn = navn;
        this.dato = dato;
        this.id = id;
        this.familieId = familieId;
        this.userID = userID;
    }

    // Getters and setters
    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFamilieId() {
        return familieId;
    }

    public String getUserID() {
        return userID;
    }

    public void setFamilieId(String familieId) {
        this.familieId = familieId;
    }
}
