package com.example.mainactivity.model;

public class FamilieboblaModel {
    private String samtaleID, userToName, samtaleName;

    public FamilieboblaModel(String samtaleID, String userToName, String samtaleName) {
        this.samtaleID = samtaleID;
        this.userToName = userToName;
        this.samtaleName = samtaleName;
    }

    public String getUserToName() {
        return userToName;
    }

    public String getIden() {
        return samtaleID;
    }

    public String getSamtaleName() {
        return samtaleName;
    }
}
