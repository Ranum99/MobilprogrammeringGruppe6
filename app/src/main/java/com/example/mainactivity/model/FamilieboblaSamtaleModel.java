package com.example.mainactivity.model;

public class FamilieboblaSamtaleModel {
    private int messageId, userFromId, conversationId;
    private String message;

    public FamilieboblaSamtaleModel(int messageId, int navn, int conversationId, String message) {
        this.messageId = messageId;
        this.userFromId = navn;
        this.conversationId = conversationId;
        this.message = message;
    }

    public int getUserFromId() {
        return userFromId;
    }

    public String getMessage() {
        return message;
    }
}
