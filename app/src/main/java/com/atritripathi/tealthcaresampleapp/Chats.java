package com.atritripathi.tealthcaresampleapp;

import android.support.annotation.Nullable;

public class Chats {

    private String messageText;
    private String status;
    private String time;
    private boolean isSentMessage;

    public Chats(String messageText, @Nullable String status, String time, boolean isSentMessage) {
        this.messageText = messageText;
        this.status = status;
        this.time = time;
        this.isSentMessage = isSentMessage;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getIsSentMessage() {
        return isSentMessage;
    }

    public void setIsSentMessage(boolean sentMessage) {
        isSentMessage = sentMessage;
    }
}
