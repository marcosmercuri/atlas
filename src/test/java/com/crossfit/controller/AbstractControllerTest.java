package com.crossfit.controller;

import static com.crossfit.util.TestHelper.createObjectMapper;
import static java.nio.charset.Charset.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.io.IOException;

import com.crossfit.AtlasApplication;
import com.crossfit.util.TestHelper;
import com.crossfit.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

/**
 * Based class with annotations to load the context.
 * Mainly used to test controllers.
 * For real end-to-end test use AtlasApplicationTest
 */
@SpringApplicationConfiguration (classes = AtlasApplication.class)
@WebAppConfiguration
public abstract class AbstractControllerTest {
    protected MockMvc mockMvc;
    protected MediaType jsonContentType;
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp () {
        jsonContentType = new MediaType(APPLICATION_JSON.getType(),APPLICATION_JSON.getSubtype(),forName("utf8"));
        objectMapper = createObjectMapper();
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    protected ResultActions createForTimeProposedWorkout () throws Exception {
        return mockMvc.perform(
              post("/proposedWorkouts")
                    .content(createValidForTimeRequest())
                    .contentType(jsonContentType)
        );
    }

    protected String createValidForTimeRequest () {
        return Utils.loadResource("new_valid_proposed_for_time_request.json");
    }

    protected <T> T convertResponseToDtoClass(ResultActions result, Class<T> resultDtoClass) throws IOException {
        String jsonResponse = result.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(jsonResponse, resultDtoClass);
    }

    protected String getResponseIdFromProposedDto(ResultActions result) throws IOException {
        ProposedWorkoutDTO proposedWorkoutDTO = convertResponseToDtoClass(result, ProposedWorkoutDTO.class);
        return proposedWorkoutDTO.getId();
    }

    protected String getResponseIdFromResultDto(ResultActions result) throws IOException {
        ResultWorkoutDTO resultWorkoutDTO = convertResponseToDtoClass(result, ResultWorkoutDTO.class);
        return resultWorkoutDTO.getId();
    }

    protected  <T> String convertToJson (T modifiedProposedWorkout) {
        try {
            return objectMapper.writeValueAsString(modifiedProposedWorkout);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
