package com.trabalho.main.controller;

import com.trabalho.main.model.Goal;
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
        goal.setCompleted(false); // Garante que o campo é inicializado corretamente
        return goalService.createGoal(goal);
    }

    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable String id) {
        goalService.deleteGoal(id);
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

    @PatchMapping("/{id}/toggle-completion")
    public ResponseEntity<Goal> toggleGoalCompletion(@PathVariable String id, @RequestBody Goal goal) {
        Goal existingGoal = goalService.getGoalById(id);

        if (existingGoal != null) {
            existingGoal.setCompleted(goal.isCompleted());
            Goal updatedGoal = goalService.updateGoal(existingGoal);
            return ResponseEntity.ok(updatedGoal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
