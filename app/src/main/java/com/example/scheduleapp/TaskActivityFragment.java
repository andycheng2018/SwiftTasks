package com.example.scheduleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class TaskActivityFragment extends Fragment {

    private static final String ARG_TASK_ID = "task_id";

    private TextView taskTitle;
    private TextView taskDescription;
    private int taskId; // Store the task ID

    public static TaskActivityFragment newInstance(int taskId) {
        TaskActivityFragment fragment = new TaskActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskId = getArguments().getInt(ARG_TASK_ID, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_screen, container, false);

        taskTitle = view.findViewById(R.id.tv_task);
        taskDescription = view.findViewById(R.id.tv_task_description);

        // Placeholder values; you can customize based on taskId
        taskTitle.setText("CLASS-TASK " + taskId);
        taskDescription.setText("GET READY TO SPEND X MINUTES ON -- TODAY!");

        return view;
    }
}
