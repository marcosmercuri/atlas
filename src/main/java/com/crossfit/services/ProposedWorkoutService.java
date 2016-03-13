package com.crossfit.services;

import com.crossfit.model.Workout;

public interface ProposedWorkoutService {

    /**
     * Saves the proposed workout, giving an id.
     * @param proposedWorkout workout to be saved.
     * @return saved workout with an id.
     */
    Workout saveProposedWorkout(Workout proposedWorkout);
}
