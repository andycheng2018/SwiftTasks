package com.example.scheduleapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class TaskLab {
    private static TaskLab sTaskLab;
    private List<Task> mTasks;

    private TaskLab(Context context) {
        mTasks = new ArrayList<>();
    }

    public static synchronized TaskLab get(Context context) {
        if (sTaskLab == null) {
            sTaskLab = new TaskLab(context.getApplicationContext());
        }
        return sTaskLab;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(mTasks);
    }

    public void addTask(Task task) {
        mTasks.add(task);
    }

    public void removeTask(Task task) {
        mTasks.remove(task);
    }

    public Task getTask(int index) {
        if (index >= 0 && index < mTasks.size()) {
            return mTasks.get(index);
        }
        return null;
    }
}
