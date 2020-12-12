package com.example.mainactivity;

public class MatplanModel {

    // Variabler
    private int matplanID, familyID, week;
    private String fromDate, toDate;

    public MatplanModel(int matplanID, int familyID, int week, String fromDate, String toDate) {
        this.matplanID = matplanID;
        this.familyID = familyID;
        this.week = week;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getMatplanID() {
        return matplanID;
    }

    public int getFamilyID() {
        return familyID;
    }

    public int getWeek() {
        return week;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }
}
