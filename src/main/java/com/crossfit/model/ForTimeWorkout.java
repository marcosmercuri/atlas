package com.crossfit.model;

import java.util.List;

/**
 *
 */
public class ForTimeWorkout extends Workout {
    private Integer maxAllowedMinutes;

    public ForTimeWorkout (List<Exercise> exercises, Integer maxAllowedMinutes) {
        super(exercises);
        this.maxAllowedMinutes = maxAllowedMinutes;
    }

    public Integer getMaxAllowedMinutes () {
        return maxAllowedMinutes;
    }
}
