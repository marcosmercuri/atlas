package com.crossfit.model;

/**
 * Represent a physical exercise that has to be repeated
 * durationInSeconds-seconds.
 */
public class TimedExercise extends Exercise {
    private Integer durationInSeconds;

    public TimedExercise (Integer durationInSeconds, Integer maleRxInKilograms, Integer femaleRxInKilograms, String name, String description) {
        super(maleRxInKilograms, femaleRxInKilograms, name, description);
        this.durationInSeconds = durationInSeconds;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TimedExercise timedExercise = (TimedExercise) o;

        return !(durationInSeconds != null ? !durationInSeconds.equals(timedExercise.durationInSeconds) : timedExercise.durationInSeconds != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (durationInSeconds != null ? durationInSeconds.hashCode() : 0);
        return result;
    }
}
