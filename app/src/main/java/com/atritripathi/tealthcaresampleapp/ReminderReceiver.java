package com.atritripathi.tealthcaresampleapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReminderReceiver extends BroadcastReceiver {
    private static final String TAG = "ReminderReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String messageToDisplay = intent.getStringExtra("msg");
        Log.d(TAG, "onReceive: Msg recieved: " + messageToDisplay);

        // We're creating a new intent that's going to start the MainActivity
        Intent in = new Intent(context, MessageActivity.class);
        // This boolean just makes it easier to check if the Activity has been started from
        // this class
        in.putExtra("lock", true);
        in.putExtra("message", messageToDisplay);
        // You need to add this to your intent if you want to start an Activity fromm a class
        // that is not an Activity itself
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Now we just start the Activity
        context.startActivity(in);
    }
}
