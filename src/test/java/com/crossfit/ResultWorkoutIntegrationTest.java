package com.crossfit;

import static com.crossfit.util.TestHelper.createRequestResultWorkoutWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ResultWorkoutIntegrationTest extends AbstractIntegrationTest {
    private Map postRequest (HttpEntity<String> httpRequest) {
        return postRequest(httpRequest, "/resultWorkouts");
    }

    @Test
    public void test_bad_request_new_proposed_workout() {
        String nonExistentProposedWorkoutId = "non-existent";
        HttpEntity<String> httpRequest = createNonExistentProposedWorkoutRequest(nonExistentProposedWorkoutId);

        Map apiResponse = postRequest(httpRequest);

        assertNotNull(apiResponse);
        assertThat(apiResponse.get("status"), is(400));
        assertThat(apiResponse.get("message"), is("Proposed workout not found for id "+nonExistentProposedWorkoutId));
    }

    private HttpEntity<String> createNonExistentProposedWorkoutRequest(String proposedWorkoutId) {
        return createJsonRequestWithBody(createRequestResultWorkoutWith(proposedWorkoutId, "true", "true", "100", "", "2015-03-03"));
    }
}
