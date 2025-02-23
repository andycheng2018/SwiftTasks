package com.example.scheduleapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<String> taskList;
    private MainActivity mainActivity;

    public TaskAdapter(List<String> taskList, MainActivity mainActivity) {
        this.taskList = taskList;
        this.mainActivity = mainActivity;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        String task = taskList.get(position);
        holder.taskText.setText(task);

        holder.deleteButton.setOnClickListener(v -> {
            mainActivity.removeTask(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        ImageButton deleteButton;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
