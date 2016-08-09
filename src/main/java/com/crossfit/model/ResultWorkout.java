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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        ResultWorkout that = (ResultWorkout)o;

        if(id != null? !id.equals(that.id) : that.id != null) return false;
        if(userId != null? !userId.equals(that.userId) : that.userId != null) return false;
        if(proposedWorkout != null? !proposedWorkout.equals(that.proposedWorkout) : that.proposedWorkout != null)
            return false;
        if(resultExercises != null? !resultExercises.equals(that.resultExercises) : that.resultExercises != null)
            return false;
        return details != null? details.equals(that.details) : that.details == null;
    }

    @Override
    public int hashCode() {
        int result = id != null? id.hashCode() : 0;
        result = 31 * result + (userId != null? userId.hashCode() : 0);
        result = 31 * result + (proposedWorkout != null? proposedWorkout.hashCode() : 0);
        result = 31 * result + (resultExercises != null? resultExercises.hashCode() : 0);
        result = 31 * result + (details != null? details.hashCode() : 0);
        return result;
    }
}
