package com.crossfit.controller;

import static com.crossfit.util.Utils.*;

import java.util.Arrays;
import java.util.List;

import com.crossfit.util.TestHelper;

class ResultWorkoutValidationControllerTestProvider {

    static List<ParametrizedTestData> data() {
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

    private static ParametrizedTestData createExerciseWithNegativeWeightInKilograms() {
        return new ParametrizedTestData(
              TestHelper.createRequestResultExerciseWith("id", "false", "21", "10", "-20"),
              "error.resultExercise.weightInKilograms.belowMinimumOne"
        );
    }

    private static ParametrizedTestData createExerciseWithNegativeRepetitionsOnUnfinishedRound() {
        return new ParametrizedTestData(TestHelper.createRequestResultExerciseWith("id", "false", "21", "-1", "20"),
              "error.resultExercise.repetitionsOnUnfinishedRound.belowMinimumOne"
        );
    }

    private static ParametrizedTestData createExerciseWithNegativeCompletedRounds() {
        return new ParametrizedTestData(TestHelper.createRequestResultExerciseWith("id", "false", "-1", "9", "20"),
              "error.resultExercise.completedRounds.belowMinimumOne"
        );
    }

    private static ParametrizedTestData createExerciseWithEmptyCompletedRounds() {
        return new ParametrizedTestData(TestHelper.createRequestResultExerciseWith("id", "false", "", "9", "20"),
              "error.resultExercise.completedRounds.notNull"
        );
    }

    private static ParametrizedTestData createExerciseWithNullRx() {
        return new ParametrizedTestData(TestHelper.createRequestResultExerciseWith("id", "null", "2", "9", "20"),
              "error.resultExercise.rx.notNull"
        );
    }

    private static ParametrizedTestData createExerciseWithEmptyProposedExerciseId() {
        return new ParametrizedTestData(TestHelper.createRequestResultExerciseWith("", "true", "2", "9", "20"),
              "error.resultExercise.proposedExerciseId.notBlank"
        );
    }

    private static ParametrizedTestData createWorkoutWithEmptyDate () {
        return new ParametrizedTestData(loadResource("new_result_workout_request_without_date.json"),
              "error.resultWorkout.date.notNull"
        );
    }

    private static ParametrizedTestData createWorkoutWithZeroValueInFinishTimeInSeconds() {
        return new ParametrizedTestData(TestHelper.createRequestResultWorkoutWith("userId", "wod1", "true", "false", "0", "non", "2015-12-03"),
              "error.resultWorkout.finishTimeInSeconds.belowMinimumOne"
        );
    }

    private static ParametrizedTestData createWorkoutWithEmptyFinishTimeInSeconds() {
        return new ParametrizedTestData(TestHelper.createRequestResultWorkoutWith("userId", "wod1", "true", "false", "", "non", "2015-12-03"),
              "error.resultWorkout.finishTimeInSeconds.notNull"
        );
    }

    private static ParametrizedTestData createWorkoutWithEmptyFinished() {
        return new ParametrizedTestData(TestHelper.createRequestResultWorkoutWith("userId", "wod1", "true", "null", "100", "non", "2015-12-03"),
              "error.resultWorkout.finished.notNull"
        );
    }

    private static ParametrizedTestData createWorkoutWithEmptyRx() {
        return new ParametrizedTestData(TestHelper.createRequestResultWorkoutWith("userId", "wod1", "null", "true", "100", "non", "2015-12-03"),
              "error.resultWorkout.rx.notNull"
        );
    }

    private static ParametrizedTestData createWorkoutWithEmptyProposedWorkoutId() {
        return new ParametrizedTestData(TestHelper.createRequestResultWorkoutWith("userId", "", "true", "true", "100", "non", "2015-12-03"),
              "error.resultWorkout.proposedWorkoutId.notBlank"
        );
    }

    private static ParametrizedTestData createWorkoutWithEmptyUserId() {
        return new ParametrizedTestData(TestHelper.createRequestResultWorkoutWith("", "wod1", "true", "true", "100", "non", "2015-12-03"),
              "error.resultWorkout.userId.notBlank"
        );
    }

}
