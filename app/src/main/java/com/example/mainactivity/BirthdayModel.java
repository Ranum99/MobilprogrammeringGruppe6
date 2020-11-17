package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class BirthdayModel extends AppCompatActivity {

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

    // Henter data fra databasen og lagrer i en ArrayList
    public static ArrayList<BirthdayModel> getData(ArrayList<String> navn, ArrayList<String> dato, ArrayList<String> id) {
        ArrayList<BirthdayModel> data = new ArrayList<>();

        for(int i = 0; i < navn.size(); i++) {

            BirthdayModel enBursdag = new BirthdayModel(navn.get(i), dato.get(i), id.get(i));
            data.add(enBursdag);
        }

        return data;
    }

}
