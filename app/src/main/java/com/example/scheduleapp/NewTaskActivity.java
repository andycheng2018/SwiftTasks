package com.example.scheduleapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;
import java.util.UUID;

public class NewTaskActivity extends AppCompatActivity {
    private static final String EXTRA_TASK_ID = "com.bignerdranch.android.scheduleApp.crime_id";
    private List<Task> mTasks;
    private TextView taskTitle;
    private TextView taskDescription;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_task_screen);

        taskTitle = findViewById(R.id.tv_task_title);
        taskDescription = findViewById(R.id.tv_task_description);

        mTasks = TaskLab.get(this).getTasks();

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_TASK_ID);
        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getId().equals(crimeId)) {
                taskTitle.setText(mTasks.get(i).getAssignmentName());
                taskDescription.setText("Get ready to spend " + mTasks.get(i).getTimeNeeded() + " mins today!");
                break;
            }
        }
    }
}
