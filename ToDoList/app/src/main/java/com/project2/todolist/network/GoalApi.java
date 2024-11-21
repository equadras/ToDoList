package com.project2.todolist.network;

import com.project2.todolist.models.Goal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GoalApi {
    @GET("/api/goals")
    Call<List<Goal>> getAllGoals();

    @POST("/api/goals")
    Call<Goal> createGoal(@Body Goal goal);

    @PUT("/api/goals/{id}")
    Call<Goal> updateGoal(@Path("id") String id, @Body Goal goal);

    @DELETE("/api/goals/{id}")
    Call<Void> deleteGoal(@Path("id") String id);

   @HTTP(method = "PATCH", path = "/api/goals/{id}/toggle-completion", hasBody = true)
    Call<Goal> toggleGoalCompletion(@Path("id") String id, @Body Goal goal);
}
