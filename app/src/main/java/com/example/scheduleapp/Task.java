package com.example.scheduleapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

public class Task {
    private UUID mId;
    private String assignmentName = "New Task";
    private Calendar startDate;
    private Calendar dueDate;
    private int timeNeeded;
    private boolean isCompleted;
    private boolean isAlternating;
    private boolean taskChanged;

    private long remainingTimeMillis;
    private boolean timerRunning;

    public long getRemainingTimeMillis() { return remainingTimeMillis; }
    public void setRemainingTimeMillis(long remainingTimeMillis) { this.remainingTimeMillis = remainingTimeMillis; }
    public boolean isTimerRunning() { return timerRunning; }
    public void setTimerRunning(boolean timerRunning) { this.timerRunning = timerRunning; }

    public Task() {
        this(UUID.randomUUID());
    }

    public Task(UUID id) {
        mId = id;
        startDate = (GregorianCalendar) Calendar.getInstance();
        dueDate = (GregorianCalendar) Calendar.getInstance();
    }

    public UUID getId() {
        return mId;
    }

    public String getStartTimeString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String finalStartDate = "Start Date: " + dateFormat.format(getStartDate().getTime()) + " Start Time: " + timeFormat.format(getStartDate().getTime());
        return finalStartDate;
    }
    public String getDueTimeString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String finalDueDate = "Due Date: " + dateFormat.format(getDueDate().getTime()) + " Due Time: " + timeFormat.format(getDueDate().getTime());
        return finalDueDate;
    }

    public String getAssignmentName() { return assignmentName; }
    public void setAssignmentName(String assignmentName) { this.assignmentName = assignmentName; }

    public Calendar getStartDate() {
        return startDate;
    }
    public void setStartDate(Calendar startDate) {
        this.startDate.set(Calendar.YEAR, startDate.get(Calendar.YEAR));
        this.startDate.set(Calendar.MONTH, startDate.get(Calendar.MONTH));
        this.startDate.set(Calendar.DAY_OF_MONTH, startDate.get(Calendar.DAY_OF_MONTH));
    }

    public Calendar getDueDate() {
        return dueDate;
    }
    public void setDueDate(Calendar dueDate) {
        this.dueDate.set(Calendar.YEAR, dueDate.get(Calendar.YEAR));
        this.dueDate.set(Calendar.MONTH, dueDate.get(Calendar.MONTH));
        this.dueDate.set(Calendar.DAY_OF_MONTH, dueDate.get(Calendar.DAY_OF_MONTH));
    }

    public void setDueDateTime(Calendar dueDate) {
        this.dueDate.set(Calendar.HOUR, dueDate.get(Calendar.HOUR));
        this.dueDate.set(Calendar.MINUTE, dueDate.get(Calendar.MINUTE));
        this.dueDate.set(Calendar.SECOND, dueDate.get(Calendar.SECOND));
        this.dueDate.set(Calendar.MILLISECOND, dueDate.get(Calendar.MILLISECOND));
        this.dueDate.set(Calendar.AM_PM, dueDate.get(Calendar.AM_PM));
    }

    public int getTimeNeeded() { return timeNeeded; }
    public void setTimeNeeded(int timeNeeded) { this.timeNeeded = timeNeeded; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean isCompleted) { this.isCompleted = isCompleted; }

    public boolean isAlternating() { return isAlternating; }
    public void setAlternating(boolean isAlternating) { this.isAlternating = isAlternating; }

    public void setTaskChanged(boolean taskChanged) {
        this.taskChanged = taskChanged;
    }
}