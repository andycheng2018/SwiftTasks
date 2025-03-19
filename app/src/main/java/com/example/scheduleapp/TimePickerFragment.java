package com.example.scheduleapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "com.bignerdranch.android.scheduleApp.time";
    private static final String ARG_TIME = "time";
    private static final String ARG_REQUEST_CODE = "request_code_time";
    private static final String RESULT_TIME_KEY = "result_time";

    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Calendar time, String requestCode) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TIME, time);
        bundle.putSerializable(ARG_REQUEST_CODE, requestCode);
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setArguments(bundle);
        return timePickerFragment;
    }

    public static Calendar getSelectedTime(Bundle result) {
        Calendar resultTime = (Calendar) result.getSerializable(RESULT_TIME_KEY);
        return resultTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar time = (Calendar) getArguments().getSerializable(ARG_TIME);
        Calendar calendar = Calendar.getInstance();

        View v = LayoutInflater.from(requireActivity()).inflate(R.layout.dialogue_time_picker, null);
        mTimePicker = v.findViewById(R.id.dialog_time_picker);


        return new AlertDialog.Builder(requireActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = mTimePicker.getHour();
                        int minute = mTimePicker.getMinute();

                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);

                        Bundle result = new Bundle();
                        result.putSerializable(RESULT_TIME_KEY, calendar);
                        String resultRequestCode = requireArguments().getString(ARG_REQUEST_CODE, "");
                        requireActivity().getSupportFragmentManager().setFragmentResult(resultRequestCode, result);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }
}

