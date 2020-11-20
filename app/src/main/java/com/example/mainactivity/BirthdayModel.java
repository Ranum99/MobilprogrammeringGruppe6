package com.example.mainactivity;

public class BirthdayModel {

    // Variabler
    private String navn, dato, id;

    public BirthdayModel(String navn, String dato, String id) {

        // Setter variablene
        this.navn = navn;
        this.dato = dato;
        this.id = id;

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

}
