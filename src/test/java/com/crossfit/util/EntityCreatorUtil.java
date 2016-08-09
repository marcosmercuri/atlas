package com.crossfit.util;

import static java.util.Collections.*;

import java.time.LocalDate;

import com.crossfit.model.*;

public class EntityCreatorUtil {
    private EntityCreatorUtil() { }

    public static Amrap givenValidAmrap() {
        return new Amrap(singletonList(createValidDistanceExercise()), 120, "id");
    }

    public static ForTimeWorkout givenValidForTimeWorkout () {
        return new ForTimeWorkout(singletonList(createValidRepetitionExercise()), 120, 4, "id for time");
    }

    public static DistanceExercise createValidDistanceExercise() {
        return new DistanceExercise(200D, "running", "id running", 0.0, 0.0, "You must run like hell!");
    }

    public static RepetitionExercise createValidRepetitionExercise() {
        return new RepetitionExercise(10, "burpees", "id burpees", 0.0, 0.0, "Burpees!!!");
    }

    public static TimedExercise createValidTimedExercise () {
        return new TimedExercise(60, "toes to bar", "id toes to bar", 0.0, 0.0, "toes to bar, man");
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
              singletonList(createUnFinishedNonRxResultExercise()),
              details);
    }
}
