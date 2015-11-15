package com.crossfit.model;

import java.util.List;

/**
 * Represents a full workout (or WOD)
 */
public class Workout {
    private List<Task> tasks;

    public Workout (List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks () {
        return tasks;
    }
}
