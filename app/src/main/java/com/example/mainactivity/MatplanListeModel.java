package com.example.mainactivity;

public class MatplanListeModel {
    private int subMatplanID, matplanID;
    private String day, food;

    public MatplanListeModel(int matplanID, String day, String food) {
        this.matplanID = matplanID;
        this.day = day;
        this.food = food;
    }

    public MatplanListeModel(int subMatplanID, int matplanID, String day, String food) {
        this.subMatplanID = subMatplanID;
        this.matplanID = matplanID;
        this.day = day;
        this.food = food;
    }

    public int getSubMatplanID() {
        return subMatplanID;
    }

    public int getMatplanID() {
        return matplanID;
    }

    public String getDay() {
        return day;
    }

    public String getFood() {
        return food;
    }
}
