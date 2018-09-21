package com.atritripathi.tealthcaresampleapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class MessageViewModel extends AndroidViewModel {
    private MessageRepository mMessageRepository;
    private LiveData<List<Message>> mAllMessages;

    public MessageViewModel(Application application) {
        super(application);

        mMessageRepository = new MessageRepository(application);
        mAllMessages = mMessageRepository.getAllMessages();
    }

    LiveData<List<Message>> getAllMessages() {
        return mAllMessages;
    }

    public void insert(Message message) {
        mMessageRepository.insert(message);
    }
}
