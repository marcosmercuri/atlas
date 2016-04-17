package com.crossfit.controller;

import static com.crossfit.util.Utils.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

class ResultWorkoutValidationControllerTestProvider {
    private static final String ROOT_WORKOUT_TEMPLATE_NAME = "new_result_workout_request.json.tmpl";

    private String workoutTemplate;

    private ResultWorkoutValidationControllerTestProvider() {
        workoutTemplate = loadResource(ROOT_WORKOUT_TEMPLATE_NAME);
    }

    public static List<String> data() {
        ResultWorkoutValidationControllerTestProvider provider = new ResultWorkoutValidationControllerTestProvider();

        return Arrays.asList(
              createWorkoutWithEmptyUserId(provider),
              createWorkoutWithEmptyProposedWorkoutId(provider),
              createWorkoutWithEmptyRx(provider),
              createWorkoutWithEmptyFinished(provider),
              createWorkoutWithEmptyFinishTimeInSeconds(provider),
              createWorkoutWithZeroValueInFinishTimeInSeconds(provider),
              createWorkoutWithEmptyDate(),
        );
    }

    private static String createWorkoutWithEmptyExercises () {
        return loadResource("new_result_workout_request_with_empty_exercise.json");
    }

    private static String createWorkoutWithNullExercises () {
        return loadResource("new_result_workout_request_with_null_exercise.json");
    }

    private static String createWorkoutWithEmptyDate () {
        return loadResource("new_result_workout_request_without_date.json");
    }

    private static String createWorkoutWithZeroValueInFinishTimeInSeconds (ResultWorkoutValidationControllerTestProvider provider) {
        return provider.createWorkoutWith("userId", "wod1", "true", "false", "0", "non", "2015-12-03");
    }

    private static String createWorkoutWithEmptyFinishTimeInSeconds (ResultWorkoutValidationControllerTestProvider provider) {
        return provider.createWorkoutWith("userId", "wod1", "true", "false", "", "non", "2015-12-03");
    }

    private static String createWorkoutWithEmptyFinished (ResultWorkoutValidationControllerTestProvider provider) {
        return provider.createWorkoutWith("userId", "wod1", "true", "null", "100", "non", "2015-12-03");
    }

    private static String createWorkoutWithEmptyRx (ResultWorkoutValidationControllerTestProvider provider) {
        return provider.createWorkoutWith("userId", "wod1", "null", "true", "100", "non", "2015-12-03");
    }

    private static String createWorkoutWithEmptyProposedWorkoutId (ResultWorkoutValidationControllerTestProvider provider) {
        return provider.createWorkoutWith("userId", "", "true", "true", "100", "non", "2015-12-03");
    }

    private static String createWorkoutWithEmptyUserId (ResultWorkoutValidationControllerTestProvider provider) {
        return provider.createWorkoutWith("", "wod1", "true", "true", "100", "non", "2015-12-03");
    }

    private String createWorkoutWith (String userId, String proposedWorkoutId, String rx, String finished, String finishTimeInSeconds, String comments, String date) {
        return generateWorkoutRequest(
              new HashMap<String, String>() {
                  {
                      put("userId", userId);
                      put("proposedWorkoutId", proposedWorkoutId);
                      put("rx", rx);
                      put("finished", finished);
                      put("finishTimeInSeconds", finishTimeInSeconds);
                      put("comments", comments);
                      put("date", date);
                  }
              });
    }

    private String generateWorkoutRequest (Map<String, String> keyAndValues) {
        return new StrSubstitutor(keyAndValues, "$(", ")").replace(workoutTemplate);
    }

}
