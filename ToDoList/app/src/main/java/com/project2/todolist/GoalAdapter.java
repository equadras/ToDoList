package com.project2.todolist;

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

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

    private final List<Goal> goals;
    private final OnGoalActionListener listener;

    public interface OnGoalActionListener {
        void onEditGoal(Goal goal);
        void onDeleteGoal(Goal goal);
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

        // Alternar entre exibição normal e modo de edição
        if (goal.isEditing()) {
            holder.tvTitle.setVisibility(View.GONE);
            holder.etTitle.setVisibility(View.VISIBLE);
            holder.etTitle.setText(goal.getTitle());
            holder.btnSave.setVisibility(View.VISIBLE);
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        } else {
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.tvTitle.setText(goal.getTitle());
            holder.etTitle.setVisibility(View.GONE);
            holder.btnSave.setVisibility(View.GONE);
            holder.btnEdit.setVisibility(View.VISIBLE);
        }

        // Ação de editar (ativa o modo de edição)
        holder.btnEdit.setOnClickListener(v -> {
            goal.setEditing(true);
            notifyItemChanged(position);
        });

        // Ação de salvar (confirma o título atualizado)
        holder.btnSave.setOnClickListener(v -> {
            String updatedTitle = holder.etTitle.getText().toString();
            if (!updatedTitle.isEmpty()) {
                goal.setEditing(false);
                goal.setTitle(updatedTitle); // Atualiza localmente
                listener.onEditGoal(goal); // Dispara a requisição de atualização
                notifyItemChanged(position);
            }
        });

        // Ação de deletar
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

        TextView tvTitle;
        EditText etTitle;
        ImageButton btnEdit, btnSave, btnDelete;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            etTitle = itemView.findViewById(R.id.etTitle);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnSave = itemView.findViewById(R.id.btnSave);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
