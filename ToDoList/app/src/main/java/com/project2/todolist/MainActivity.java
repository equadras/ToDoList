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
    private RecyclerView rvGoals;
    private GoalAdapter adapter;
    private GoalApi goalApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Conectando os elementos do layout
        etGoal = findViewById(R.id.etGoal);
        rvGoals = findViewById(R.id.rvGoals);

        // Configurar o RecyclerView
        rvGoals.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GoalAdapter(new ArrayList<>());
        rvGoals.setAdapter(adapter);

        // Inicializar Retrofit
        goalApi = RetrofitClient.getInstance().create(GoalApi.class);

        // Carregar os goals ao abrir o app
        loadGoals();

        // Adicionar um novo goal ao clicar no botão
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalTitle = etGoal.getText().toString();
                if (!goalTitle.isEmpty()) {
                    createGoal(new Goal(goalTitle, 3)); // Frequência semanal arbitrária
                } else {
                    Toast.makeText(MainActivity.this, "Digite um goal!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para carregar todos os goals da API
    private void loadGoals() {
        goalApi.getAllGoals().enqueue(new Callback<List<Goal>>() {
            @Override
            public void onResponse(Call<List<Goal>> call, Response<List<Goal>> response) {
                if (response.isSuccessful()) {
                    adapter.updateGoals(response.body());
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao carregar goals", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Goal>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para criar um novo goal
    private void createGoal(Goal goal) {
        goalApi.createGoal(goal).enqueue(new Callback<Goal>() {
            @Override
            public void onResponse(Call<Goal> call, Response<Goal> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Goal adicionado!", Toast.LENGTH_SHORT).show();
                    loadGoals(); // Atualizar a lista de goals
                    etGoal.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao adicionar goal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Goal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
