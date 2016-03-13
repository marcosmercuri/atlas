package com.crossfit.mappers;

import java.util.Optional;

import com.crossfit.controller.WorkoutType;
import com.crossfit.exceptions.BasicException;

class MapperNotAvailableForProposedWorkoutTypeException extends BasicException {
    MapperNotAvailableForProposedWorkoutTypeException (WorkoutType workoutType) {
        super(
              String.format("The workout type %s cannot be transform to a domain class", workoutType.toString()),
              String.format("The workout type %s doesn't have a mapper available", workoutType.toString()),
              500,
              Optional.empty()
        );
    }
}
