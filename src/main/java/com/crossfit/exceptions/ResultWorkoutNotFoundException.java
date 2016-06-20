package com.crossfit.exceptions;

import static com.crossfit.util.RequestErrorCodes.*;

import java.util.Optional;

public class ResultWorkoutNotFoundException extends BasicException {

    public ResultWorkoutNotFoundException(String resultWorkoutId) {
        super(String.format("Result workout not found for id %s", resultWorkoutId),
              RESULT_WORKOUT_NOT_FOUND,
              Optional.empty()
        );
    }
}
