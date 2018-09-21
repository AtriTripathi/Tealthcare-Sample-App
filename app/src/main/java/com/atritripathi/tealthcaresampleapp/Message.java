package com.atritripathi.tealthcaresampleapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "chat_table")
public class Message {

    // Columns in the table
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "message_text")
    private String messageText;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "is_sent_message")
    private boolean isSentMessage;


    public Message(int id, String messageText, String time, String status, boolean isSentMessage) {
        this.id = id;
        this.messageText = messageText;
        this.time = time;
        this.status = status;
        this.isSentMessage = isSentMessage;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSentMessage() {
        return isSentMessage;
    }

    public void setSentMessage(boolean sentMessage) {
        isSentMessage = sentMessage;
    }
}
