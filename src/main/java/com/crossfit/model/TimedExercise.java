package com.crossfit.model;

/**
 * Represent a physical exercise that has to be repeated
 * durationInMinutes-minutes.
 */
public class TimedExercise extends Exercise {
    private Integer durationInMinutes;

    public TimedExercise (Integer durationInMinutes, Integer maleRxInKilograms, Integer femaleRxInKilograms, String name, String description) {
        super(maleRxInKilograms, femaleRxInKilograms, name, description);
        this.durationInMinutes = durationInMinutes;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TimedExercise timedExercise = (TimedExercise) o;

        return !(durationInMinutes != null ? !durationInMinutes.equals(timedExercise.durationInMinutes) : timedExercise.durationInMinutes != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (durationInMinutes != null ? durationInMinutes.hashCode() : 0);
        return result;
    }
}
