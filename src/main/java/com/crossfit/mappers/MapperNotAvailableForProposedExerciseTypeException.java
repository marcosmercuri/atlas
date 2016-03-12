package com.crossfit.mappers;

import java.util.Optional;

import com.crossfit.controller.ExerciseType;
import com.crossfit.exceptions.BasicException;
import com.crossfit.model.Exercise;

public class MapperNotAvailableForProposedExerciseTypeException extends BasicException {

    public MapperNotAvailableForProposedExerciseTypeException (ExerciseType exerciseType) {
        super(
              String.format("The exercise type %s cannot be transform to a domain class", exerciseType.toString()),
              String.format("The exercise type %s doesn't have a mapper available", exerciseType.toString()),
              500,
              Optional.empty()
        );
    }
}
