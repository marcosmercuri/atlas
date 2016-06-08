package com.crossfit.mappers;

import com.crossfit.controller.ResultExerciseDTO;
import com.crossfit.model.ResultExercise;

public interface ResultExerciseMapper {

    /**
     * Maps the DTO to its corresponding entity Result (NonRxResultExercise, RxResultExercise)
     *
     * @param resultExerciseDTO the DTO to be mapped
     * @return the model entity with the same values as resultExerciseDTO.
     */
    ResultExercise mapToEntity (ResultExerciseDTO resultExerciseDTO);

    /**
     * Maps the entity to the DTO.
     *
     * @param exercise entity to be mapped
     * @return The DTO with the same values as the entity.
     */
    ResultExerciseDTO mapToDto (ResultExercise exercise);
}
