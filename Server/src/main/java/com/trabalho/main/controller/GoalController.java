package com.trabalho.main.controller;

import com.trabalho.main.model.Goal;
import com.trabalho.main.model.GoalCompletion;
import com.trabalho.main.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/completions")
    public List<GoalCompletion> getAllCompletions() {
        return goalService.getAllCompletions();
    }
}
