package com.crossfit.model;

import java.time.LocalDate;

public class ResultWorkoutDetails {
    private final Boolean rx;
    private final Boolean finished;
    private final Integer finishTimeInSeconds;
    private final String comments;
    private final LocalDate date;
    
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
}
