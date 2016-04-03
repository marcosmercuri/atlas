package com.crossfit.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a full workout (or WOD)
 */
//TODO Find out how to store the same entity in different collections
@Document(collection = "proposedworkouts")
public class Workout {
    @Id
    private String id;
    private final List<Exercise> exercises;

    public Workout (String id, List<Exercise> exercises) {
        this.id = id;
        this.exercises = exercises;
    }

    public Workout (List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Exercise> getExercises () {
        return exercises;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }
}
