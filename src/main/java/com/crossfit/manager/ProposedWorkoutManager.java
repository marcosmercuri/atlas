package com.crossfit.manager;

import com.crossfit.model.Workout;

public interface ProposedWorkoutManager {

    /**
     * Saves the proposed workout, giving an id.
     * @param proposedWorkout workout to be saved.
     * @return saved workout with an id.
     */
    Workout saveProposedWorkout(Workout proposedWorkout);
}
