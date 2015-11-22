package com.crossfit.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.nio.charset.Charset;

import com.crossfit.AtlasApplication;
import com.crossfit.util.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration (classes = AtlasApplication.class)
@WebAppConfiguration
public class AtlasControllerTest {
    private MockMvc mockMvc;
    private MediaType contentType;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
              MediaType.APPLICATION_JSON.getSubtype(),
              Charset.forName("utf8"));
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_successful_new_proposed_workout() throws Exception {
        mockMvc.perform(
          post("/proposedWorkouts")
              .content(createValidForTimeRequest())
              .contentType(contentType)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(not(""))));
    }

    private String createValidForTimeRequest () {
        return Utils.loadResource("new_valid_proposed_for_time_request.json");
    }


    @Test
    public void test_bad_request_new_proposed_workout() {
        /*
        HttpEntity<String> httpRequest = createInvalidForTimeRequest();

        Map<String, Object> apiResponse = restTemplate.postForObject(getApplicationUrl() +"/proposedWorkouts", httpRequest, Map.class);

        assertNotNull(apiResponse);*/

    }

    //TODO Make a test that validates that the error are properly catch and nicely returned
}
