package com.project2.todolist.models;

public class Goal {
    private String id;
    private String title;
    private int desiredWeeklyFrequency;
    private boolean isEditing; // Propriedade para controlar o estado de edição (frontend)

    public Goal(String title, int desiredWeeklyFrequency) {
        this.title = title;
        this.desiredWeeklyFrequency = desiredWeeklyFrequency;
        this.isEditing = false;
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

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }
}
