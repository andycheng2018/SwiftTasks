package com.example.scheduleapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class NewTaskActivity extends SingleFragmentActivity {
    private static final String EXTRA_SCHEDULE_ID = "com.bignerdranch.android.scheduleApp";
    public static Intent newIntent(Context packageContext, UUID scheduleId) {
        Intent intent = new Intent(packageContext, NewTaskFragment.class);
        intent.putExtra(EXTRA_SCHEDULE_ID, scheduleId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID scheduleId = (UUID) getIntent().getSerializableExtra(EXTRA_SCHEDULE_ID);
        return NewTaskFragment.newInstance(scheduleId);
    }
}