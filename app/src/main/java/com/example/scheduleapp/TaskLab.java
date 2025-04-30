package com.example.scheduleapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.scheduleapp.TaskDbSchema.TaskBaseHelper;
import com.example.scheduleapp.TaskDbSchema.TaskCursorWriter;
import com.example.scheduleapp.TaskDbSchema.TaskDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskLab {
    private static TaskLab sTaskLab;

    private static Context mContext;
    private static SQLiteDatabase mDatabase;

    public static TaskLab get(Context context) {
        if (sTaskLab == null) {
            sTaskLab = new TaskLab(context);
        }
        return sTaskLab;
    }

    private TaskLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TaskBaseHelper(mContext).getWritableDatabase();
    }

    public void addTask(Task c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(TaskDbSchema.TaskTable.NAME, null, values);
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        TaskCursorWriter cursor = queryTasks(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }

    public Task getTask(UUID id) {
        TaskCursorWriter cursor = queryTasks(
                TaskDbSchema.TaskTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTask();
        } finally {
            cursor.close();
        }
    }

    public void updateTask(Task task) {
        String uuidString = task.getId().toString();
        ContentValues values = getContentValues(task);

        mDatabase.update(TaskDbSchema.TaskTable.NAME, values, TaskDbSchema.TaskTable.Cols.UUID + " = ?",new String[] { uuidString });
    }

    private TaskCursorWriter queryTasks(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TaskDbSchema.TaskTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new TaskCursorWriter(cursor);
    }

    private static ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskDbSchema.TaskTable.Cols.UUID, task.getId().toString());
        values.put(TaskDbSchema.TaskTable.Cols.ASSIGNMENT_NAME, task.getAssignmentName());
        values.put(TaskDbSchema.TaskTable.Cols.START_DATE, task.getStartDate().getTimeInMillis());
        values.put(TaskDbSchema.TaskTable.Cols.DUE_DATE, task.getDueDate().getTimeInMillis());
        values.put(TaskDbSchema.TaskTable.Cols.TIME_NEEDED, task.getTimeNeeded());
        values.put(TaskDbSchema.TaskTable.Cols.IS_COMPLETED, task.isCompleted() ? 1 : 0);
        values.put(TaskDbSchema.TaskTable.Cols.IS_ALTERNATING, task.isAlternating() ? 1 : 0);


        return values;
    }

    public void deleteTask(UUID taskId) {
        String uuidString = taskId.toString();

        mDatabase.delete(TaskDbSchema.TaskTable.NAME, TaskDbSchema.TaskTable.Cols.UUID + " = ?", new String[] { uuidString }
        );
    }
}
