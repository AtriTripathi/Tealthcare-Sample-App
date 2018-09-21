package com.atritripathi.tealthcaresampleapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FragmentThree extends Fragment {

    private static ArrayList<Chats> messagesList;
    private static MessageViewModel mMessageViewModel;
    private static Button addDummyChatButton;
    private static TextView statusTextView;

    public FragmentThree() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_three, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addDummyChatButton = view.findViewById(R.id.button_add_dummy_chat);


        // This is used to easily get access to all the views of "message_layout".
        LayoutInflater messageLayout = getLayoutInflater();
        View messageView = messageLayout.inflate(R.layout.message_layout,null);
        statusTextView = messageView.findViewById(R.id.tv_status);


        final MessageAdapter mMessageAdapter = new MessageAdapter(getContext());

        RecyclerView mRecyclerView = view.findViewById(R.id.rv_messages);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMessageAdapter);


        /**
         * To get a new or existing ViewModel from the ViewModelProvider.
         */
        mMessageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);


        /**
         * Add an observer on the LiveData. The onChanged() method fires when the observed data
         * changes and the activity is in the foreground.
         */
        mMessageViewModel.getAllMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable List<Message> messages) {
                mMessageAdapter.setMessages(messages);
            }
        });


        /**
         * This is a list of sample dummy data, with message text, status of the message, time of message,
         * and a boolean value to know whether the data was sent or recieved.
         */
        messagesList = new ArrayList<>();
        messagesList.add(new Chats("Hey Atri!", null, "8:30 AM",false));
        messagesList.add(new Chats("Hey Doc", "Read", "9:40 AM",true));
        messagesList.add(new Chats("What's up?!", "Read", "9:55 AM",true));
        messagesList.add(new Chats("Yeah, just wanted to check on you.", null, "12:30 PM",false));
        messagesList.add(new Chats("How is your health?", null, "1:25 PM",false));
        messagesList.add(new Chats("It's better now. The cold is gone.", "Read", "2:00 PM",true));
        messagesList.add(new Chats("But, I still have some headache.", "Delivered", "3:30 PM",true));
        messagesList.add(new Chats("Okay then, let's change your medication.", null, "4:00 PM",false));
        messagesList.add(new Chats("And most importantly, take rest. It is the best healer, ever known.", null, "4:30 PM",false));
        messagesList.add(new Chats("Sure Doc. And thank you for your precious time. I'll kee you posted.", "Sent", "7:30 PM",true));
        messagesList.add(new Chats("Your welcome, Atri. Always pleased to serve you.", null, "8:15 PM",false));


        // Handling the DB operations on a background thread, i.e. AsyncTask
        new PopulateChatTask().execute();

    }


    private static class PopulateChatTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            addDummyChatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (Chats chat: messagesList) {

                        if(chat.getIsSentMessage()) {
                            statusTextView.setText(chat.getStatus());           // Update the TextView with "Status" for sent messages.
                        } else {
                            statusTextView.setText(chat.getTime());             // Update the same TextView with "Time" for the received messages.
                        }

                        Message message = new Message(chat.getMessageText(), chat.getStatus(), chat.getTime(), chat.getIsSentMessage());
                        mMessageViewModel.insert(message);
                    }
                }
            });
            return null;
        }


        /**
         * The below function can be used to update the activity with the "Status" values, as
         * the onPostExecute runs on the UI thread. But, I was unable to get it running, for now.
         * As I think the issue might be because we are trying to access the UI thread from
         * a Fragment, which itself is being handled separately. We can look into this issue.
         */
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            statusTextView.setText("Done");
//        }


    }
}
