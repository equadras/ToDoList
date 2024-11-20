package com.trabalho.main.repository;

import com.trabalho.main.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, String> {
}
