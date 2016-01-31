package com.crossfit.controller;

import java.util.List;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO for a request of the proposedWorkout service.
 */
class ProposedWorkoutDTO {
    private String id;
    @NotNull(message="error.exercises.null")
    @NotEmpty(message="error.exercises.empty")
    private List<ProposedExerciseDTO> exercises;

    @NotNull(message="error.type.null")
    private WorkoutType type;

    private Integer durationInMinutes;
    private Integer maxAllowedMinutes;
    private Integer numberOfRounds;

    public List<ProposedExerciseDTO> getExercises () {
        return exercises;
    }

    public void setExercises (List<ProposedExerciseDTO> exercises) {
        this.exercises = exercises;
    }

    public Integer getDurationInMinutes () {
        return durationInMinutes;
    }

    public void setDurationInMinutes (Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Integer getMaxAllowedMinutes () {
        return maxAllowedMinutes;
    }

    public void setMaxAllowedMinutes (Integer maxAllowedMinutes) {
        this.maxAllowedMinutes = maxAllowedMinutes;
    }

    public Integer getNumberOfRounds () {
        return numberOfRounds;
    }

    public void setNumberOfRounds (Integer numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public String getType () {
        return type != null ? type.toString() : null;
    }

    public void setType (String type) {
        this.type = WorkoutType.valueOf(type);
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }
}
