package com.example.mainactivity;

public class OnskelisteListeModel {
    private int wishID, wishlistID, userID;
    private String wish;
    private boolean checkBox;

    public OnskelisteListeModel(int wishID, int wishlistID, int userID, String wish, boolean checkBox) {
        this.wishID = wishID;
        this.wishlistID = wishlistID;
        this.userID = userID;
        this.wish = wish;
        this.checkBox = checkBox;
    }

    public int getWishID() {
        return wishID;
    }

    public int getWishlistID() {
        return wishlistID;
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
