package com.crossfit.model;

/**
 * Represent a physical exercise that has to be repeated
 * distanceInMeters-meters.
 */
public class DistanceExercise extends Exercise {
    private final Double distanceInMeters;

    public DistanceExercise (Double distanceInMeters, String name, String id, Double maleRxInKilograms, Double femaleRxInKilograms, String description) {
        super(id, name, maleRxInKilograms, femaleRxInKilograms, description);
        this.distanceInMeters = distanceInMeters;
    }

    public Double getDistanceInMeters() {
        return distanceInMeters;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;

        DistanceExercise that = (DistanceExercise)o;

        return distanceInMeters != null? distanceInMeters.equals(that.distanceInMeters) : that.distanceInMeters == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (distanceInMeters != null? distanceInMeters.hashCode() : 0);
        return result;
    }
}
