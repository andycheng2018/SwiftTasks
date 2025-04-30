package com.example.scheduleapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskListFragment extends Fragment {
    private RecyclerView mTaskRecyclerView;
    private View mEmptyView;
    private Button mAddTaskButton;
    private TaskAdapter mAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        mTaskRecyclerView = view.findViewById(R.id.task_recycler_view);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mEmptyView = view.findViewById(R.id.empty_view);
        mAddTaskButton = view.findViewById(R.id.add_task_button);
        mAddTaskButton.setOnClickListener(v -> addNewTask());

        updateUI();
        setUpMenu();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void updateUI() {
        TaskLab taskLab = TaskLab.get(getActivity());
        List<Task> tasks = taskLab.getTasks();

        if (tasks.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
            mTaskRecyclerView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mTaskRecyclerView.setVisibility(View.VISIBLE);

            if (mAdapter == null) {
                mAdapter = new TaskAdapter(tasks);
                mTaskRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setTasks(tasks);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mStartDateTextView;
        private TextView mDueDateTextView;
        private Button mStartButton;
        private CheckBox mSolvedCheckBox;
        private CheckBox mAlternating;
        private Task mTask;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.task_title_2);
            mStartDateTextView = itemView.findViewById(R.id.task_start_date);
            mDueDateTextView = itemView.findViewById(R.id.task_due_date);
            mStartButton = itemView.findViewById(R.id.start_task);
            mSolvedCheckBox = itemView.findViewById(R.id.is_completed);
            mAlternating = itemView.findViewById(R.id.is_alternating);
        }

        public void bind(Task task) {
            mTask = task;
            mTitleTextView.setText(mTask.getAssignmentName());
            mStartDateTextView.setText(mTask.getStartTimeString());
            mDueDateTextView.setText(mTask.getDueTimeString());
            mStartButton.setOnClickListener(v -> startTask());
            mSolvedCheckBox.setChecked(task.isCompleted());
            if (mAlternating != null) {
                mAlternating.setChecked(task.isAlternating());
            }
            updateStrikeThrough(task.isCompleted());

            updateBackgroundColor(task.isCompleted());

            mSolvedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                mTask.setCompleted(isChecked);
                updateStrikeThrough(isChecked);
                updateBackgroundColor(isChecked);
            });

            mAlternating.setOnCheckedChangeListener((buttonView, isChecked) -> {
                mTask.setAlternating(isChecked);
            });


        }

        private void updateBackgroundColor(boolean isChecked) {
            ConstraintLayout layout = itemView.findViewById(R.id.linearLayout);
            if (isChecked) {
                layout.setBackgroundColor(Color.parseColor("#77C7C1"));
            } else {
                layout.setBackgroundColor(Color.parseColor("#FFAAAA"));
            }
        }

        private void updateStrikeThrough(boolean isCompleted) {
            int flags = isCompleted ? android.graphics.Paint.STRIKE_THRU_TEXT_FLAG : 0;
            mTitleTextView.setPaintFlags(flags);
            mStartDateTextView.setPaintFlags(flags);
            mDueDateTextView.setPaintFlags(flags);
            mDueDateTextView.setPaintFlags(flags);
        }

        @Override
        public void onClick(View view) {
            Intent intent = TaskPagerActivity.newIntent(getActivity(), mTask.getId());
            startActivity(intent);
        }

        private void startTask() {
            Intent intent = TaskPagerActivity.newTaskActivityIntent(getActivity(), mTask.getId());
            startActivity(intent);
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bind(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

    private void addNewTask() {
        Task newTask = new Task();
        TaskLab.get(requireActivity()).addTask(newTask);
        Intent intent = TaskPagerActivity.newIntent(requireActivity(), newTask.getId());
        startActivity(intent);
    }

    private void openCalendar() {
        Intent intent = TaskPagerActivity.newCalendarIntent(requireActivity());
        startActivity(intent);
    }

    private void setUpMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.fragment_task_list, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.new_task) {
                    addNewTask();
                    return true;
                }
                if (menuItem.getItemId() == R.id.menu_calendar) {
                    openCalendar();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}
