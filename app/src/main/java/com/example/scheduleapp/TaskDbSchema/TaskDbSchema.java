package com.example.scheduleapp.TaskDbSchema;

public class TaskDbSchema {
    public static final class TaskTable {
        public static final String NAME = "tasks";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String ASSIGNMENT_NAME = "assignmentName";
            public static final String START_DATE = "startDate";
            public static final String DUE_DATE = "dueDate";
            public static final String TIME_NEEDED = "timeNeeded";
            public static final String IS_COMPLETED = "isCompleted";
            public static final String IS_ALTERNATING = "isAlternating";
        }
    }
}