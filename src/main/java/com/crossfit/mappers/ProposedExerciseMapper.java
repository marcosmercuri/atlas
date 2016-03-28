package com.crossfit.mappers;

import com.crossfit.controller.ProposedExerciseDTO;
import com.crossfit.model.Exercise;

public interface ProposedExerciseMapper {

    /**
     * Maps the DTO to its corresponding entity Exercise (be TimedExercise, RepetitionExercise, etc)
     * @param proposedExerciseDTO the DTO to be mapped
     * @return the model entity with the same values as proposedExerciseDTO.
     */
    Exercise mapToEntity (ProposedExerciseDTO proposedExerciseDTO);

    /**
     * Maps the entity (TimedExercise, RepetitionExercise, etc) to the DTO with the corresponding Type.
     */
    ProposedExerciseDTO mapToDto (Exercise exercise);
}
