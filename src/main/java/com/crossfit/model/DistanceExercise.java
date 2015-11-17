package com.crossfit.model;

/**
 * Represent a physical exercise that has to be repeated
 * distanceInMeters-meters.
 */
public class DistanceExercise extends Exercise {
    private Integer distanceInMeters;

    public DistanceExercise (Integer distanceInMeters, Integer maleRxInKilograms, Integer femaleRxInKilograms, String name, String description) {
        super(maleRxInKilograms, femaleRxInKilograms, name, description);
        this.distanceInMeters = distanceInMeters;
    }

    public Integer getDistanceInMeters() {
        return distanceInMeters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistanceExercise that = (DistanceExercise) o;

        return !(distanceInMeters != null ? !distanceInMeters.equals(that.distanceInMeters) : that.distanceInMeters != null);
    }

    @Override
    public int hashCode() {
        return distanceInMeters != null ? distanceInMeters.hashCode() : 0;
    }
}
