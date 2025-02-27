package com.example.scheduleapp;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private ListView taskListView;
    private TextView titleTextView;
    private ImageButton backButton;

    private ArrayList<String> dateList;
    private ArrayList<ArrayList<String>> taskLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        taskListView = findViewById(R.id.taskListView);
        backButton = findViewById(R.id.menu_tasks);
        titleTextView = findViewById(R.id.titleTextView);

        dateList = new ArrayList<>();
        taskLists = new ArrayList<>();

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String dateKey = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            String title = "Tasks for " + getMonthName(month) + " " + dayOfMonth;
            updateTitleAndTasks(title, dateKey);
        });

        backButton.setOnClickListener(v -> {
            finish();
        });

        //THIS IS A PLACEHOLDER FOR THE ACTUAL TASK ASSIGNMENTS
        String startDate = "2025-03-01";
        String dueDate = "2025-03-07";
        String assignmentName = "CS Homework";
        int numDays = 7;
        int timeNeeded = 14;

        loadTasks(assignmentName, startDate, dueDate, numDays, timeNeeded);
    }

    private void loadTasks(String assignmentName, String startDate, String dueDate, int numDays, int timeNeeded) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = sdf.parse(startDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);

            int timePerDay = timeNeeded / numDays;

            for (int i = 0; i < numDays; i++) {
                String date = sdf.format(calendar.getTime());

                ArrayList<String> tasksForDay = new ArrayList<>();
                tasksForDay.add(assignmentName + " - Study for " + timePerDay + " hours");

                dateList.add(date);
                taskLists.add(tasksForDay);

                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void updateTitleAndTasks(String title, String dateKey) {
        titleTextView.setText(title);

        int index = dateList.indexOf(dateKey);
        ArrayList<String> taskList = new ArrayList<>();

        if (index != -1) {
            taskList = taskLists.get(index);
        }

        taskListView.setAdapter(new TaskListAdapter(this, taskList));
    }

    private String getMonthName(int month) {
        if (month == 0) return "January";
        if (month == 1) return "February";
        if (month == 2) return "March";
        if (month == 3) return "April";
        if (month == 4) return "May";
        if (month == 5) return "June";
        if (month == 6) return "July";
        if (month == 7) return "August";
        if (month == 8) return "September";
        if (month == 9) return "October";
        if (month == 10) return "November";
        if (month == 11) return "December";

        return "Invalid month";
    }
}
