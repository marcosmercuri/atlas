package com.crossfit.model;

/**
 * Represents a physical exercise with some kind of a counter
 */
public class Task {
    private Exercise exercise;

    public Task (Exercise exercise) {
        this.exercise = exercise;
    }

    public Exercise getExercise () {
        return exercise;
    }
}
