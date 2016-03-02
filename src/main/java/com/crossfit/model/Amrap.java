package com.crossfit.model;

import java.util.List;

/**
 * A type of workout in which a list of exercises must be repeated
 * for durationInSeconds seconds.
 */
public class Amrap extends Workout {
    private Integer durationInSeconds;

    public Amrap (List<Exercise> exercises, Integer durationInSeconds) {
        super(exercises);
        this.durationInSeconds = durationInSeconds;
    }

    public Integer getDurationInSeconds () {
        return durationInSeconds;
    }
}
