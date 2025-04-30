package com.example.scheduleapp.TaskDbSchema;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.scheduleapp.Task;

import java.util.GregorianCalendar;
import java.util.UUID;

public class TaskCursorWriter extends CursorWrapper {
    public TaskCursorWriter(Cursor cursor) {
        super(cursor);
    }

    public Task getTask() {
        String uuidString = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.UUID));
        String assignmentName = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.ASSIGNMENT_NAME));
        long startDate = getLong(getColumnIndex(TaskDbSchema.TaskTable.Cols.START_DATE));
        long dueDate = getLong(getColumnIndex(TaskDbSchema.TaskTable.Cols.DUE_DATE));
        int timeNeeded = getInt(getColumnIndex(TaskDbSchema.TaskTable.Cols.TIME_NEEDED));
        int isCompleted= getInt(getColumnIndex(TaskDbSchema.TaskTable.Cols.IS_COMPLETED));
        int isAlternating= getInt(getColumnIndex(TaskDbSchema.TaskTable.Cols.IS_ALTERNATING));

        Task task = new Task(UUID.fromString(uuidString));
        task.setAssignmentName(assignmentName);
        task.setTimeNeeded(timeNeeded);
        task.setCompleted(isCompleted != 0);
        task.setAlternating(isAlternating != 0);

        GregorianCalendar startCalendar = new GregorianCalendar();
        startCalendar.setTimeInMillis(startDate);
        task.setStartDate(startCalendar);

        GregorianCalendar dueCalendar = new GregorianCalendar();
        dueCalendar.setTimeInMillis(dueDate);
        task.setDueDate(dueCalendar);
        task.setDueDateTime(dueCalendar);

        return task;
    }
}

