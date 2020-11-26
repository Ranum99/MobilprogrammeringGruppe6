package com.example.mainactivity;

import java.util.ArrayList;

public class OnskelisteModel {

    private int wishlistID;
    private String userToName, wishlistName;

    public OnskelisteModel(int wishlistID, String userToName, String wishlistName) {
        this.wishlistID = wishlistID;
        this.userToName = userToName;
        this.wishlistName = wishlistName;
    }

    public int getWishlistID() {
        return wishlistID;
    }

    public String getUserToName() {
        return userToName;
    }

    public String getWishlistName() {
        return wishlistName;
    }
}
