package com.crossfit.controller.request;

import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * POJO for a request of the proposedWorkout service.
 */
class ProposedWorkoutDTO {
    @NotNull
    private List<ProposedExerciseDTO> exercises;
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
}
