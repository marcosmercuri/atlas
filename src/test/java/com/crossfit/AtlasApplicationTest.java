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

        Map<String, Object> apiResponse = restTemplate.postForObject(getApplicationUrl() +"/proposedWorkouts", httpRequest, Map.class);

        assertNotNull(apiResponse);
        assertThat(apiResponse.get("id"), is(not("")));
    }

    private String getApplicationUrl () {
        return "http://localhost:"+ applicationPort;
    }

    private HttpEntity<String> createValidForTimeRequest () {
        String requestBody = Utils.loadResource("new_valid_proposed_for_time_request.json");
        return createRequestWithBody(requestBody, MediaType.APPLICATION_JSON);
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

        Map<String, Object> apiResponse = restTemplate.postForObject(getApplicationUrl() +"/proposedWorkouts", httpRequest, Map.class);

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

        Map<String, Object> apiResponse = restTemplate.postForObject(getApplicationUrl() +"/proposedWorkouts", httpRequest, Map.class);

        assertNotNull(apiResponse);
        System.out.println(apiResponse);
        assertThat(apiResponse.get("status"), is(400));
        String errorMessage = (String)apiResponse.get("message");
        assertThat(errorMessage, containsString("the list of exercises cannot be empty"));
        assertThat(errorMessage, containsString("the type of the workout cannot be null"));
        assertThat(errorMessage, containsString("the list of exercises cannot be null"));
        assertThat((String)apiResponse.get("developerMessage"), containsString("org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument at index 0 in method: public com.crossfit.controller.ProposedWorkoutDTO com.crossfit.controller.AtlasController.createProposedWorkout(com.crossfit.controller.ProposedWorkoutDTO), with 3 error(s)"));
    }

    private HttpEntity<String> createRequestWithVariousMissingFields () {
        String requestBody = Utils.loadResource("proposed_workout_request_with_exercises_and_type_fields_missing.json");
        return createRequestWithBody(requestBody, MediaType.APPLICATION_JSON);
    }
}
