package com.crossfit.exceptions;

import static com.crossfit.util.RequestErrorCodes.*;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ProposedWorkoutNotFoundException extends BasicException {

    public ProposedWorkoutNotFoundException (String proposedWorkoutId) {
        super(String.format("Proposed workout not found for id %s", proposedWorkoutId),
              PROPOSED_WORKOUT_NOT_FOUND,
              Optional.empty()
        );
    }
}
