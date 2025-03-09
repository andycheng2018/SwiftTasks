package com.example.scheduleapp;

public class Task {


    private String assignmentName;
    private String startDate;
    private String dueDate;
    private int numberOfDays;
    private int timeNeeded;
    private boolean isCompleted;

    public Task(String assignmentName, String startDate, String dueDate, int numberOfDays, int timeNeeded, boolean isCompleted) {
        this.assignmentName = assignmentName;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.numberOfDays = numberOfDays;
        this.timeNeeded = timeNeeded;
        this.isCompleted = isCompleted;
    }


    public String getAssignmentName() { return assignmentName; }
    public void setAssignmentName(String assignmentName) { this.assignmentName = assignmentName; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public int getNumberOfDays() { return numberOfDays; }
    public void setNumberOfDays(int numberOfDays) { this.numberOfDays = numberOfDays; }

    public int getTimeNeeded() { return timeNeeded; }
    public void setTimeNeeded(int timeNeeded) { this.timeNeeded = timeNeeded; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}