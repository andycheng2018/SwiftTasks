package com.example.scheduleapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;
import java.util.UUID;

public class TaskPagerActivity extends AppCompatActivity {
    private static final String EXTRA_TASK_ID = "com.bignerdranch.android.scheduleApp.crime_id";

    private List<Task> mTasks;
    private ViewPager2 mViewPager;
    private FragmentStateAdapter mPagerAdapter;
    private Button mFirstButton;
    private Button mLastButton;

    public static Intent newIntent(Context packageContext, UUID taskId) {
        Intent intent = new Intent(packageContext, TaskPagerActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }

    public static Intent newCalendarIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, CalendarActivity.class);
        return intent;
    }

    public static Intent newTaskActivityIntent(Context packageContext, UUID taskId) {
        Intent intent = new Intent(packageContext, NewTaskActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);

        mViewPager = findViewById(R.id.task_view_pager);
        mFirstButton = findViewById(R.id.first_button);
        mLastButton = findViewById(R.id.last_button);

        mTasks = TaskLab.get(this).getTasks();
        mPagerAdapter = new CrimePagerAdapter(this);
        mViewPager.setAdapter(mPagerAdapter);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_TASK_ID);
        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0, true);
            }
        });

        mLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mTasks.size() - 1, true);
            }
        });

        updateButtons();

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateButtons();
                super.onPageSelected(position);
            }
        });
    }
    private void updateButtons() {
        if (mViewPager.getCurrentItem() == 0) {
            mFirstButton.setEnabled(false);
        } else {
            mFirstButton.setEnabled(true);
        }

        if (mViewPager.getCurrentItem() == mTasks.size() - 1) {
            mLastButton.setEnabled(false);
        } else {
            mLastButton.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }

    private class CrimePagerAdapter extends FragmentStateAdapter {
        public CrimePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            Task task = mTasks.get(position);
            return TaskFragment.newInstance(task.getId());
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }
}