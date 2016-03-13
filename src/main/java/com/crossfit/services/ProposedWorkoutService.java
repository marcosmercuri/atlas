package com.crossfit.services;

import com.crossfit.controller.ProposedWorkoutDTO;

public interface ProposedWorkoutService {

    /**
     * Saves the proposed workout, giving it an id.
     * @param proposedWorkoutDTO workout to be saved.
     * @return saved workout with an id.
     */
    ProposedWorkoutDTO saveProposedWorkout(ProposedWorkoutDTO proposedWorkoutDTO);
}
