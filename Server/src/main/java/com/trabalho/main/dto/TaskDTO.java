package com.trabalho.main.dto;

public class TaskDTO {
    private Long id;
    private String title;

    // Construtor
    public TaskDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
