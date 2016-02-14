package com.crossfit.controller;

import static com.crossfit.controller.RequestErrorCodes.*;

import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

import com.crossfit.exceptions.BasicException;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO for a request of the proposedWorkout service.
 */
class ProposedWorkoutDTO {
    private String id;
    @NotNull(message="error.proposedWorkout.exercises.null")
    @NotEmpty(message="error.proposedWorkout.exercises.empty")
    private List<ProposedExerciseDTO> exercises;

    @NotNull(message="error.proposedWorkout.type.null")
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
        try {
            this.type = WorkoutType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw createInvalidTypeException(exception);
        }
    }

    private BasicException createInvalidTypeException (IllegalArgumentException originalException) {
        //I couldn't find a way to get the messageSource injected, to pass the type as argument to the message
        return new BasicException("error.type.invalid",
              "error.type.invalid.developerMessage",
              INVALID_FIELDS_IN_REQUEST_ERROR_CODE,
              Optional.of(originalException));
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }
}
