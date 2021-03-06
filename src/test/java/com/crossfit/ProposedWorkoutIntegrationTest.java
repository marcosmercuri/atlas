package com.crossfit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Map;

import com.crossfit.util.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProposedWorkoutIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void test_successful_new_proposed_workout() {
        HttpEntity<String> httpRequest = createValidForTimeRequest();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);
        assertNotNull(apiResponse.get("id"));
        assertThat((String)apiResponse.get("id"), not(isEmptyOrNullString()));
    }

    private Map postRequest (HttpEntity<String> httpRequest) {
        return postRequest(httpRequest, "/proposedWorkouts");
    }

    private HttpEntity<String> createValidForTimeRequest () {
        return createRequestFromFile("new_valid_proposed_for_time_request.json");
    }

    @Test
    public void test_bad_request_new_proposed_workout() {
        HttpEntity<String> httpRequest = createInvalidMediaTypeRequest();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);
        assertThat(apiResponse.get("status"), is(415));
        assertThat(apiResponse.get("message"), is("Unsupported Media Type"));
        assertThat(apiResponse.get("developerMessage"), is("Exception: org.springframework.web.HttpMediaTypeNotSupportedException. Problem: Content type 'application/xml' not supported"));
        String timestamp = (String) apiResponse.get("timestamp");
        assertTrue(timestamp.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*"));
    }

    private HttpEntity<String> createInvalidMediaTypeRequest () {
        String requestBody = Utils.loadResource("new_valid_proposed_for_time_request.json");
        return createRequestWithBody(requestBody, MediaType.APPLICATION_XML);
    }

    /**
     * Test that all the missing fields are in the response message.
     */
    @Test
    public void test_missing_required_fields_new_proposed_workout() {
        HttpEntity<String> httpRequest = createRequestWithVariousMissingFields();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);

        verifyBadRequestStatus(apiResponse);
        assertThat(apiResponse.get("code"), is(40001));
        String errorMessage = (String)apiResponse.get("message");
        assertThat(errorMessage, containsString("The list of exercises cannot be empty"));
        assertThat(errorMessage, containsString("The type of the workout cannot be null"));
        assertThat(errorMessage, containsString("The list of exercises cannot be null"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument at index 0 in method: public com.crossfit.controller.ProposedWorkoutDTO com.crossfit.controller.ProposedWorkoutController.createProposedWorkout(com.crossfit.controller.ProposedWorkoutDTO), with 3 error(s)"));
    }

    private HttpEntity<String> createRequestWithVariousMissingFields () {
        return createRequestFromFile("proposed_workout_request_with_exercises_and_type_fields_missing.json");
    }

    @Test
    public void test_invalid_workout_type_in_new_proposed_workout() {
        HttpEntity<String> httpRequest = createRequestWithInvalidWorkoutType();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);
        verifyBadRequestStatus(apiResponse);
        assertThat((String)apiResponse.get("message"), containsString("The workout type is not a valid value"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Check the allow workout types"));
    }

    private HttpEntity<String> createRequestWithInvalidWorkoutType () {
        return createRequestFromFile("proposed_workout_request_with_invalid_workout_type.json");
    }

    @Test
    public void test_invalid_exercise_type_in_new_proposed_workout() {
        HttpEntity<String> httpRequest = createRequestWithInvalidExerciseType();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);
        verifyBadRequestStatus(apiResponse);
        assertThat((String)apiResponse.get("message"), containsString("The exercise type is not a valid value"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Check the allow exercise types"));
    }

    private HttpEntity<String> createRequestWithInvalidExerciseType () {
        return createRequestFromFile("proposed_workout_request_with_invalid_exercise_type.json");
    }

    @Test
    public void test_missing_durationInSeconds_field_in_amrap_proposed_workout_request() throws Exception {
        HttpEntity<String> httpRequest = createRequestWithMissingDurationInSecondsForAmrap();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);
        verifyBadRequestStatus(apiResponse);
        assertThat(apiResponse.get("message"), is("For AMRAP workout, the durationInSeconds cannot be null nor empty"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Field error in object 'proposedWorkoutDTO' on field 'durationInSeconds'"));
    }

    private HttpEntity<String> createRequestWithMissingDurationInSecondsForAmrap () {
        return createRequestFromFile("proposed_amrap_workout_request_without_duration_in_seconds.json");
    }

    @Test
    public void test_invalid_value_for_numeric_fields_in_amrap_proposed_workout_request () throws Exception {
        HttpEntity<String> httpRequest = createRequestWithInvalidNumericFieldsForAmrap();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);
        verifyBadRequestStatus(apiResponse);
        assertThat((String)apiResponse.get("message"), containsString("For AMRAP workout, the durationInSeconds has to be above zero"));
        assertThat((String)apiResponse.get("message"), containsString("For FOR TIME workout, the maxAllowedSeconds has to be above zero"));
        assertThat((String)apiResponse.get("message"), containsString("For FOR TIME workout, the numberOfRounds has to be above zero"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Field error in object 'proposedWorkoutDTO' on field 'durationInSeconds'"));
    }

    private HttpEntity<String> createRequestWithInvalidNumericFieldsForAmrap () {
        return createRequestFromFile("proposed_amrap_workout_request_with_invalid_numeric_fields.json");
    }

    @Test
    public void test_missing_numberOfRounds_and_maxAllowedSeconds_fields_in_forTime_proposed_workout_request() throws Exception {
        HttpEntity<String> httpRequest = createRequestWithMissingNumberOfRoundsAndMaxAllowedSecondsForForTime();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);
        verifyBadRequestStatus(apiResponse);
        assertThat((String)apiResponse.get("message"), containsString("For FOR TIME workout, the numberOfRounds cannot be null nor empty"));
        assertThat((String)apiResponse.get("message"), containsString("For FOR TIME workout, the maxAllowedSeconds cannot be null nor empty"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Field error in object 'proposedWorkoutDTO' on field 'numberOfRounds'"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Field error in object 'proposedWorkoutDTO' on field 'maxAllowedSeconds'"));
    }

    private HttpEntity<String> createRequestWithMissingNumberOfRoundsAndMaxAllowedSecondsForForTime () {
        return createRequestFromFile("proposed_for_time_workout_request_with_missing_number_of_rounds_and_max_allowed_seconds.json");
    }

    @Test
    public void test_invalid_value_for_numberOfRounds_and_maxAllowedSeconds_fields_in_forTime_proposed_workout_request() throws Exception {
        HttpEntity<String> httpRequest = createRequestWithInvalidMaxAllowedSecondsAndInvalidNumberOfRoundsForForTime();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);
        verifyBadRequestStatus(apiResponse);
        assertThat((String)apiResponse.get("message"), containsString("For FOR TIME workout, the numberOfRounds has to be above zero"));
        assertThat((String)apiResponse.get("message"), containsString("For FOR TIME workout, the maxAllowedSeconds has to be above zero"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Field error in object 'proposedWorkoutDTO' on field 'numberOfRounds'"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Field error in object 'proposedWorkoutDTO' on field 'maxAllowedSeconds'"));
    }

    private HttpEntity<String> createRequestWithInvalidMaxAllowedSecondsAndInvalidNumberOfRoundsForForTime () {
        return createRequestFromFile("proposed_for_time_workout_request_with_invalid_number_of_rounds_and_invalid_max_allowed_seconds.json");
    }

    /**
     * Test that all the missing fields are in the response message.
     */
    @Test
    public void test_missing_required_fields_on_proposed_exercise_on_new_proposed_workout() {
        HttpEntity<String> httpRequest = createRequestWithAllRequiredFieldsMissingInProposedExercise();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);

        verifyBadRequestStatus(apiResponse);
        assertThat(apiResponse.get("code"), is(40001));
        String errorMessage = (String)apiResponse.get("message");
        assertThat(errorMessage, containsString("The name of the proposed exercise cannot be null"));
        assertThat(errorMessage, containsString("The name of the proposed exercise cannot be blank"));
        assertThat(errorMessage, containsString("The type of the proposed exercise cannot be null"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument at index 0 in method: public com.crossfit.controller.ProposedWorkoutDTO com.crossfit.controller.ProposedWorkoutController.createProposedWorkout(com.crossfit.controller.ProposedWorkoutDTO), with 4 error(s)"));
    }

    private HttpEntity<String> createRequestWithAllRequiredFieldsMissingInProposedExercise () {
        return createRequestFromFile("proposed_workout_request_with_all_required_fields_missing_in_exercise.json");
    }

    @Test
    public void test_invalid_integer_fields_value_on_proposed_exercise_on_new_proposed_workout() {
        HttpEntity<String> httpRequest = createRequestWithAllIntegerFieldsInvalidInProposedExercise();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);

        verifyBadRequestStatus(apiResponse);
        assertThat(apiResponse.get("code"), is(40001));
        String errorMessage = (String)apiResponse.get("message");

        assertThat(errorMessage, containsString("The number of repetitions of the proposed exercise has to be above zero"));
        assertThat(errorMessage, containsString("The male Rx of the proposed exercise has to be above zero"));
        assertThat(errorMessage, containsString("The female Rx of the proposed exercise has to be above zero"));
        assertThat(errorMessage, containsString("The distance in meters of the proposed exercise has to be above zero"));
        assertThat(errorMessage, containsString("The duration of the proposed exercise has to be above zero"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument at index 0 in method: public com.crossfit.controller.ProposedWorkoutDTO com.crossfit.controller.ProposedWorkoutController.createProposedWorkout(com.crossfit.controller.ProposedWorkoutDTO), with 5 error(s)"));
    }

    private HttpEntity<String> createRequestWithAllIntegerFieldsInvalidInProposedExercise () {
        return createRequestFromFile("proposed_workout_request_with_all_integer_fields_invalid_in_exercise.json");
    }

    @Test
    public void test_one_rx_field_is_missing_on_proposed_exercise_on_new_proposed_workout() {
        HttpEntity<String> httpRequest = createRequestWithOnlyMaleRxInProposedExercise();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);

        verifyBadRequestStatus(apiResponse);
        assertThat(apiResponse.get("code"), is(40001));
        String errorMessage = (String)apiResponse.get("message");

        assertThat(errorMessage, containsString("Either both rx fields are set, or non can"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument at index 0 in method: public com.crossfit.controller.ProposedWorkoutDTO com.crossfit.controller.ProposedWorkoutController.createProposedWorkout(com.crossfit.controller.ProposedWorkoutDTO), with 1 error(s)"));
    }

    private HttpEntity<String> createRequestWithOnlyMaleRxInProposedExercise () {
        return createRequestFromFile("proposed_workout_request_with_only_male_rx_in_exercise.json");
    }

    @Test
    public void test_rx_and_distance_in_meters_fields_exceed_decimal_length_on_proposed_exercise_on_new_proposed_workout() {
        HttpEntity<String> httpRequest = createRequestWithAllDoubleFieldsExceedingDecimalLengthInProposedExercise();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);

        verifyBadRequestStatus(apiResponse);
        assertThat(apiResponse.get("code"), is(40001));
        String errorMessage = (String)apiResponse.get("message");

        assertThat(errorMessage, containsString("The male Rx can have two decimals at the most"));
        assertThat(errorMessage, containsString("The female Rx can have two decimals at the most"));
        assertThat(errorMessage, containsString("The distance in meters can have two decimals at the most"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument at index 0 in method: public com.crossfit.controller.ProposedWorkoutDTO com.crossfit.controller.ProposedWorkoutController.createProposedWorkout(com.crossfit.controller.ProposedWorkoutDTO), with 3 error(s)"));
    }

    private HttpEntity<String> createRequestWithAllDoubleFieldsExceedingDecimalLengthInProposedExercise () {
        return createRequestFromFile("proposed_workout_request_with_all_double_fields_exceeding_decimal_length_in_exercise.json");
    }

    @Test
    public void test_missing_fields_on_each_exercise_type_on_new_proposed_workout() {
        HttpEntity<String> httpRequest = createRequestWithAllRequiredFieldsMissingInProposedExerciseForEachType();

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);

        verifyBadRequestStatus(apiResponse);
        assertThat(apiResponse.get("code"), is(40001));
        String errorMessage = (String)apiResponse.get("message");

        assertThat(errorMessage, containsString("For REPETITION exercise, the numberOfRepetitions cannot be null nor empty"));
        assertThat(errorMessage, containsString("For TIMED exercise, the durationInSeconds cannot be null nor empty"));
        assertThat(errorMessage, containsString("For DISTANCE exercise, the distanceInMeters cannot be null nor empty"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument at index 0 in method: public com.crossfit.controller.ProposedWorkoutDTO com.crossfit.controller.ProposedWorkoutController.createProposedWorkout(com.crossfit.controller.ProposedWorkoutDTO), with 3 error(s)"));
    }

    private HttpEntity<String> createRequestWithAllRequiredFieldsMissingInProposedExerciseForEachType() {
        return createRequestFromFile("exercise/proposed_workout_request_with_missing_fields_on_each_exercise_type.json");
    }
}
