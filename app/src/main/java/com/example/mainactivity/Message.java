package com.example.mainactivity;

public class Message {
    private int messageID, fromID, conversationID;
    private String message;

    public Message(int messageID, int fromID, int conversationID, String message) {
        this.messageID = messageID;
        this.fromID = fromID;
        this.conversationID = conversationID;
        this.message = message;
    }

    public int getMessageID() {
        return messageID;
    }

    public int getFromID() {
        return fromID;
    }

    public int getConversationID() {
        return conversationID;
    }

    public String getMessage() {
        return message;
    }
}
