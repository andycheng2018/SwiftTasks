package com.example.scheduleapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class NewTaskActivity extends AppCompatActivity {

    private EditText assignmentName, startDate, dueDate, numberOfDays, timeNeeded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        assignmentName = findViewById(R.id.assignmentName);
        startDate = findViewById(R.id.startDate);
        dueDate = findViewById(R.id.dueDate);
        numberOfDays = findViewById(R.id.numberOfDays);
        timeNeeded = findViewById(R.id.timeNeeded);
    }

    public void saveTask(View view) {
        // Save task logic here
        finish();
    }
}