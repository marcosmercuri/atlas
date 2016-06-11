package com.crossfit.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ResultWorkout {
    @Id
    private final String id;
    private final String userId;
    private final Workout proposedWorkout;
    private final List<ResultExercise> resultExercises;
    private final ResultWorkoutDetails details;

    // This is needed for spring to hydrate the object
    private ResultWorkout () {
        this(null, null, null, null, null);
    }

    public ResultWorkout (String id, String userId, Workout proposedWorkout, List<ResultExercise> resultExercises, ResultWorkoutDetails details) {
        this.id = id;
        this.userId = userId;
        this.proposedWorkout = proposedWorkout;
        this.resultExercises = resultExercises;
        this.details = details;
    }

    public String getId () {
        return id;
    }

    public String getUserId () {
        return userId;
    }

    public Workout getProposedWorkout () {
        return proposedWorkout;
    }

    public List<ResultExercise> getResultExercises () {
        return resultExercises;
    }

    public ResultWorkoutDetails getDetails () {
        return details;
    }
}
