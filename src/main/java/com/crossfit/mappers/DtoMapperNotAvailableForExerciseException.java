package com.crossfit.mappers;

import java.util.Optional;

import com.crossfit.exceptions.BasicException;

class DtoMapperNotAvailableForExerciseException extends BasicException {

    DtoMapperNotAvailableForExerciseException (String exerciseClassName) {
        super(
              String.format("The exercise class %s cannot be transform to a DTO", exerciseClassName),
              String.format("The exercise class %s doesn't have a mapper available", exerciseClassName),
              500,
              Optional.empty()
        );
    }
}
