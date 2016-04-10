package com.crossfit.controller;

import java.util.List;

/**
 * POJO for a done workout.
 */
public class ResultWorkoutDTO {
    private String id;
    private String proposedWorkoutId;
    private Boolean rx;
    private Boolean finished;
    private WorkoutType workoutType;
    private Integer finishTimeInSeconds;
    private List<ResultExerciseDTO> resultExercises;
    private String comments;

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

    public WorkoutType getWorkoutType () {
        return workoutType;
    }

    public void setWorkoutType (WorkoutType workoutType) {
        this.workoutType = workoutType;
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
}
