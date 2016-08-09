package com.crossfit.model;

/**
 * Represent a physical exercise that has to be repeated
 * durationInSeconds-seconds.
 */
public class TimedExercise extends Exercise {
    private final Integer durationInSeconds;

    public TimedExercise (Integer durationInSeconds, String name, String id, Double maleRxInKilograms, Double femaleRxInKilograms, String description) {
        super(id, name, maleRxInKilograms, femaleRxInKilograms, description);
        this.durationInSeconds = durationInSeconds;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;

        TimedExercise that = (TimedExercise)o;

        return durationInSeconds != null? durationInSeconds.equals(that.durationInSeconds) : that.durationInSeconds == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (durationInSeconds != null? durationInSeconds.hashCode() : 0);
        return result;
    }
}
