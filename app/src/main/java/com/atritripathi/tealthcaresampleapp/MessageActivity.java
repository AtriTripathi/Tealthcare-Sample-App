package com.atritripathi.tealthcaresampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        TextView reminderMessage = findViewById(R.id.tv_rem_msg);
        Button dismissButton = findViewById(R.id.button_dismiss);

        if (getIntent().getBooleanExtra("lock", false)) {
            // These next few lines of code open a window with the MainActivity
            // evan if the device is locked
            Window win = this.getWindow();
            win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            win.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

            String msg = getIntent().getStringExtra("message");
            reminderMessage.setText(msg);

            dismissButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Used this to remove all the activities from the backstack, can also use just finish()
                    finishAffinity();
                }
            });

        }
    }
}
