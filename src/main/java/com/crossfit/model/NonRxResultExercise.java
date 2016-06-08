package com.crossfit.model;

public class NonRxResultExercise extends ResultExercise {
    private final Float weightInKilograms;
    private final String replaceExercise;

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
}
