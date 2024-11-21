package com.project2.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.todolist.models.Goal;
import com.project2.todolist.network.GoalApi;
import com.project2.todolist.network.RetrofitClient;



import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etGoal;
    private GoalAdapter incompleteAdapter, completedAdapter;
    private GoalApi goalApi;
    private RecyclerView rvIncompleteGoals, rvCompletedGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Conectando os elementos do layout
        etGoal = findViewById(R.id.etGoal);
        rvIncompleteGoals = findViewById(R.id.rvIncompleteGoals);
        rvCompletedGoals = findViewById(R.id.rvCompletedGoals);

        // Inicializar Retrofit
        goalApi = RetrofitClient.getInstance().create(GoalApi.class);

        // Configurar os adaptadores para as listas
        incompleteAdapter = new GoalAdapter(new ArrayList<>(), new GoalAdapter.OnGoalActionListener() {
            @Override
            public void onEditGoal(Goal goal) {
                editGoal(goal);
            }

            @Override
            public void onDeleteGoal(Goal goal) {
                deleteGoal(goal);
            }

            @Override
            public void onToggleComplete(Goal goal, boolean completed) {
                toggleGoalCompletion(goal, completed);
            }
        });

        completedAdapter = new GoalAdapter(new ArrayList<>(), new GoalAdapter.OnGoalActionListener() {
            @Override
            public void onEditGoal(Goal goal) {
                editGoal(goal);
            }

            @Override
            public void onDeleteGoal(Goal goal) {
                deleteGoal(goal);
            }

            @Override
            public void onToggleComplete(Goal goal, boolean completed) {
                toggleGoalCompletion(goal, completed);
            }
        });

        // Configurar RecyclerViews
        rvIncompleteGoals.setLayoutManager(new LinearLayoutManager(this));
        rvIncompleteGoals.setAdapter(incompleteAdapter);

        rvCompletedGoals.setLayoutManager(new LinearLayoutManager(this));
        rvCompletedGoals.setAdapter(completedAdapter);

        // Carregar os goals ao abrir o app
        loadGoals();

        // Adicionar um novo goal ao clicar no botão
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalTitle = etGoal.getText().toString();
                if (!goalTitle.isEmpty()) {
                    createGoal(new Goal(goalTitle, 3, false)); // Frequência semanal arbitrária
                } else {
                    Toast.makeText(MainActivity.this, "Escreva um objetivo!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void toggleGoalCompletion(Goal goal, boolean completed) {
        goal.setCompleted(completed);

        // Atualizar no backend
        goalApi.toggleGoalCompletion(goal.getId(), goal).enqueue(new Callback<Goal>() {
            @Override
            public void onResponse(Call<Goal> call, Response<Goal> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Objetivo atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    loadGoals(); // Atualiza a listagem
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao atualizar objetivo!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Goal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro de conexão ao atualizar objetivo.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void editGoal(Goal goal) {
        goalApi.updateGoal(goal.getId(), goal).enqueue(new Callback<Goal>() {
            @Override
            public void onResponse(Call<Goal> call, Response<Goal> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Objetivo atualizado!", Toast.LENGTH_SHORT).show();
                    loadGoals();
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao atualizar objetivo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Goal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteGoal(Goal goal) {
        goalApi.deleteGoal(goal.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Objetivo removido!", Toast.LENGTH_SHORT).show();
                    loadGoals(); // Atualizar a lista de objetivos
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao remover objetivo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }




    // Método para carregar todos os goals da API
   private void loadGoals() {
        goalApi.getAllGoals().enqueue(new Callback<List<Goal>>() {
            @Override
            public void onResponse(Call<List<Goal>> call, Response<List<Goal>> response) {
                if (response.isSuccessful()) {
                    List<Goal> allGoals = response.body();
                    System.out.println("AQUI O TOTAL: " + allGoals.size());
                    if (allGoals != null) {
                        List<Goal> completedGoals = new ArrayList<>();
                        List<Goal> incompleteGoals = new ArrayList<>();
                        System.out.println("OS GOALS: " + allGoals);
                        for (Goal goal : allGoals) {
                            System.out.println("ID: " + goal.getId() + ", Title: " + goal.getTitle() + ", Completed: " + goal.isCompleted());

                            if (goal.isCompleted()) {
                                completedGoals.add(goal);
                            } else {
                                incompleteGoals.add(goal);
                            }
                        }

                        // Atualizar adaptadores
                        completedAdapter.updateGoals(completedGoals);
                        incompleteAdapter.updateGoals(incompleteGoals);

                        // Logs para depuração
                        System.out.println("Concluídos: " + completedGoals.size());
                        System.out.println("Não Concluídos: " + incompleteGoals.size());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao carregar os objetivos: Resposta inválida", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Goal>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para criar um novo goal
    private void createGoal(Goal goal) {
        goalApi.createGoal(goal).enqueue(new Callback<Goal>() {
            @Override
            public void onResponse(Call<Goal> call, Response<Goal> response) {
                if (response.isSuccessful()) {
                    System.out.println("Objetivo criado: " + response.body());
                    Toast.makeText(MainActivity.this, "Objetivo adicionado!", Toast.LENGTH_SHORT).show();
                    loadGoals(); // Atualizar a lista de goals
                    etGoal.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao adicionar objetivo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Goal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
