package com.crossfit.model;

/**
 * Represent a physical exercise that has to be repeated
 * repetitions-times.
 */
public class RepetitionExercise extends Exercise {
    private Integer repetitions;

    public RepetitionExercise (Integer repetitions, Integer maleRxInKilograms, Integer femaleRxInKilograms, String name, String description) {
        super(maleRxInKilograms, femaleRxInKilograms, name, description);
        this.repetitions = repetitions;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RepetitionExercise that = (RepetitionExercise) o;

        return !(repetitions != null ? !repetitions.equals(that.repetitions) : that.repetitions != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (repetitions != null ? repetitions.hashCode() : 0);
        return result;
    }
}
