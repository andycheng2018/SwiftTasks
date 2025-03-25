package com.example.scheduleapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private TextView countdown_text;
    private Button countdown_button;
    private Button reset_button;
    private Button complete_button;
    private CountDownTimer countDownTimer;
    private Long timeLeftInMilliseconds = 10000L;
    private Long timeLeftInMillisecondsStart;
    private boolean timeRunning;
    private Task mTask;

    @SuppressLint("MissingInflatedId")
    @Override
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
                timeLeftInMilliseconds = mTasks.get(i).getTimeNeeded() * 60000L;
                mTask = mTasks.get(i);
                break;
            }
        }

        countdown_text = findViewById(R.id.countdownText);
        countdown_button = findViewById(R.id.startPauseButton);
        reset_button = findViewById(R.id.resetButton);
        complete_button = findViewById(R.id.completeTaskButton);

        // Initially hide the complete button
        complete_button.setVisibility(View.GONE);

        countdown_button.setOnClickListener(view -> {
            if (timeRunning)
                stopTimer();
            else
                startTimer();
        });

        reset_button.setOnClickListener(view -> resetTimer());

        complete_button.setOnClickListener(view -> {
            if (mTask != null) {
                mTask.setCompleted(true);
                TaskLab.get(NewTaskActivity.this).updateTask(mTask);
                finish();
            }
        });

        timeLeftInMillisecondsStart = timeLeftInMilliseconds;
        updateTimer();
    }


    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timeRunning = false;
                countdown_button.setText("Start");

                complete_button.setVisibility(View.VISIBLE);
            }
        }.start();

        countdown_button.setText("Pause");
        timeRunning = true;
    }


    public void stopTimer() {
        countDownTimer.cancel();
        countdown_button.setText("Start");
        timeRunning = false;
    }

    public void updateTimer() {
        int hours = timeLeftInMilliseconds.intValue() / 3600000;
        int minutes = (timeLeftInMilliseconds.intValue() % 3600000) / 60000;
        int seconds = (timeLeftInMilliseconds.intValue() % 60000) / 1000;

        String timeLeftText = "";

        if (hours > 0) {
            timeLeftText += hours + ":";
        }

        if (minutes < 10 && hours > 0) {
            timeLeftText += "0";
        }
        timeLeftText += minutes + ":";

        if (seconds < 10) {
            timeLeftText += "0";
        }
        timeLeftText += seconds;

        countdown_text.setText(timeLeftText);
    }

    public void resetTimer() {
        stopTimer();
        timeLeftInMilliseconds = timeLeftInMillisecondsStart;
        updateTimer();
        complete_button.setVisibility(View.GONE);
    }
}
