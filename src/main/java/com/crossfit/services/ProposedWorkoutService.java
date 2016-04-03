package com.crossfit.services;

import com.crossfit.model.Workout;

public interface ProposedWorkoutService {

    /**
     * Saves the proposed workout, giving it an id.
     * @param proposedWorkout workout to be saved.
     * @return saved workout with an id.
     */
    Workout saveProposedWorkout(Workout proposedWorkout);

    /**
     * Retrieves a proposed workout by its id
     */
    Workout getProposedWorkoutById(String proposedWorkoutId);

    /**
     * Updates the proposedWorkout that has the proposedWorkoutId.
     */
    void updateProposedWorkout (String proposedWorkoutId, Workout proposedWorkout);

    /**
     * Deletes the proposedWorkout with the given id.
     */
    void deleteProposedWorkout (String proposedWorkoutId);
}
