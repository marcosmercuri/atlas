package com.crossfit.services;

import com.crossfit.model.ResultWorkout;

public interface ResultWorkoutService {
    /**
     * Saves the result workout, giving it an id.
     * @param resultWorkout result workout to be saved.
     * @return saved result workout with an id.
     */
    ResultWorkout saveResultWorkout(ResultWorkout resultWorkout);

    /**
     * Returns a saved result workout
     * @param resultWorkoutId id of the ResultWorkout to retrieve
     * @return the retrieved workout
     */
    ResultWorkout getResultWorkout(String resultWorkoutId);

    /**
     * Deletes the result workout
     * @param resultWorkoutId id of the resultWorkout to delete
     */
    void deleteResultWorkout(String resultWorkoutId);

    /**
     * Updates the resultWorkout that has the resultWorkoutId.
     */
    void updateResultWorkout(String resultWorkoutId, ResultWorkout resultWorkout);
}
