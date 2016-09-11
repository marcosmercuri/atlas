package com.crossfit.services;

import com.crossfit.model.ResultWorkout;
import com.crossfit.model.User;

public interface ResultWorkoutService {
    /**
     * Saves the result workout, giving it an id.
     * @param resultWorkout result workout to be saved.
     * @return saved result workout with an id.
     */
    ResultWorkout saveResultWorkout(ResultWorkout resultWorkout);

    /**
     * Returns a saved result workout that belongs to the user
     * @param resultWorkoutId id of the ResultWorkout to retrieve
     * @param user the user making the request
     * @return the retrieved workout
     */
    ResultWorkout getResultWorkout(String resultWorkoutId, User user);

    /**
     * Deletes the result workout
     * @param resultWorkoutId id of the resultWorkout to delete
     * @param user the user making the request
     */
    void deleteResultWorkout(String resultWorkoutId, User user);

    /**
     * Updates the resultWorkout that has the resultWorkoutId.
     * @param resultWorkout the new result workout content
     * @param resultWorkoutId the result workout to update
     * @param user the user making the request
     */
    void updateResultWorkout(String resultWorkoutId, ResultWorkout resultWorkout, User user);
}
