package com.crossfit.mappers;

import java.util.Optional;

import com.crossfit.exceptions.BasicException;

class DtoMapperNotAvailableForWorkoutException  extends BasicException {

    DtoMapperNotAvailableForWorkoutException (String workoutClassName) {
        super(
              String.format("The workout class %s cannot be transform to a DTO", workoutClassName),
              String.format("The workout class %s doesn't have a mapper available", workoutClassName),
              500,
              Optional.empty()
        );
    }
}

