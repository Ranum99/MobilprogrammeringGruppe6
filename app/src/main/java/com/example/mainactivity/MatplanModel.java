package com.example.mainactivity;

public class MatplanModel {
    // Variabler
    private int matplanID;
    private int week;
    private String fromDate, toDate;

    public MatplanModel(int matplanID, int week, String fromDate, String toDate) {
        this.matplanID = matplanID;
        this.week = week;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getMatplanID() {
        return matplanID;
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
