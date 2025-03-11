package com.example.scheduleapp;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class TaskScreenActivity extends SingleFragmentActivity {

    private static final String EXTRA_TASK_ID = "com.example.scheduleapp.task_id";

    public static Intent newIntent(Context context, int taskId) {
        Intent intent = new Intent(context, TaskScreenActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int taskId = getIntent().getIntExtra(EXTRA_TASK_ID, -1);
        return TaskActivityFragment.newInstance(taskId);
    }
}
