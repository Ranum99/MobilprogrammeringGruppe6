package com.example.mainactivity.model;

public class OnskelisteListeModel {
    private int wishID;
    private int userID;
    private String wish;
    private boolean checkBox;

    public OnskelisteListeModel(int wishID, int userID, String wish, boolean checkBox) {
        this.wishID = wishID;
        this.userID = userID;
        this.wish = wish;
        this.checkBox = checkBox;
    }

    public int getWishID() {
        return wishID;
    }

    public int getUserID() {
        return userID;
    }

    public String getWish() {
        return wish;
    }

    public boolean getCheckBox() {
        return checkBox;
    }
}
