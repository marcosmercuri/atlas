package com.crossfit.model;

import javax.persistence.Entity;

/**
 * Represent a physical exercise that has to be repeated
 * repetitions-times.
 */
@Entity
public class RepetitionExercise extends Exercise {
    private final Integer repetitions;

    // This is needed for spring to hydrate the object
    private RepetitionExercise() {
        super();
        repetitions = null;
    }

    public RepetitionExercise (Integer repetitions, String name, String id, Double maleRxInKilograms, Double femaleRxInKilograms, String description) {
        super(id, name, maleRxInKilograms, femaleRxInKilograms, description);
        this.repetitions = repetitions;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    @Override public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;

        RepetitionExercise that = (RepetitionExercise)o;

        return repetitions != null? repetitions.equals(that.repetitions) : that.repetitions == null;
    }

    @Override public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (repetitions != null? repetitions.hashCode() : 0);
        return result;
    }
}
