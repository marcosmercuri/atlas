package com.crossfit.controller;

import static com.crossfit.util.RequestErrorCodes.*;

import java.util.Optional;

import com.crossfit.exceptions.BasicException;

public enum WorkoutType {
    AMRAP("AMRAP"),
    FOR_TIME("FOR_TIME");

    private String value;

    WorkoutType (String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static WorkoutType getWorkoutType(String type) {
        for (WorkoutType workoutType : values()) {
            if (workoutType.value.equalsIgnoreCase(type)) {
                return workoutType;
            }
        }
        throw createInvalidTypeException();
    }

    private static BasicException createInvalidTypeException () {
        return new BasicException("error.proposedWorkout.type.invalid",
              "error.proposedWorkout.type.invalid.developerMessage",
              INVALID_FIELDS_IN_REQUEST_ERROR_CODE,
              Optional.empty());
    }
}
