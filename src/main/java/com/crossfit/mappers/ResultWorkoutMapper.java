package com.crossfit.mappers;

import java.util.List;

import com.crossfit.controller.ResultWorkoutDTO;
import com.crossfit.model.ResultWorkout;

public interface ResultWorkoutMapper {

    /**
     * Maps the DTO to an ResultWorkout entity
     *
     * @param resultWorkoutDto the DTO to be mapped
     * @return the model entity with the same values as resultWorkoutDto.
     */
    ResultWorkout mapToEntity (ResultWorkoutDTO resultWorkoutDto, String userId);

    /**
     * Maps the entity to the DTO.
     *
     * @param resultWorkout entity to be mapped
     * @return The DTO with the same values as the entity.
     */
    ResultWorkoutDTO mapToDto (ResultWorkout resultWorkout);

    /**
     * Maps the list of entities to a list of Dtos
     * @param resultWorkouts the list to map
     * @return the dtos
     */
    List<ResultWorkoutDTO> mapToDtos(List<ResultWorkout> resultWorkouts);
}
