package com.crossfit.model;

import java.util.List;

/**
 * A type of workout in which a list of exercises must be repeated
 * for durationInMinutes minutes.
 */
public class Amrap extends Workout {
    private Integer durationInMinutes;

    public Amrap (List<Exercise> exercises, Integer durationInMinutes) {
        super(exercises);
        this.durationInMinutes = durationInMinutes;
    }

    public Integer getDurationInMinutes () {
        return durationInMinutes;
    }
}
