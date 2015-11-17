package com.crossfit.model;

import java.util.List;

/**
 * Represents a full workout (or WOD)
 */
public class Workout {
    private List<Exercise> exercises;

    public Workout (List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Exercise> getExercises () {
        return exercises;
    }
}
