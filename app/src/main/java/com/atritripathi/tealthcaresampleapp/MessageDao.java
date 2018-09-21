package com.atritripathi.tealthcaresampleapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM chat_table")
    LiveData<List<Message>> getAllMessage();

    // This and many other such queries can also be implemented like this.
    @Query("SELECT * FROM chat_table WHERE id == :position")
    Message getMessage(int position);

    @Insert
    void insertMessage(Message message);

}
