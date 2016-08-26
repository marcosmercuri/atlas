package com.crossfit.model;

import javax.persistence.Entity;

/**
 * Represent a physical exercise that has to be repeated
 * distanceInMeters-meters.
 */
@Entity
public class DistanceExercise extends Exercise {
    private final Double distanceInMeters;

    // This is needed for spring to hydrate the object
    private DistanceExercise() {
        this(null, null, null, null, null, null);
    }

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
