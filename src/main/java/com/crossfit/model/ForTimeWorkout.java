package com.crossfit.model;

import java.util.List;
import javax.persistence.Entity;

/**
 * A type of workout in which a list of exercises must be repeated
 * numberOfRounds times, in a max time of maxAllowedSeconds seconds.
 */
@Entity
public class ForTimeWorkout extends Workout {
    private final Integer maxAllowedSeconds;
    private final Integer numberOfRounds;

    // This is needed for spring to hydrate the object
    private ForTimeWorkout() {
        this(null, null, null, null);
    }

    public ForTimeWorkout (List<Exercise> exercises, Integer maxAllowedSeconds, Integer numberOfRounds, String id) {
        super(id, exercises);
        this.maxAllowedSeconds = maxAllowedSeconds;
        this.numberOfRounds = numberOfRounds;
    }

    public Integer getMaxAllowedSeconds () {
        return maxAllowedSeconds;
    }

    public Integer getNumberOfRounds () {
        return numberOfRounds;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;

        ForTimeWorkout that = (ForTimeWorkout)o;

        if(maxAllowedSeconds != null? !maxAllowedSeconds.equals(that.maxAllowedSeconds) : that.maxAllowedSeconds != null)
            return false;
        return numberOfRounds != null? numberOfRounds.equals(that.numberOfRounds) : that.numberOfRounds == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (maxAllowedSeconds != null? maxAllowedSeconds.hashCode() : 0);
        result = 31 * result + (numberOfRounds != null? numberOfRounds.hashCode() : 0);
        return result;
    }
}
