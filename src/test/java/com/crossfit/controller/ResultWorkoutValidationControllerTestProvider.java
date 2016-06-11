package com.crossfit.controller;

import static com.crossfit.util.Utils.*;

import java.util.Arrays;
import java.util.List;

import com.crossfit.util.TestHelper;

class ResultWorkoutValidationControllerTestProvider {
    static List<String> data() {
        return Arrays.asList(
              createWorkoutWithEmptyUserId(),
              createWorkoutWithEmptyProposedWorkoutId(),
              createWorkoutWithEmptyRx(),
              createWorkoutWithEmptyFinished(),
              createWorkoutWithEmptyFinishTimeInSeconds(),
              createWorkoutWithZeroValueInFinishTimeInSeconds(),
              createWorkoutWithEmptyDate(),

              createExerciseWithEmptyProposedExerciseId(),
              createExerciseWithNullRx(),
              createExerciseWithEmptyCompletedRounds(),
              createExerciseWithNegativeCompletedRounds(),
              createExerciseWithNegativeRepetitionsOnUnfinishedRound(),
              createExerciseWithNegativeWeightInKilograms()
        );
    }

    private static String createExerciseWithNegativeWeightInKilograms() {
        return TestHelper.createExerciseWith("id", "false", "21", "10", "-20");
    }

    private static String createExerciseWithNegativeRepetitionsOnUnfinishedRound() {
        return TestHelper.createExerciseWith("id", "false", "21", "-1", "20");
    }

    private static String createExerciseWithNegativeCompletedRounds() {
        return TestHelper.createExerciseWith("id", "false", "-1", "9", "20");
    }

    private static String createExerciseWithEmptyCompletedRounds() {
        return TestHelper.createExerciseWith("id", "false", "", "9", "20");
    }

    private static String createExerciseWithNullRx() {
        return TestHelper.createExerciseWith("id", "null", "2", "9", "20");
    }

    private static String createExerciseWithEmptyProposedExerciseId() {
        return TestHelper.createExerciseWith("", "true", "2", "9", "20");
    }

    private static String createWorkoutWithEmptyDate () {
        return loadResource("new_result_workout_request_without_date.json");
    }

    private static String createWorkoutWithZeroValueInFinishTimeInSeconds() {
        return TestHelper.createWorkoutWith("userId", "wod1", "true", "false", "0", "non", "2015-12-03");
    }

    private static String createWorkoutWithEmptyFinishTimeInSeconds() {
        return TestHelper.createWorkoutWith("userId", "wod1", "true", "false", "", "non", "2015-12-03");
    }

    private static String createWorkoutWithEmptyFinished() {
        return TestHelper.createWorkoutWith("userId", "wod1", "true", "null", "100", "non", "2015-12-03");
    }

    private static String createWorkoutWithEmptyRx() {
        return TestHelper.createWorkoutWith("userId", "wod1", "null", "true", "100", "non", "2015-12-03");
    }

    private static String createWorkoutWithEmptyProposedWorkoutId() {
        return TestHelper.createWorkoutWith("userId", "", "true", "true", "100", "non", "2015-12-03");
    }

    private static String createWorkoutWithEmptyUserId() {
        return TestHelper.createWorkoutWith("", "wod1", "true", "true", "100", "non", "2015-12-03");
    }

}
