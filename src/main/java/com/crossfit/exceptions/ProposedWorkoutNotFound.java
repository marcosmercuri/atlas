package com.crossfit.exceptions;

import static com.crossfit.util.RequestErrorCodes.*;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProposedWorkoutNotFound extends BasicException {

    public ProposedWorkoutNotFound (String proposedWorkoutId) {
        super(String.format("Proposed workout not found for id %s", proposedWorkoutId),
              PROPOSED_WORKOUT_NOT_FOUND,
              Optional.empty()
        );
    }
}
