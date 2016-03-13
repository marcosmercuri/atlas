package com.crossfit.model;

/**
 * Represent a physical exercise that has to be repeated
 * durationInSeconds-seconds.
 */
public class TimedExercise extends Exercise {
    private final Integer durationInSeconds;

    public TimedExercise (Integer durationInSeconds, String name, String id) {
        super(id, name);
        this.durationInSeconds = durationInSeconds;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

}
