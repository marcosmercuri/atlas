package com.crossfit.model;

import java.time.LocalDateTime;

/**
 * Represents the done workout of a crossfit WOD.
 */
public class WorkoutResult {
    private Workout proposedWorkout;
    private Workout achievedWorkout;
    private LocalDateTime workoutDateTime;

    public WorkoutResult (Workout proposedWorkout, Workout achievedWorkout, LocalDateTime workoutDateTime) {
        this.proposedWorkout = proposedWorkout;
        this.achievedWorkout = achievedWorkout;
        this.workoutDateTime = workoutDateTime;
    }

    public Workout getProposedWorkout () {
        return proposedWorkout;
    }

    public Workout getAchievedWorkout () {
        return achievedWorkout;
    }

    public LocalDateTime getWorkoutDateTime () {
        return workoutDateTime;
    }
}
