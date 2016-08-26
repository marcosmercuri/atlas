package com.crossfit.model;

import java.time.LocalDate;
import javax.persistence.Embeddable;

@Embeddable
public class ResultWorkoutDetails {
    private final Boolean rx;
    private final Boolean finished;
    private final Integer finishTimeInSeconds;
    private final String comments;
    private final LocalDate date;

    // This is needed for spring to hydrate the object
    private ResultWorkoutDetails() {
        this(null, null, null, null, null);
    }

    public ResultWorkoutDetails (Boolean rx, Boolean finished, Integer finishTimeInSeconds, String comments, LocalDate date) {
        this.rx = rx;
        this.finished = finished;
        this.finishTimeInSeconds = finishTimeInSeconds;
        this.comments = comments;
        this.date = date;
    }

    public Boolean getRx () {
        return rx;
    }

    public Boolean getFinished () {
        return finished;
    }

    public Integer getFinishTimeInSeconds () {
        return finishTimeInSeconds;
    }

    public String getComments () {
        return comments;
    }

    public LocalDate getDate () {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        ResultWorkoutDetails that = (ResultWorkoutDetails)o;

        if(rx != null? !rx.equals(that.rx) : that.rx != null) return false;
        if(finished != null? !finished.equals(that.finished) : that.finished != null) return false;
        if(finishTimeInSeconds != null? !finishTimeInSeconds.equals(that.finishTimeInSeconds) : that.finishTimeInSeconds != null)
            return false;
        if(comments != null? !comments.equals(that.comments) : that.comments != null) return false;
        return date != null? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = rx != null? rx.hashCode() : 0;
        result = 31 * result + (finished != null? finished.hashCode() : 0);
        result = 31 * result + (finishTimeInSeconds != null? finishTimeInSeconds.hashCode() : 0);
        result = 31 * result + (comments != null? comments.hashCode() : 0);
        result = 31 * result + (date != null? date.hashCode() : 0);
        return result;
    }
}
