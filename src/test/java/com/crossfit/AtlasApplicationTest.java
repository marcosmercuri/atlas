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
        return createRequestWithBody(requestBody);
    }

    private HttpEntity<String> createRequestWithBody (String requestBody) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        return new HttpEntity<>(requestBody, requestHeaders);
    }

    @Test
    public void test_bad_request_new_proposed_workout() {
        /*
        HttpEntity<String> httpRequest = createInvalidForTimeRequest();

        Map<String, Object> apiResponse = restTemplate.postForObject(getApplicationUrl() +"/proposedWorkouts", httpRequest, Map.class);

        assertNotNull(apiResponse);*/

    }

    private HttpEntity<String> createInvalidForTimeRequest () {
        String requestBody = Utils.loadResource("new_invalid_proposed_for_time_request.json");
        return createRequestWithBody(requestBody);
    }
    //TODO Make a test that validates that the error are properly catch and nicely returned
}
