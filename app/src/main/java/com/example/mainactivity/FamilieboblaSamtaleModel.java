package com.example.mainactivity;

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

    public int getIden() {
        return messageId;
    }

    public int getConversationId() {
        return conversationId;
    }

    public String getMessage() {
        return message;
    }
}
