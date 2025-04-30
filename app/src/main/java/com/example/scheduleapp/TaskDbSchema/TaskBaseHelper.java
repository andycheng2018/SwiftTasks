package com.example.scheduleapp.TaskDbSchema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "taskBase.db";

    public TaskBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TaskDbSchema.TaskTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TaskDbSchema.TaskTable.Cols.UUID + ", " +
                TaskDbSchema.TaskTable.Cols.ASSIGNMENT_NAME + ", " +
                TaskDbSchema.TaskTable.Cols.START_DATE + ", " +
                TaskDbSchema.TaskTable.Cols.DUE_DATE + ", " +
                TaskDbSchema.TaskTable.Cols.TIME_NEEDED + ", " +
                TaskDbSchema.TaskTable.Cols.IS_COMPLETED + ", " +
                TaskDbSchema.TaskTable.Cols.IS_ALTERNATING + ")");    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

