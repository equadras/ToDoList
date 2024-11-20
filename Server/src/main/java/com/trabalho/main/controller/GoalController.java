package com.trabalho.main.controller;

import com.trabalho.main.model.Goal;
import com.trabalho.main.model.GoalCompletion;
import com.trabalho.main.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping
    public List<Goal> getAllGoals() {
        return goalService.getAllGoals();
    }

    @PostMapping
    public Goal createGoal(@RequestBody Goal goal) {
        return goalService.createGoal(goal);
    }

    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable String id) {
        goalService.deleteGoal(id);
    }

    @PostMapping("/{id}/complete")
    public GoalCompletion completeGoal(@PathVariable String id) {
        Goal goal = new Goal();
        goal.setId(id);

        GoalCompletion completion = new GoalCompletion();
        completion.setGoal(goal);

        return goalService.completeGoal(completion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable String id, @RequestBody Goal updatedGoal) {
        Goal existingGoal = goalService.getGoalById(id); // Assumindo que o GoalService tem esse método.

        if (existingGoal != null) {
            existingGoal.setTitle(updatedGoal.getTitle());
            existingGoal.setDesiredWeeklyFrequency(updatedGoal.getDesiredWeeklyFrequency());
            Goal savedGoal = goalService.updateGoal(existingGoal);
            return ResponseEntity.ok(savedGoal);
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se o objetivo não for encontrado.
        }
    }

    @GetMapping("/completions")
    public List<GoalCompletion> getAllCompletions() {
        return goalService.getAllCompletions();
    }
}
