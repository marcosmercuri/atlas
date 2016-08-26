package com.crossfit.model;

import javax.persistence.Embeddable;

@Embeddable
public class RoundsCounter {
    private final Integer completedRounds;
    private final Integer repetitionsOnUnfinishedRound;

    // This is needed for spring to hydrate the object
    private RoundsCounter() {
        this(null, null);
    }

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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        RoundsCounter that = (RoundsCounter)o;

        if(completedRounds != null? !completedRounds.equals(that.completedRounds) : that.completedRounds != null)
            return false;
        return repetitionsOnUnfinishedRound != null? repetitionsOnUnfinishedRound.equals(that.repetitionsOnUnfinishedRound) : that.repetitionsOnUnfinishedRound == null;
    }

    @Override
    public int hashCode() {
        int result = completedRounds != null? completedRounds.hashCode() : 0;
        result = 31 * result + (repetitionsOnUnfinishedRound != null? repetitionsOnUnfinishedRound.hashCode() : 0);
        return result;
    }
}
