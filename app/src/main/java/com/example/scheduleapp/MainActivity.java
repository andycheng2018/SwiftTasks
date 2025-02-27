package com.example.scheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private List<String> taskList;

    private TextView noTasksMessage;

    private ImageButton addTaskButton;
    private ImageButton menuTasksButton;
    private ImageButton menuAddButton;
    private ImageButton menuCalendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);

        noTasksMessage = findViewById(R.id.noTasksMessage);

        addTaskButton = findViewById(R.id.addTaskButton);
        menuTasksButton = findViewById(R.id.menu_tasks);
        menuAddButton = findViewById(R.id.menu_add);
        menuCalendarButton = findViewById(R.id.menu_calendar);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, this);

        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);

        addTaskButton.setOnClickListener(v -> showAddTask());

        menuTasksButton.setOnClickListener(v -> navigateToHomeScreen());
        menuAddButton.setOnClickListener(v -> showAddTask());
        menuCalendarButton.setOnClickListener(v -> openCalendar());

        updateNoTasksMessage();
    }

    private void showAddTask() {
        addNewTask("New Task");
    }

    private void addNewTask(String taskName) {
        taskList.add(taskName);
        taskAdapter.notifyItemInserted(taskList.size() - 1);
        updateNoTasksMessage();
    }

    public void removeTask(int position) {
        if (position >= 0 && position < taskList.size()) {
            taskList.remove(position);
            taskAdapter.notifyItemRemoved(position);
            updateNoTasksMessage();
        }
    }

    private void updateNoTasksMessage() {
        if (taskList.isEmpty()) {
            noTasksMessage.setVisibility(View.VISIBLE);
        } else {
            noTasksMessage.setVisibility(View.GONE);
        }
    }
    private void navigateToHomeScreen() {
        // Nothing, home page
    }

    private void openCalendar() {
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }
}
