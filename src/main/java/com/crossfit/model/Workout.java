package com.crossfit.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a full workout (or WOD)
 */
@Document(collection = "proposedworkouts")
public abstract class Workout {
    @Id
    private final String id;
    private final List<Exercise> exercises;

    public Workout(String id, List<Exercise> exercises) {
        this.id = id;
        this.exercises = exercises;
    }

    public List<Exercise> getExercises () {
        return exercises;
    }

    public String getId () {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Workout workout = (Workout)o;

        if(id != null? !id.equals(workout.id) : workout.id != null) return false;
        return exercises != null? exercises.equals(workout.exercises) : workout.exercises == null;
    }

    @Override
    public int hashCode() {
        int result = id != null? id.hashCode() : 0;
        result = 31 * result + (exercises != null? exercises.hashCode() : 0);
        return result;
    }
}
