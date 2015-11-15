package com.crossfit.manager;

import com.crossfit.model.Workout;

public interface AtlasManager {

    /**
     * Devuelve un id
     * @param proposedWorkout
     * @return
     */
    Integer saveProposedWorkout(Workout proposedWorkout);
}
