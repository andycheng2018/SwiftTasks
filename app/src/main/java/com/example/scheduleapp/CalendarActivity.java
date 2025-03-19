package com.example.scheduleapp;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private ListView taskListView;
    private TextView titleTextView;
    private List<Task> mTasks;

    private ArrayList<String> dateList;
    private ArrayList<ArrayList<String>> taskLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        taskListView = findViewById(R.id.taskListView);
        titleTextView = findViewById(R.id.titleTextView);

        dateList = new ArrayList<>();
        taskLists = new ArrayList<>();

        mTasks = TaskLab.get(this).getTasks();
        loadTasks();

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String dateKey = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            String title = "Tasks for " + getMonthName(month) + " " + dayOfMonth;
            updateTitleAndTasks(title, dateKey);
        });
    }

    private void loadTasks() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        for (Task task : mTasks) {
            Calendar startDate = task.getStartDate();
            Calendar dueDate = task.getDueDate();
            Calendar tempDate = (Calendar) startDate.clone();

            while (!tempDate.after(dueDate)) {
                String date = sdf.format(tempDate.getTime());
                ArrayList<String> tasksForDay = new ArrayList<>();
                tasksForDay.add(task.getAssignmentName() + " - Study for " + task.getTimeNeeded() + " hours");

                int index = dateList.indexOf(date);
                if (index != -1) {
                    taskLists.get(index).addAll(tasksForDay);
                } else {
                    dateList.add(date);
                    taskLists.add(tasksForDay);
                }

                tempDate.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
    }

    private void updateTitleAndTasks(String title, String dateKey) {
        titleTextView.setText(title);

        int index = dateList.indexOf(dateKey);
        ArrayList<String> taskList = new ArrayList<>();

        if (index != -1) {
            taskList = taskLists.get(index);
        }
        taskListView.setAdapter(new CalendarListAdapter(this, taskList));
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