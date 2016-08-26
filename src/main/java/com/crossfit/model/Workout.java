package com.crossfit.model;

import java.util.List;
import java.util.UUID;
import javax.persistence.*;

/**
 * Represents a full workout (or WOD)
 */
@Entity
@Table(name = "proposedWorkouts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
public abstract class Workout {
    @Id
    private String id;
    @OneToMany(cascade = CascadeType.ALL)
    private final List<Exercise> exercises;

    // This is needed for spring to hydrate the object
    protected Workout() {
        this(null, null);
    }

    public Workout(String id, List<Exercise> exercises) {
        this.id = id;
        this.exercises = exercises;
    }

    /**
     * It is only executed on insert, and generates an UUID for the entity
     */
    @PrePersist
    private void autoGenerateId(){
        this.id = UUID.randomUUID().toString();
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
