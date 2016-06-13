package com.crossfit.services;

import com.crossfit.model.ResultWorkout;

public interface ResultWorkoutService {
    /**
     * Saves the result workout, giving it an id.
     * @param resultWorkout result workout to be saved.
     * @return saved result workout with an id.
     */
    ResultWorkout saveResultWorkout(ResultWorkout resultWorkout);

}
