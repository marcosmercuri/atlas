package com.crossfit.model;

import java.util.List;

/**
 * A type of workout in which a list of exercises must be repeated
 * numberOfRounds times, in a max time of maxAllowedMinutes minutes.
 */
public class ForTimeWorkout extends Workout {
    private Integer maxAllowedMinutes;
    private Integer numberOfRounds;

    public ForTimeWorkout (List<Exercise> exercises, Integer maxAllowedMinutes, Integer numberOfRounds) {
        super(exercises);
        this.maxAllowedMinutes = maxAllowedMinutes;
        this.numberOfRounds = numberOfRounds;
    }

    public Integer getMaxAllowedMinutes () {
        return maxAllowedMinutes;
    }

    public Integer getNumberOfRounds () {
        return numberOfRounds;
    }
}
