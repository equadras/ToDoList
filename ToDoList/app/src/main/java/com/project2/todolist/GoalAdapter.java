package com.project2.todolist;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project2.todolist.R;
import com.project2.todolist.models.Goal;
import java.util.List;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

    private final List<Goal> goals;
    private final OnGoalActionListener listener;

    public interface OnGoalActionListener {
        void onEditGoal(Goal goal);
        void onDeleteGoal(Goal goal);
        void onToggleComplete(Goal goal, boolean completed); // Adicione este método
    }

    public GoalAdapter(List<Goal> goals, OnGoalActionListener listener) {
        this.goals = goals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goal, parent, false);
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal goal = goals.get(position);

        holder.cbCompleted.setChecked(goal.isCompleted());
        holder.tvTitle.setText(goal.getTitle());

        if (goal.isCompleted()) {
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.cbCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) { // Evita loops
                    goal.setCompleted(isChecked);
                    listener.onToggleComplete(goal, isChecked); // Atualiza o backend e move entre as listas
                }
            }
        });

        

        // Modo de edição
        if (goal.isEditing()) {
            holder.tvTitle.setVisibility(View.GONE);
            holder.etTitle.setVisibility(View.VISIBLE);
            holder.etTitle.setText(goal.getTitle());
            holder.btnSave.setVisibility(View.VISIBLE);
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        } else {
            if (goal.isCompleted()) {
                holder.btnSave.setVisibility(View.GONE);
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnDelete.setVisibility(View.GONE);
            } else {
                holder.tvTitle.setVisibility(View.VISIBLE);
                holder.etTitle.setVisibility(View.GONE);
                holder.btnSave.setVisibility(View.GONE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnDelete.setVisibility(View.VISIBLE);
            }
        }

        holder.btnEdit.setOnClickListener(v -> {
            goal.setEditing(true);
            notifyItemChanged(position);
        });

        holder.btnSave.setOnClickListener(v -> {
            String updatedTitle = holder.etTitle.getText().toString();
            if (!updatedTitle.isEmpty()) {
                goal.setEditing(false);
                goal.setTitle(updatedTitle);
                listener.onEditGoal(goal);
                notifyItemChanged(position);
            }
        });

        holder.btnDelete.setOnClickListener(v -> listener.onDeleteGoal(goal));
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public void updateGoals(List<Goal> updatedGoals) {
        this.goals.clear();
        this.goals.addAll(updatedGoals);
        notifyDataSetChanged();
    }

    static class GoalViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbCompleted;
        TextView tvTitle;
        EditText etTitle;
        ImageButton btnEdit, btnSave, btnDelete;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            cbCompleted = itemView.findViewById(R.id.cbCompleted);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            etTitle = itemView.findViewById(R.id.etTitle);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnSave = itemView.findViewById(R.id.btnSave);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
