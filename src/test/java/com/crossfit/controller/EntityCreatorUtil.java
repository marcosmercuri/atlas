package com.crossfit.controller;

import static java.util.Arrays.*;

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
        TimedExercise exercise = new TimedExercise(60, "toes to bar", "id toes to bar");
        return exercise;
    }
}
