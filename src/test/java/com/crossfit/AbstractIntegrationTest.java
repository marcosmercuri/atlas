package com.crossfit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import com.crossfit.util.Utils;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

/**
 * This class loads the entire application, so it can be used for real end-to-end test.
 * Eg, you get the real response that a client would get if there's
 * an error (what is done in DefaultErrorResponseAttributes).
 */
@SpringApplicationConfiguration (classes = AtlasApplication.class)
@WebIntegrationTest ("server.port:0")
// Profile activated to disable spring security features.
@ActiveProfiles("integration-test")
public class AbstractIntegrationTest {
    //In this way I can get the port dynamically without hardcoding it
    @Value ("${local.server.port}")
    int applicationPort;
    protected TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new TestRestTemplate();
    }

    protected Map postRequest (HttpEntity<String> httpRequest, String endpointUrl) {
        return restTemplate.postForObject(getApplicationUrl() +endpointUrl, httpRequest, Map.class);
    }

    private String getApplicationUrl () {
        return "http://localhost:"+ applicationPort;
    }

    protected HttpEntity<String> createRequestWithBody (String requestBody, MediaType mediaType) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(mediaType);

        //Creating http entity object with request body and headers
        return new HttpEntity<>(requestBody, requestHeaders);
    }

    protected HttpEntity<String> createJsonRequestWithBody (String requestBody) {
        return createRequestWithBody(requestBody, MediaType.APPLICATION_JSON);
    }

    protected HttpEntity<String> createRequestFromFile (String fileName) {
        String requestBody = Utils.loadResource(fileName);
        return createJsonRequestWithBody(requestBody);
    }

    protected void verifyBadRequestStatus (Map apiResponse) {
        assertThat(apiResponse.get("status"), is(400));
    }

}
