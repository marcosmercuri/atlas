package com.crossfit.model;

import javax.persistence.Entity;

@Entity
public class RxResultExercise extends ResultExercise {
    // This is needed for spring to hydrate the object
    private RxResultExercise() {
        this(null, null, null, null);
    }

    public RxResultExercise (String id, String proposedExerciseId, RoundsCounter roundsCounter, String comments) {
        super(id, proposedExerciseId, roundsCounter, comments);
    }
}
