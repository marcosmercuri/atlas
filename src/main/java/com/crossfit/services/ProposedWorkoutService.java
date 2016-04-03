package com.crossfit.services;

import com.crossfit.controller.ProposedWorkoutDTO;

public interface ProposedWorkoutService {

    /**
     * Saves the proposed workout, giving it an id.
     * @param proposedWorkoutDTO workout to be saved.
     * @return saved workout with an id.
     */
    ProposedWorkoutDTO saveProposedWorkout(ProposedWorkoutDTO proposedWorkoutDTO);

    /**
     * Retrieves a proposed workout by its id
     */
    ProposedWorkoutDTO getProposedWorkoutById(String proposedWorkoutId);

    /**
     * Updates the proposedWorkout that has the proposedWorkoutId.
     */
    void updateProposedWorkout (String proposedWorkoutId, ProposedWorkoutDTO proposedWorkoutDTO);
}
