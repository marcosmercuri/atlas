package com.crossfit.model;

import java.util.List;

/**
 *
 */
public class ForTimeWorkout extends Workout {
    private Integer maxAllowedMinutes;

    public ForTimeWorkout (List<Task> tasks, Integer maxAllowedMinutes) {
        super(tasks);
        this.maxAllowedMinutes = maxAllowedMinutes;
    }

    public Integer getMaxAllowedMinutes () {
        return maxAllowedMinutes;
    }
}
