package com.crossfit.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotBlank;

/**
 * POJO for a done workout.
 */
public class ResultWorkoutDTO {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private String id;
    @NotBlank (message="error.resultWorkout.userId.notBlank")
    private String userId;

    @NotBlank (message="error.resultWorkout.proposedWorkoutId.notBlank")
    private String proposedWorkoutId;
    private Boolean rx;
    private Boolean finished;
    private Integer finishTimeInSeconds;
    private List<ResultExerciseDTO> resultExercises;
    private String comments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDate date;

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getProposedWorkoutId () {
        return proposedWorkoutId;
    }

    public void setProposedWorkoutId (String proposedWorkoutId) {
        this.proposedWorkoutId = proposedWorkoutId;
    }

    public Boolean getRx () {
        return rx;
    }

    public void setRx (Boolean rx) {
        this.rx = rx;
    }

    public Boolean getFinished () {
        return finished;
    }

    public void setFinished (Boolean finished) {
        this.finished = finished;
    }

    public Integer getFinishTimeInSeconds () {
        return finishTimeInSeconds;
    }

    public void setFinishTimeInSeconds (Integer finishTimeInSeconds) {
        this.finishTimeInSeconds = finishTimeInSeconds;
    }

    public List<ResultExerciseDTO> getResultExercises () {
        return resultExercises;
    }

    public void setResultExercises (List<ResultExerciseDTO> resultExercises) {
        this.resultExercises = resultExercises;
    }

    public String getComments () {
        return comments;
    }

    public void setComments (String comments) {
        this.comments = comments;
    }

    public String getUserId () {
        return userId;
    }

    public void setUserId (String userId) {
        this.userId = userId;
    }

    public LocalDate getDate () {
        return date;
    }

    public void setDate (String date) {
        this.date = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }
}
