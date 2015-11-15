package com.crossfit.model;

/**
 * Represent a physical exercise that has to be repeated
 * durationInMinutes-minutes.
 */
public class TimedTask extends Task {
    private Integer durationInMinutes;

    public TimedTask(Integer durationInMinutes) {
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

        TimedTask timedTask = (TimedTask) o;

        return !(durationInMinutes != null ? !durationInMinutes.equals(timedTask.durationInMinutes) : timedTask.durationInMinutes != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (durationInMinutes != null ? durationInMinutes.hashCode() : 0);
        return result;
    }
}
