package com.crossfit.mappers;

import com.crossfit.controller.ProposedWorkoutDTO;
import com.crossfit.model.Workout;

public interface ProposedWorkoutMapper {

    /**
     *
     * @param proposedWorkoutDTO
     * @return
     */
    Workout mapToEntity (ProposedWorkoutDTO proposedWorkoutDTO);
}
