package com.crossfit.model;

import java.util.List;

/**
 *
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
