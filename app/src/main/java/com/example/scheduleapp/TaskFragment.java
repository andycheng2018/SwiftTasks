package com.example.scheduleapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class TaskFragment extends Fragment {
    private static final String ARG_TASK_ID = "task_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_DATE2 = "DialogDate2";
    private static final String DIALOG_TIME = "DialogTime";
    private Task task;
    private EditText assignmentName;
    private Button startDateButton;
    private Button dueDateButton;
    private Button dueTimeButton;
    private EditText timeNeeded;
    private CheckBox isCompleted;

    public static TaskFragment newInstance(UUID taskID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskID);
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID taskId = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        task = TaskLab.get(getActivity()).getTask(taskId);
    }

    @Override
    public void onPause() {
        super.onPause();

        TaskLab.get(getActivity()).updateTask(task);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);

        assignmentName = v.findViewById(R.id.task_title);
        assignmentName.setText(task.getAssignmentName());
        assignmentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.setAssignmentName(s.toString());
                task.setTaskChanged(true);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        startDateButton = v.findViewById(R.id.task_startDate);
        updateStartDate();
        startDateButton.setOnClickListener(view -> {
            DatePickerFragment dialog = DatePickerFragment.newInstance(task.getStartDate(), DIALOG_DATE);
            dialog.show(getChildFragmentManager(), DIALOG_DATE);
        });

        dueDateButton = v.findViewById(R.id.task_dueDate);
        updateDueDate();
        dueDateButton.setOnClickListener(view -> {
            DatePickerFragment dialog = DatePickerFragment.newInstance(task.getDueDate(), DIALOG_DATE2);
            dialog.show(getChildFragmentManager(), DIALOG_DATE2);
        });

        dueTimeButton = v.findViewById(R.id.task_dueTime);
        updateDueTime();
        dueTimeButton.setOnClickListener(view -> {
            TimePickerFragment dialog = TimePickerFragment.newInstance(task.getDueDate(), DIALOG_TIME);
            dialog.show(getChildFragmentManager(), DIALOG_TIME);
        });

        timeNeeded = v.findViewById(R.id.task_timeNeeded);
        if(task.getTimeNeeded() != 0)
            timeNeeded.setText(Integer.toString(task.getTimeNeeded()));
        timeNeeded.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(""))
                    task.setTimeNeeded(0);
                else
                    task.setTimeNeeded(Integer.parseInt(s.toString()));
                task.setTaskChanged(true);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        isCompleted = v.findViewById(R.id.is_completed);
        isCompleted.setChecked(task.isCompleted());
        isCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> task.setCompleted(isChecked));

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupMenu();

        getParentFragmentManager().setFragmentResultListener(DIALOG_DATE, this, (requestKey, result) -> {
            if (DIALOG_DATE.equals(requestKey)) {
                Calendar date = DatePickerFragment.getSelectedDate(result);
                task.setStartDate(date);
                updateStartDate();
                task.setTaskChanged(true);
            }
        });

        getParentFragmentManager().setFragmentResultListener(DIALOG_DATE2, this, (requestKey, result) -> {
            if (DIALOG_DATE2.equals(requestKey)) {
                Calendar date = DatePickerFragment.getSelectedDate(result);
                task.setDueDate(date);
                updateDueDate();
                task.setTaskChanged(true);
            }
        });

        getParentFragmentManager().setFragmentResultListener(DIALOG_TIME, this, (requestKey, result) -> {
            if (DIALOG_TIME.equals(requestKey)) {
                Calendar selectedTime = TimePickerFragment.getSelectedTime(result);
                if (selectedTime != null) {
                    Calendar date = task.getDueDate();
                    date.set(Calendar.HOUR_OF_DAY, selectedTime.get(Calendar.HOUR_OF_DAY));
                    date.set(Calendar.MINUTE, selectedTime.get(Calendar.MINUTE));
                    task.setDueDate(date);
                    updateDueTime();
                    task.setTaskChanged(true);
                }
            }
        });
    }

    private void setupMenu() {
        requireActivity().addMenuProvider(new androidx.core.view.MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.fragment_task, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.delete_task) {
                    deleteCurrentTask();
                    return true;
                }
                if (menuItem.getItemId() == R.id.new_task) {
                    addNewTask();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void deleteCurrentTask() {
        TaskLab.get(getActivity()).deleteTask(task.getId());
        requireActivity().finish();
    }

    private void addNewTask() {
        Task newTask = new Task();
        TaskLab.get(getActivity()).addTask(newTask);
    }

    private void updateStartDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault());
        startDateButton.setText(dateFormat.format(task.getStartDate().getTime()));
    }

    private void updateDueDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault());
        dueDateButton.setText(dateFormat.format(task.getDueDate().getTime()));
    }

    private void updateDueTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        dueTimeButton.setText(timeFormat.format(task.getDueDate().getTime()));
    }
}
