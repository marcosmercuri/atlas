package com.crossfit.model;

/**
 * Represent a physical exercise that has to be repeated
 * distanceInMeters-meters.
 */
public class DistanceExercise extends Exercise {
    private final Double distanceInMeters;

    public DistanceExercise (Double distanceInMeters, String name) {
        super(name);
        this.distanceInMeters = distanceInMeters;
    }

    public Double getDistanceInMeters() {
        return distanceInMeters;
    }

}
