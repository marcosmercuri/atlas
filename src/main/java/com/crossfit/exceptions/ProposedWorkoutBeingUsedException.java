package com.crossfit.exceptions;


import static com.crossfit.util.RequestErrorCodes.CANNOT_DELETE_PROPOSED_WORKOUT;

import java.util.Optional;

public class ProposedWorkoutBeingUsedException extends BasicException {

    public ProposedWorkoutBeingUsedException(String proposedWorkoutId) {
        super(String.format("The proposed workout %s cannot be deleted as it is being referred to",  proposedWorkoutId),
              CANNOT_DELETE_PROPOSED_WORKOUT,
              Optional.empty()
        );
    }
}
