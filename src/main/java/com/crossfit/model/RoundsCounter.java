package com.crossfit.model;

public class RoundsCounter {
    private final Integer completedRounds;
    private final Integer repetitionsOnUnfinishedRound;

    public RoundsCounter (Integer completedRounds, Integer repetitionsOnUnfinishedRound) {
        this.completedRounds = completedRounds;
        this.repetitionsOnUnfinishedRound = repetitionsOnUnfinishedRound;
    }

    public Integer getCompletedRounds () {
        return completedRounds;
    }

    public Integer getRepetitionsOnUnfinishedRound () {
        return repetitionsOnUnfinishedRound;
    }
}
