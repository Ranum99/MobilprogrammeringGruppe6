package com.example.mainactivity.model;

public class OnskelisteModel {
    private int wishlistID, userToID;
    private String userToName, wishlistName;

    public OnskelisteModel(int wishlistID, String userToName, int userToID, String wishlistName) {
        this.wishlistID = wishlistID;
        this.userToName = userToName;
        this.userToID = userToID;
        this.wishlistName = wishlistName;
    }

    public int getWishlistID() {
        return wishlistID;
    }

    public int getUserToID() {
        return userToID;
    }

    public String getUserToName() {
        return userToName;
    }

    public String getWishlistName() {
        return wishlistName;
    }
}
