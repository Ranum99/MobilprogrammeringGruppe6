package com.example.mainactivity;

import java.util.ArrayList;

public class FamilieboblaSamtaleModel {
    private int id, userFrom;
    private String message;

    public FamilieboblaSamtaleModel(int id, int navn, String message) {
        this.id = id;
        this.userFrom = navn;
        this.message = message;
    }

    public int getUserFrom() {
        return userFrom;
    }

    public int getIden() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public static ArrayList<FamilieboblaSamtaleModel> getData(ArrayList<Message> messages) {
        ArrayList<FamilieboblaSamtaleModel> data = new ArrayList<>();

        for(int i = 0; i < messages.size(); i++) {
            FamilieboblaSamtaleModel enMelding = new FamilieboblaSamtaleModel(messages.get(i).getMessageID(), messages.get(i).getFromID(), messages.get(i).getMessage());
            data.add(enMelding);
        }

        return data;
    }
}
