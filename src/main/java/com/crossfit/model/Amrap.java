package com.crossfit.model;

import java.util.List;

/**
 * A type of workout in which a list of exercises must be repeated
 * for durationInSeconds seconds.
 */
public class Amrap extends Workout {
    private final Integer durationInSeconds;

    public Amrap (List<Exercise> exercises, Integer durationInSeconds, String id) {
        super(id, exercises);
        this.durationInSeconds = durationInSeconds;
    }

    public Integer getDurationInSeconds () {
        return durationInSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;

        Amrap amrap = (Amrap)o;

        return durationInSeconds != null? durationInSeconds.equals(amrap.durationInSeconds) : amrap.durationInSeconds == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (durationInSeconds != null? durationInSeconds.hashCode() : 0);
        return result;
    }
}
