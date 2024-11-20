package com.trabalho.main.service;

import com.trabalho.main.model.Goal;
import com.trabalho.main.model.GoalCompletion;
import com.trabalho.main.repository.GoalCompletionRepository;
import com.trabalho.main.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private GoalCompletionRepository goalCompletionRepository;

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public Goal createGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public void deleteGoal(String id) {
        goalRepository.deleteById(id);
    }

    public GoalCompletion completeGoal(GoalCompletion goalCompletion) {
        return goalCompletionRepository.save(goalCompletion);
    }

    public Goal getGoalById(String id) {
        return goalRepository.findById(id).orElse(null);
    }

    public Goal updateGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public List<GoalCompletion> getAllCompletions() {
        return goalCompletionRepository.findAll();
    }
}
