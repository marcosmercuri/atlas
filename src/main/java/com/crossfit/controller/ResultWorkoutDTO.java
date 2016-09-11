package com.crossfit.controller;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

/**
 * POJO for a done workout.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultWorkoutDTO {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private String id;

    @NotBlank (message="error.resultWorkout.proposedWorkoutId.notBlank")
    private String proposedWorkoutId;

    @NotNull (message = "error.resultWorkout.rx.notNull")
    private Boolean rx;

    @NotNull (message = "error.resultWorkout.finished.notNull")
    private Boolean finished;

    @NotNull (message = "error.resultWorkout.finishTimeInSeconds.notNull")
    @Min(value = 1, message = "error.resultWorkout.finishTimeInSeconds.belowMinimumOne")
    private Integer finishTimeInSeconds;

    @Valid
    private List<ResultExerciseDTO> resultExercises;
    private String comments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @NotNull (message = "error.resultWorkout.date.notNull")
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

    public LocalDate getDate () {
        return date;
    }

    public void setDate (LocalDate date) {
        this.date = date;
    }

}
