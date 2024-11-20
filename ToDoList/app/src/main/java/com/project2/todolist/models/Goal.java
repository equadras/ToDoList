package com.project2.todolist.models;

public class Goal {
    private String id;
    private String title;
    private int desiredWeeklyFrequency;

    // Construtor padrão (necessário para Retrofit e Gson)
    public Goal() {
    }

    // Construtor com argumentos
    public Goal(String title, int desiredWeeklyFrequency) {
        this.title = title;
        this.desiredWeeklyFrequency = desiredWeeklyFrequency;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDesiredWeeklyFrequency() {
        return desiredWeeklyFrequency;
    }

    public void setDesiredWeeklyFrequency(int desiredWeeklyFrequency) {
        this.desiredWeeklyFrequency = desiredWeeklyFrequency;
    }
}
