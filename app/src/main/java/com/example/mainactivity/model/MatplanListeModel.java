package com.example.mainactivity.model;

public class MatplanListeModel {
    private int subMatplanID;
    private String day, food;

    public MatplanListeModel(int subMatplanID, String day, String food) {
        this.subMatplanID = subMatplanID;
        this.day = day;
        this.food = food;
    }

    public int getSubMatplanID() {
        return subMatplanID;
    }

    public String getDay() {
        return day;
    }

    public String getFood() {
        return food;
    }
}
