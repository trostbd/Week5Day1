package com.example.lawre.week5day1.model;

public class Message
{
    String username, time, text, key;

    public Message()
    {
    }

    public Message(String username, String time, String text) {
        this.username = username;
        this.time = time;
        this.text = text;
    }

    public Message(String username, String time, String text, String key) {
        this.username = username;
        this.time = time;
        this.text = text;
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
