package com.crossfit.mappers;

import java.util.List;

import com.crossfit.controller.ProposedWorkoutDTO;
import com.crossfit.model.Workout;

public interface ProposedWorkoutMapper {

    /**
     * Maps the DTO to its corresponding entity Workout (be Amrpa, ForTime, etc)
     * @param proposedWorkoutDTO the DTO to be mapped
     * @return the model entity with the same values as proposedWorkoutDTO.
     */
    Workout mapToEntity (ProposedWorkoutDTO proposedWorkoutDTO);

    /**
     * Maps the entity to the DTO.
     * @param workout entity to be mapped
     * @return The DTO with the same values as the entity.
     */
    ProposedWorkoutDTO mapToDto(Workout workout);

    /**
     * Maps the list of entities to a list of Dtos
     * @param workouts the list to map
     * @return the dtos
     */
    List<ProposedWorkoutDTO> mapToDtos(List<Workout> workouts);
}
