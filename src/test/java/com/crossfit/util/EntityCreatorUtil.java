package com.crossfit.util;

import static java.util.Arrays.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import com.crossfit.model.*;

public class EntityCreatorUtil {
    private EntityCreatorUtil() { }

    public static Amrap givenValidAmrap() {
        return new Amrap(asList(createValidDistanceExercise()), 120, "id");
    }

    public static ForTimeWorkout givenValidForTimeWorkout () {
        return new ForTimeWorkout(asList(createValidRepetitionExercise()), 120, 4, "id for time");
    }

    public static DistanceExercise createValidDistanceExercise() {
        DistanceExercise exercise = new DistanceExercise(200D, "running", "id running");
        exercise.setDescription("You must run like hell!");
        return exercise;
    }

    public static RepetitionExercise createValidRepetitionExercise() {
        RepetitionExercise exercise = new RepetitionExercise(10, "burpees", "id burpees");
        exercise.setDescription("Burpees!!!");
        return exercise;
    }

    public static TimedExercise createValidTimedExercise () {
        return new TimedExercise(60, "toes to bar", "id toes to bar");
    }

    public static RxResultExercise createFinishedRxResultExercise () {
        return new RxResultExercise("random-id", "proposed-exercise-id", createRoundsCounter(), "comment");
    }

    private static RoundsCounter createRoundsCounter () {
        return new RoundsCounter(10, 0);
    }

    public static NonRxResultExercise createUnFinishedNonRxResultExercise () {
        return new NonRxResultExercise("id", "exercise-id", createRoundsCounter(), "commenting", 35.5f, null);
    }

    public static ResultWorkout createUnFinishedNonRxResultWorkout() {
        ResultWorkoutDetails details = new ResultWorkoutDetails(false, false, 360, "no comment", LocalDate.now());
        return new ResultWorkout("workout-id", "user_id",
              EntityCreatorUtil.givenValidForTimeWorkout(),
              Collections.singletonList(createUnFinishedNonRxResultExercise()),
              details);
    }
}
