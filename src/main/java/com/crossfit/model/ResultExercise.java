package com.crossfit.model;

import org.springframework.data.annotation.Id;

public abstract class ResultExercise {
    @Id
    private final String id;
    private final String proposedExerciseId;
    private final RoundsCounter roundsCounter;
    private final String comments;

    protected ResultExercise (String id, String proposedExerciseId, RoundsCounter roundsCounter, String comments) {
        this.id = id;
        this.proposedExerciseId = proposedExerciseId;
        this.roundsCounter = roundsCounter;
        this.comments = comments;
    }

    public String getId () {
        return id;
    }

    public String getProposedExerciseId () {
        return proposedExerciseId;
    }

    public RoundsCounter getRoundsCounter () {
        return roundsCounter;
    }

    public String getComments () {
        return comments;
    }
}
