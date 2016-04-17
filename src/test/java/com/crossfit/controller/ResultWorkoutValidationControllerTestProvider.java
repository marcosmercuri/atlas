package com.crossfit.controller;

import static com.crossfit.util.Utils.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

class ResultWorkoutValidationControllerTestProvider {
    private static final String TEMPLATE_NAME = "new_result_workout_request.json.tmpl";

    private String template;

    private ResultWorkoutValidationControllerTestProvider() {
        template = loadResource(TEMPLATE_NAME);
    }

    public static List<String> data() {
        ResultWorkoutValidationControllerTestProvider provider = new ResultWorkoutValidationControllerTestProvider();

        return Arrays.asList(
              createWithEmptyUserId(provider),
              createWithEmptyProposedWorkoutId(provider),
              createWithEmptyRx(provider),
              createWithEmptyFinished(provider),
              createWithEmptyFinishTimeInSeconds(provider),
              createWithZeroValueInFinishTimeInSeconds(provider),
              createWithEmptyDate(),
              createWithEmptyExercises(provider),
              createWithNullExercises(provider)
        );
    }

    private static String createWithEmptyExercises (ResultWorkoutValidationControllerTestProvider provider) {
        Map<String, String> keyAndValues = new HashMap<String, String>() {{
                put("resultExercises", "[]");
            }};
        return provider.loadRequest(keyAndValues, loadResource("new_result_workout_request_with_templated_exercises.json.tmpl"));
    }

    private static String createWithNullExercises (ResultWorkoutValidationControllerTestProvider provider) {
        Map<String, String> keyAndValues = new HashMap<String, String>() {{
                put("resultExercises", "null");
            }};
        return provider.loadRequest(keyAndValues, loadResource("new_result_workout_request_with_templated_exercises.json.tmpl"));
    }

    private static String createWithEmptyDate () {
        return loadResource("new_result_workout_request_without_date.json");
    }

    private static String createWithZeroValueInFinishTimeInSeconds (ResultWorkoutValidationControllerTestProvider provider) {
        return provider.getRequest("userId", "wod1", "true", "false", "0", "non", "2015-12-03");
    }

    private static String createWithEmptyFinishTimeInSeconds (ResultWorkoutValidationControllerTestProvider provider) {
        return provider.getRequest("userId", "wod1", "true", "false", "", "non", "2015-12-03");
    }

    private static String createWithEmptyFinished (ResultWorkoutValidationControllerTestProvider provider) {
        return provider.getRequest("userId", "wod1", "true", "null", "100", "non", "2015-12-03");
    }

    private static String createWithEmptyRx (ResultWorkoutValidationControllerTestProvider provider) {
        return provider.getRequest("userId", "wod1", "null", "true", "100", "non", "2015-12-03");
    }

    private static String createWithEmptyProposedWorkoutId (ResultWorkoutValidationControllerTestProvider provider) {
        return provider.getRequest("userId", "", "true", "true", "100", "non", "2015-12-03");
    }

    private static String createWithEmptyUserId (ResultWorkoutValidationControllerTestProvider provider) {
        return provider.getRequest("", "wod1", "true", "true", "100", "non", "2015-12-03");
    }

    private String getRequest (String userId, String proposedWorkoutId, String rx, String finished, String finishTimeInSeconds, String comments, String date) {
        return loadRequest(
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

    private String loadRequest(Map<String, String> keyAndValues) {
        return new StrSubstitutor(keyAndValues, "$(", ")").replace(template);
    }

    private String loadRequest(Map<String, String> keyAndValues, String jsonTemplate) {
        return new StrSubstitutor(keyAndValues, "$(", ")").replace(jsonTemplate);
    }
}
