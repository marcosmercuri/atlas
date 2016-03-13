package com.crossfit.model;

import java.util.List;

/**
 * Represents a full workout (or WOD)
 */
public class Workout {
    @Id
    private final String id;
    private final List<Exercise> exercises;

    public Workout (String id, List<Exercise> exercises) {
        this.id = id;
        this.exercises = exercises;
    }

    public List<Exercise> getExercises () {
        return exercises;
    }

    public String getId () {
        return id;
    }
}
