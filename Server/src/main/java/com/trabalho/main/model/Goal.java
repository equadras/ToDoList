package com.trabalho.main.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.ZonedDateTime;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(name = "desired_weekly_frequency", nullable = false)
    private int desiredWeeklyFrequency;

    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

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

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
