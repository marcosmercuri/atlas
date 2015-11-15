package com.crossfit.model;

/**
 * Represent a physical exercise that has to be repeated
 * distanceInMeters-meters.
 */
public class DistanceTask {
    private Integer distanceInMeters;

    public DistanceTask(Integer distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public Integer getDistanceInMeters() {
        return distanceInMeters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistanceTask that = (DistanceTask) o;

        return !(distanceInMeters != null ? !distanceInMeters.equals(that.distanceInMeters) : that.distanceInMeters != null);
    }

    @Override
    public int hashCode() {
        return distanceInMeters != null ? distanceInMeters.hashCode() : 0;
    }
}
