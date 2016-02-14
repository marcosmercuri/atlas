package com.crossfit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Map;

import com.crossfit.util.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration (classes = AtlasApplication.class)
@WebIntegrationTest("server.port:0")
public class AtlasApplicationTest {
    //In this way I can get the port dynamically without hardcoding it
    @Value ("${local.server.port}")
    int applicationPort;

    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new TestRestTemplate();
    }

    @Test
    public void test_successful_new_proposed_workout() {
        HttpEntity<String> httpRequest = createValidForTimeRequest();

        Map apiResponse = restTemplate.postForObject(getApplicationUrl() +"/proposedWorkouts", httpRequest, Map.class);

        assertNotNull(apiResponse);
        assertThat(apiResponse.get("id"), is(not("")));
    }

    private String getApplicationUrl () {
        return "http://localhost:"+ applicationPort;
    }

    private HttpEntity<String> createValidForTimeRequest () {
        String requestBody = Utils.loadResource("new_valid_proposed_for_time_request.json");
        return createJsonRequestWithBody(requestBody);
    }

    private HttpEntity<String> createRequestWithBody (String requestBody, MediaType mediaType) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(mediaType);

        //Creating http entity object with request body and headers
        return new HttpEntity<>(requestBody, requestHeaders);
    }

    @Test
    public void test_bad_request_new_proposed_workout() {
        HttpEntity<String> httpRequest = createInvalidMediaTypeRequest();

        Map apiResponse = restTemplate.postForObject(getApplicationUrl() +"/proposedWorkouts", httpRequest, Map.class);

        assertNotNull(apiResponse);
        assertThat(apiResponse.get("status"), is(415));
        assertThat(apiResponse.get("message"), is("Unsupported Media Type"));
        assertThat(apiResponse.get("developerMessage"), is("Exception: org.springframework.web.HttpMediaTypeNotSupportedException. Problem: Content type 'application/xml;charset=UTF-8' not supported"));
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

        Map apiResponse = restTemplate.postForObject(getApplicationUrl() +"/proposedWorkouts", httpRequest, Map.class);

        assertNotNull(apiResponse);

        assertThat(apiResponse.get("status"), is(400));
        assertThat(apiResponse.get("code"), is(40001));
        String errorMessage = (String)apiResponse.get("message");
        assertThat(errorMessage, containsString("the list of exercises cannot be empty"));
        assertThat(errorMessage, containsString("the type of the workout cannot be null"));
        assertThat(errorMessage, containsString("the list of exercises cannot be null"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument at index 0 in method: public com.crossfit.controller.ProposedWorkoutDTO com.crossfit.controller.AtlasController.createProposedWorkout(com.crossfit.controller.ProposedWorkoutDTO), with 3 error(s)"));
    }

    private HttpEntity<String> createRequestWithVariousMissingFields () {
        String requestBody = Utils.loadResource("proposed_workout_request_with_exercises_and_type_fields_missing.json");
        return createJsonRequestWithBody(requestBody);
    }

    private HttpEntity<String> createJsonRequestWithBody (String requestBody) {return createRequestWithBody(requestBody, MediaType.APPLICATION_JSON);}

    @Test
    public void test_invalid_workout_type_in_new_proposed_workout() {
        HttpEntity<String> httpRequest = createRequestWithInvalidWorkoutType();

        Map apiResponse = restTemplate.postForObject(getApplicationUrl() +"/proposedWorkouts", httpRequest, Map.class);

        assertNotNull(apiResponse);
        assertThat(apiResponse.get("status"), is(400));
        assertThat((String)apiResponse.get("message"), containsString("The workout type is not a valid value"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Check the allow workout types"));
    }

    private HttpEntity<String> createRequestWithInvalidWorkoutType () {
        String requestBody = Utils.loadResource("proposed_workout_request_with_invalid_workout_type.json");
        return createJsonRequestWithBody(requestBody);
    }

    @Test
    public void test_missing_durationInMinutes_field_in_amrap_proposed_workout_request() throws Exception {
        HttpEntity<String> httpRequest = createRequestWithMissingDurationInMinutesForAmrap();

        Map apiResponse = restTemplate.postForObject(getApplicationUrl() +"/proposedWorkouts", httpRequest, Map.class);

        assertNotNull(apiResponse);
        assertThat(apiResponse.get("status"), is(400));
        assertThat(apiResponse.get("message"), is("For AMRAP workout, the durationInMinutes cannot be null nor empty"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Field error in object 'proposedWorkoutDTO' on field 'durationInMinutes'"));
    }

    private HttpEntity<String> createRequestWithMissingDurationInMinutesForAmrap () {
        String requestBody = Utils.loadResource("proposed_amrap_workout_request_without_duration_in_minutes.json");
        return createJsonRequestWithBody(requestBody);
    }

    @Test
    public void test_invalid_value_for_durationInMinutes_field_in_amrap_proposed_workout_request() throws Exception {
        HttpEntity<String> httpRequest = createRequestWithInvalidDurationInMinutesForAmrap();

        Map apiResponse = restTemplate.postForObject(getApplicationUrl() +"/proposedWorkouts", httpRequest, Map.class);

        assertNotNull(apiResponse);
        assertThat(apiResponse.get("status"), is(400));
        assertThat(apiResponse.get("message"), is("For AMRAP workout, the durationInMinutes has to be above zero"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Field error in object 'proposedWorkoutDTO' on field 'durationInMinutes'"));
    }

    private HttpEntity<String> createRequestWithInvalidDurationInMinutesForAmrap () {
        String requestBody = Utils.loadResource("proposed_amrap_workout_request_with_invalid_duration_in_minutes.json");
        return createJsonRequestWithBody(requestBody);
    }

    @Test
    public void test_missing_numberOfRounds_and_maxAllowedMinutes_fields_in_forTime_proposed_workout_request() throws Exception {
        HttpEntity<String> httpRequest = createRequestWithMissingNumberOfRoundsAndMaxAllowedMinutesForForTime();

        Map apiResponse = restTemplate.postForObject(getApplicationUrl() +"/proposedWorkouts", httpRequest, Map.class);
        System.out.println("Response:" + apiResponse);
        assertNotNull(apiResponse);
        assertThat(apiResponse.get("status"), is(400));
        assertThat((String)apiResponse.get("message"), containsString("For FOR TIME workout, the numberOfRounds cannot be null nor empty"));
        assertThat((String)apiResponse.get("message"), containsString("For FOR TIME workout, the maxAllowedMinutes cannot be null nor empty"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Field error in object 'proposedWorkoutDTO' on field 'numberOfRounds'"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Field error in object 'proposedWorkoutDTO' on field 'maxAllowedMinutes'"));
    }

    private HttpEntity<String> createRequestWithMissingNumberOfRoundsAndMaxAllowedMinutesForForTime () {
        String requestBody = Utils.loadResource("proposed_for_time_workout_request_with_missing_number_of_rounds_and_max_allowed_minutes.json");
        return createJsonRequestWithBody(requestBody);
    }

    @Test
    public void test_invalid_value_for_numberOfRounds_and_maxAllowedMinutes_fields_in_forTime_proposed_workout_request() throws Exception {
        HttpEntity<String> httpRequest = createRequestWithInvalidMaxAllowedMinutesAndInvalidNumberOfRoundsForForTime();

        Map apiResponse = restTemplate.postForObject(getApplicationUrl() +"/proposedWorkouts", httpRequest, Map.class);

        assertNotNull(apiResponse);
        assertThat(apiResponse.get("status"), is(400));
        assertThat((String)apiResponse.get("message"), containsString("For FOR TIME workout, the numberOfRounds has to be above zero"));
        assertThat((String)apiResponse.get("message"), containsString("For FOR TIME workout, the maxAllowedMinutes has to be above zero"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Field error in object 'proposedWorkoutDTO' on field 'numberOfRounds'"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("Field error in object 'proposedWorkoutDTO' on field 'maxAllowedMinutes'"));
    }

    private HttpEntity<String> createRequestWithInvalidMaxAllowedMinutesAndInvalidNumberOfRoundsForForTime () {
        String requestBody = Utils.loadResource("proposed_for_time_workout_request_with_invalid_number_of_rounds_and_invalid_max_allowed_minutes.json");
        return createJsonRequestWithBody(requestBody);
    }
}
