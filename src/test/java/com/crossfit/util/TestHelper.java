package com.crossfit.util;

import static com.crossfit.util.Utils.loadResource;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

public class TestHelper {
    private static final String ROOT_WORKOUT_TEMPLATE_NAME = "new_result_workout_request.json.tmpl";
    private static final String EXERCISE_TEMPLATE_NAME = "new_result_workout_request_with_template_exercises.json.tmpl";


    public static String createRequestResultWorkoutWith(String userId, String proposedWorkoutId, String rx, String finished, String finishTimeInSeconds, String comments, String date) {
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

    private static String generateWorkoutRequest (Map<String, String> keyAndValues) {
        return new StrSubstitutor(keyAndValues, "$(", ")").replace(loadResource(ROOT_WORKOUT_TEMPLATE_NAME));
    }

    public static String createRequestResultExerciseWith(String proposedExerciseId, String rx, String completedRounds, String repetitionsOnUnfinishedRound, String weightInKilograms) {
        return generateExerciseRequest(
              new HashMap<String, String>() {
                  {
                      put("proposedExerciseId", proposedExerciseId);
                      put("rx", rx);
                      put("completedRounds", completedRounds);
                      put("repetitionsOnUnfinishedRound", repetitionsOnUnfinishedRound);
                      put("weightInKilograms", weightInKilograms);
                  }
              });
    }

    private static String generateExerciseRequest (Map<String, String> keyAndValues) {
        return new StrSubstitutor(keyAndValues, "$(", ")").replace(loadResource(EXERCISE_TEMPLATE_NAME));
    }
}
