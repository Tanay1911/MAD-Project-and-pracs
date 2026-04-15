package com.example.my_project;

public class Message {
    private String sender;
    private String content;
    private long timestamp;
    private boolean isSentByMe;

    public Message(String sender, String content, long timestamp, boolean isSentByMe) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.isSentByMe = isSentByMe;
    }

    public String getSender() { return sender; }
    public String getContent() { return content; }
    public long getTimestamp() { return timestamp; }
    public boolean isSentByMe() { return isSentByMe; }
}
