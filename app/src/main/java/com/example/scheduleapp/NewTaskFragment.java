package com.example.scheduleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class NewTaskFragment extends Fragment {

    private EditText assignmentName, startDate, dueDate, numberOfDays, timeNeeded;
    private Button saveButton;
    private static final String ARG_SCHEDULE_ID = "schedule_id";

    public static NewTaskFragment newInstance(UUID scheduleID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SCHEDULE_ID, scheduleID);
        NewTaskFragment fragment = new NewTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_task, container, false);

        assignmentName = view.findViewById(R.id.assignmentName);
        startDate = view.findViewById(R.id.startDate);
        dueDate = view.findViewById(R.id.dueDate);
        numberOfDays = view.findViewById(R.id.numberOfDays);
        timeNeeded = view.findViewById(R.id.timeNeeded);
        saveButton = view.findViewById(R.id.saveTaskButton);

        saveButton.setOnClickListener(v -> saveTask());

        return view;
    }

    private void saveTask() {
        String name = assignmentName.getText().toString();
        String start = startDate.getText().toString();
        String due = dueDate.getText().toString();
        int days = Integer.parseInt(numberOfDays.getText().toString());
        int time = Integer.parseInt(timeNeeded.getText().toString());

        Task task = new Task(name, start, due, days, time, false);
        TaskLab.get(requireActivity()).addTask(task);

        requireActivity().finish();
    }
}
