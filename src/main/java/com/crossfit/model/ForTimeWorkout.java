package com.crossfit.model;

import java.util.List;

/**
 * A type of workout in which a list of exercises must be repeated
 * numberOfRounds times, in a max time of maxAllowedSeconds seconds.
 */
public class ForTimeWorkout extends Workout {
    private Integer maxAllowedSeconds;
    private Integer numberOfRounds;

    public ForTimeWorkout (List<Exercise> exercises, Integer maxAllowedSeconds, Integer numberOfRounds) {
        super(exercises);
        this.maxAllowedSeconds = maxAllowedSeconds;
        this.numberOfRounds = numberOfRounds;
    }

    public Integer getMaxAllowedSeconds () {
        return maxAllowedSeconds;
    }

    public Integer getNumberOfRounds () {
        return numberOfRounds;
    }
}
