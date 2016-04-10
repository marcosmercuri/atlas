package com.crossfit.controller;

import static com.crossfit.util.RequestErrorCodes.INVALID_FIELDS_IN_REQUEST_ERROR_CODE;

import java.util.Optional;

import com.crossfit.exceptions.BasicException;

/**
 * Represents the different types of exercise, ie, if it's an exercise that has to be repeated a number
 * of times, or if it has to be done for a an amount of time, etc.
 */
public enum ExerciseType {
    /**
     * The exercise has to be repeated N times.
     */
    REPETITION("REPETITION"),
    /**
     * The exercise has to be done for a certain amount of time.
     */
    TIMED("TIMED"),
    /**
     * The exercise has to be done through a certain distance, such as running.
     */
    DISTANCE("DISTANCE");

    private String value;

    ExerciseType (String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static ExerciseType getExerciseType(String type) {
            for (ExerciseType exerciseType : values()) {
                if (exerciseType.value.equalsIgnoreCase(type)) {
                    return exerciseType;
                }
            }
            throw createInvalidTypeException();
    }

    private static BasicException createInvalidTypeException () {
        //I couldn't find a way to get the messageSource injected, to pass the type as argument to the message
        return new BasicException("error.proposedExercise.type.invalid",
              "error.proposedExercise.type.invalid.developerMessage",
              INVALID_FIELDS_IN_REQUEST_ERROR_CODE,
              Optional.empty());
    }
}
