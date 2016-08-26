package com.crossfit.model;

import javax.persistence.Entity;

@Entity
public class NonRxResultExercise extends ResultExercise {
    private final Float weightInKilograms;
    private final String replaceExercise;

    // This is needed for spring to hydrate the object
    private NonRxResultExercise() {
        this(null, null, null, null, null, null);
    }

    public NonRxResultExercise (String id, String proposedExerciseId, RoundsCounter roundsCounter, String comments, Float weightInKilograms, String replaceExercise) {
        super(id, proposedExerciseId, roundsCounter, comments);
        this.weightInKilograms = weightInKilograms;
        this.replaceExercise = replaceExercise;
    }

    public Float getWeightInKilograms () {
        return weightInKilograms;
    }

    public String getReplaceExercise () {
        return replaceExercise;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;

        NonRxResultExercise that = (NonRxResultExercise)o;

        if(weightInKilograms != null? !weightInKilograms.equals(that.weightInKilograms) : that.weightInKilograms != null)
            return false;
        return replaceExercise != null? replaceExercise.equals(that.replaceExercise) : that.replaceExercise == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (weightInKilograms != null? weightInKilograms.hashCode() : 0);
        result = 31 * result + (replaceExercise != null? replaceExercise.hashCode() : 0);
        return result;
    }
}
