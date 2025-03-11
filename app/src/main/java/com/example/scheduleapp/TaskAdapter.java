package com.example.scheduleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks;
    private Context context;

    public TaskAdapter(List<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskText.setText(task.getAssignmentName());

        // Start Button
        holder.startButton.setOnClickListener(v -> {
            // Trigger the fragment to start a new task
            if (context instanceof AppCompatActivity) {
                AppCompatActivity activity = (AppCompatActivity) context;
                NewTaskFragment newTaskFragment = new NewTaskFragment();

                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, newTaskFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Delete Button
        holder.deleteButton.setOnClickListener(v -> {
            tasks.remove(position);
            notifyItemRemoved(position);
            if (tasks.isEmpty()) {
                Toast.makeText(context, "No tasks remaining", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        ImageButton startButton, deleteButton;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
            startButton = itemView.findViewById(R.id.startButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
