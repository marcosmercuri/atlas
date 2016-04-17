package com.crossfit.controller;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * POJO for a done exercise.
 */
public class ResultExerciseDTO {
    private String Id;

    /**
     * This field can be null if the proposed exercise couldn't be done.
     * See also replaceExercise.
     */
    @NotBlank (message="error.resultExercise.proposedExerciseId.notBlank")
    private String proposedExerciseId;

    @NotNull (message = "error.resultExercise.rx.notNull")
    private Boolean rx;

    /**
     * Number of full completed rounds. Eg, 3 rounds of 5 burpees.
     * See also repetitionsOnUnfinishedRound
     */
    @NotNull (message = "error.resultExercise.completedRounds.notNull")
    @Min (value = 1, message = "error.resultExercise.completedRounds.belowMinimumOne")
    private Integer completedRounds;

    /**
     * If time was up when in the middle of a round, this field
     * indicates the number of repetitions done in that round.
     */
    @Min (value = 1, message = "error.resultExercise.repetitionsOnUnfinishedRound.belowMinimumOne")
    private Integer repetitionsOnUnfinishedRound;
    private String comments;

    @NotNull (message = "error.resultExercise.type.notNull")
    private ExerciseType type;

    @Min (value = 1, message = "error.resultExercise.weightInKilograms.belowMinimumOne")
    private Float weightInKilograms;

    /**
     * If the proposed exercise couldn't be done, this field
     * indicates the exercise that replaced it.
     */
    private String replaceExercise;

    public Float getWeightInKilograms () {
        return weightInKilograms;
    }

    public void setWeightInKilograms (Float weightInKilograms) {
        this.weightInKilograms = weightInKilograms;
    }

    public String getReplaceExercise () {
        return replaceExercise;
    }

    public void setReplaceExercise (String replaceExercise) {
        this.replaceExercise = replaceExercise;
    }

    public ExerciseType getType () {
        return type;
    }

    public void setType (ExerciseType type) {
        this.type = type;
    }

    public String getComments () {
        return comments;
    }

    public void setComments (String comments) {
        this.comments = comments;
    }

    public Integer getRepetitionsOnUnfinishedRound () {
        return repetitionsOnUnfinishedRound;
    }

    public void setRepetitionsOnUnfinishedRound (Integer repetitionsOnUnfinishedRound) {
        this.repetitionsOnUnfinishedRound = repetitionsOnUnfinishedRound;
    }

    public Integer getCompletedRounds () {
        return completedRounds;
    }

    public void setCompletedRounds (Integer completedRounds) {
        this.completedRounds = completedRounds;
    }

    public Boolean getRx () {
        return rx;
    }

    public void setRx (Boolean rx) {
        this.rx = rx;
    }

    public String getProposedExerciseId () {
        return proposedExerciseId;
    }

    public void setProposedExerciseId (String proposedExerciseId) {
        this.proposedExerciseId = proposedExerciseId;
    }

    public String getId () {
        return Id;
    }

    public void setId (String id) {
        Id = id;
    }


}
