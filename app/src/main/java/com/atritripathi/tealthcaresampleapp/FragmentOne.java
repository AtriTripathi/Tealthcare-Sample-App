package com.atritripathi.tealthcaresampleapp;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class FragmentOne extends Fragment {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private EditText message;
    private EditText reminderTime;
    private Calendar mCurrentTime;
    private Calendar mReminderTime = Calendar.getInstance();
    private Button setReminderButton;

    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reminderTime = view.findViewById(R.id.et_reminder_time);
        setReminderButton = view.findViewById(R.id.button_set_reminder);
        message = view.findViewById(R.id.et_msg);

        reminderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        reminderTime.setText(selectedHour + ":" + selectedMinute);

                        mReminderTime.set(mCurrentTime.get(Calendar.YEAR), mCurrentTime.get(Calendar.MONTH),
                                mCurrentTime.get(Calendar.DATE), selectedHour, selectedMinute, mCurrentTime.get(Calendar.SECOND));
                    }
                }, hour, minute, true); // For 24 hour time format
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        setReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!message.toString().equals("") && !reminderTime.toString().equals("")) {
                    Toast.makeText(getContext(),"Message and Time cannot be empty",Toast.LENGTH_SHORT).show();
                } else {
                    alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getContext(), ReminderReceiver.class);
                    intent.putExtra("msg", message.getText().toString().trim());

                    pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);

                    setAlarm(mReminderTime);
                }
            }
        });
    }

    public void setAlarm (Calendar cal) {
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
}


