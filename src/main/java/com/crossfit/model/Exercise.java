package com.crossfit.model;

import java.util.UUID;
import javax.persistence.*;

/**
 * Represents a physical exercise, with its restrictions.
 */
@Entity
@Table(name = "proposedExercises")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
public abstract class Exercise {
    @Id
    private String id;
    private final String name;
    private final Double maleRxInKilograms;
    private final Double femaleRxInKilograms;
    private final String description;

    // This is needed for spring to hydrate the object
    protected Exercise() {
        this(null, null, null, null);
    }

    public Exercise(String id, String name, Double maleRxInKilograms, Double femaleRxInKilograms, String description) {
        this.id = id;
        this.name = name;
        this.maleRxInKilograms = maleRxInKilograms;
        this.femaleRxInKilograms = femaleRxInKilograms;
        this.description = description;
    }

    public Exercise(String name, Double maleRxInKilograms, Double femaleRxInKilograms, String description) {
        this(null, name, maleRxInKilograms, femaleRxInKilograms, description);
    }

    /**
     * It is only executed on insert, and generates an UUID for the entity
     */
    @PrePersist
    private void autoGenerateId(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaleRxInKilograms() {
        return maleRxInKilograms;
    }

    public Double getFemaleRxInKilograms() {
        return femaleRxInKilograms;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise)o;

        if(id != null? !id.equals(exercise.id) : exercise.id != null) return false;
        if(name != null? !name.equals(exercise.name) : exercise.name != null) return false;
        if(maleRxInKilograms != null? !maleRxInKilograms.equals(exercise.maleRxInKilograms) : exercise.maleRxInKilograms != null)
            return false;
        if(femaleRxInKilograms != null? !femaleRxInKilograms.equals(exercise.femaleRxInKilograms) : exercise.femaleRxInKilograms != null)
            return false;
        return description != null? description.equals(exercise.description) : exercise.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null? id.hashCode() : 0;
        result = 31 * result + (name != null? name.hashCode() : 0);
        result = 31 * result + (maleRxInKilograms != null? maleRxInKilograms.hashCode() : 0);
        result = 31 * result + (femaleRxInKilograms != null? femaleRxInKilograms.hashCode() : 0);
        result = 31 * result + (description != null? description.hashCode() : 0);
        return result;
    }
}
