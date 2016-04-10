package com.crossfit.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO for a request of the proposedWorkout service.
 */
public class ProposedWorkoutDTO {
    private String id;
    @NotNull(message="error.proposedWorkout.exercises.null")
    @NotEmpty(message="error.proposedWorkout.exercises.empty")
    @Valid
    private List<ProposedExerciseDTO> exercises;

    @NotNull(message="error.proposedWorkout.type.null")
    private WorkoutType type;

    @Min (value = 1, message="error.proposedWorkout.durationInSeconds.invalid")
    private Integer durationInSeconds;

    @Min (value = 1, message="error.proposedWorkout.maxAllowedSeconds.invalid")
    private Integer maxAllowedSeconds;

    @Min (value = 1, message="error.proposedWorkout.numberOfRounds.invalid")
    private Integer numberOfRounds;

    public List<ProposedExerciseDTO> getExercises () {
        return exercises;
    }

    public void setExercises (List<ProposedExerciseDTO> exercises) {
        this.exercises = exercises;
    }

    public Integer getDurationInSeconds () {
        return durationInSeconds;
    }

    public void setDurationInSeconds (Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public Integer getMaxAllowedSeconds () {
        return maxAllowedSeconds;
    }

    public void setMaxAllowedSeconds (Integer maxAllowedSeconds) {
        this.maxAllowedSeconds = maxAllowedSeconds;
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

    @JsonIgnore
    public WorkoutType getTypeEnum() {
        return type;
    }

    public void setType (String type) {
        this.type = WorkoutType.getWorkoutType(type);
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }
}
