package com.crossfit.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.io.IOException;
import java.nio.charset.Charset;

import com.crossfit.AtlasApplication;
import com.crossfit.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

@RunWith (SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration (classes = AtlasApplication.class)
@WebAppConfiguration
public class AtlasControllerTest {
    private MockMvc mockMvc;
    private MediaType jsonContentType;
    private MediaType xmlContentType;
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp () {
        jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
              MediaType.APPLICATION_JSON.getSubtype(),
              Charset.forName("utf8"));
        xmlContentType = new MediaType(MediaType.APPLICATION_XML.getType(),
              MediaType.APPLICATION_XML.getSubtype(),
              Charset.forName("utf8"));
        mockMvc = webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void test_successful_new_proposed_workout () throws Exception {
        mockMvc.perform(
              post("/proposedWorkouts")
                    .content(createValidForTimeRequest())
                    .contentType(jsonContentType)
        )
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id", is(not(""))))
              .andExpect(jsonPath("$.exercises[0].femaleRxInKilograms", is(8.5)))
              .andExpect(jsonPath("$.exercises[1].maleRxInKilograms", is(12.5)))
        ;
    }

    private String createValidForTimeRequest () {
        return Utils.loadResource("new_valid_proposed_for_time_request.json");
    }

    @Test
    public void test_successful_new_proposed_workout_with_type_in_lower_case () throws Exception {
        mockMvc.perform(
              post("/proposedWorkouts")
                    .content(createValidForTimeRequestWithTypeInLowerCase())
                    .contentType(jsonContentType)
        )
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id", is(not(""))));
    }

    private String createValidForTimeRequestWithTypeInLowerCase () {
        String validRequestWithTypeInUpperCase = createValidForTimeRequest();
        return validRequestWithTypeInUpperCase.replace("FOR_TIME", "for_time");
    }

    @Test
    public void test_invalid_content_type_in_request_new_proposed_workout () throws Exception {
        ResultActions result = mockMvc.perform(
              post("/proposedWorkouts")
                    .content(createValidForTimeRequest())
                    .contentType(xmlContentType)
        );
        result.andDo(print());
        result.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());
    }

    @Test
    public void test_missing_type_in_new_proposed_workout_request () throws Exception {
        ResultActions result = mockMvc.perform(
              post("/proposedWorkouts")
                    .content(createRequestWithMissingType())
                    .contentType(jsonContentType)
        );
        result.andExpect(status().isBadRequest());
    }

    private String createRequestWithMissingType () {
        return Utils.loadResource("proposed_workout_request_with_missing_type.json");
    }

    @Test
    public void test_invalid_workout_type_in_new_proposed_workout_request () throws Exception {
        ResultActions result = mockMvc.perform(
              post("/proposedWorkouts")
                    .content(createRequestWithInvalidWorkoutType())
                    .contentType(jsonContentType)
        );
        result.andDo(print());
        result.andExpect(status().isBadRequest());
    }

    private String createRequestWithInvalidWorkoutType () {
        return Utils.loadResource("proposed_workout_request_with_invalid_workout_type.json");
    }

    @Test
    public void test_missing_DurationInSeconds_field_in_amrap_proposed_workout_request () throws Exception {
        ResultActions result = mockMvc.perform(
              post("/proposedWorkouts")
                    .content(createRequestWithMissingDurationInSecondsForAmrap())
                    .contentType(jsonContentType)
        );
        result.andDo(print());
        result.andExpect(status().isBadRequest());
    }

    private String createRequestWithMissingDurationInSecondsForAmrap () {
        return Utils.loadResource("proposed_amrap_workout_request_without_duration_in_seconds.json");
    }

    @Test
    public void test_successful_get_proposed_workout () throws Exception {
        ResultActions result = mockMvc.perform(
              post("/proposedWorkouts")
                    .content(createValidForTimeRequest())
                    .contentType(jsonContentType)
        )
              .andExpect(status().isCreated());
        String proposedWorkoutId = getResponseId(result);
        mockMvc.perform(get("/proposedWorkouts/{id}", proposedWorkoutId))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id", is(proposedWorkoutId)));
    }

    @Test
    public void test_nonexistent_id_for_get_proposed_workout () throws Exception {
        mockMvc.perform(get("/proposedWorkouts/{id}", "nonexistent_id"))
              .andExpect(status().isNotFound());
    }

    @Test
    public void test_nonexistent_id_for_put_proposed_workout () throws Exception {
        mockMvc.perform(get("/proposedWorkouts/{id}", "nonexistent_id"))
              .andExpect(status().isNotFound());
    }

    @Test
    public void test_successful_update_proposed_workout () throws Exception {
        ResultActions result = createForTimeProposedWorkout();

        ProposedWorkoutDTO modifiedProposedWorkout = modifyMaxAllowed(result);

        mockMvc.perform(put("/proposedWorkouts/{id}", modifiedProposedWorkout.getId())
              .content(convertToJson(modifiedProposedWorkout))
              .contentType(jsonContentType)
        )
        .andExpect(status().isNoContent());

        verifyThatProposedWorkoutWasModified(modifiedProposedWorkout);
    }

    @Test
    public void test_validation_on_update_proposed_workout() throws Exception {
        ResultActions result = createForTimeProposedWorkout();

        ProposedWorkoutDTO modifiedProposedWorkout = deleteMaxAllowed(result);

        mockMvc.perform(put("/proposedWorkouts/{id}", modifiedProposedWorkout.getId())
              .content(convertToJson(modifiedProposedWorkout))
              .contentType(jsonContentType)
        ).andExpect(status().isBadRequest());
    }

    private void verifyThatProposedWorkoutWasModified (ProposedWorkoutDTO modifiedProposedWorkout) throws Exception {
        ResultActions result = mockMvc.perform(get("/proposedWorkouts/{id}", modifiedProposedWorkout.getId()));
        ProposedWorkoutDTO proposedWorkoutLatestVersion = convertResponseToProposedWorkoutDto(result);
        assertThat(proposedWorkoutLatestVersion.getMaxAllowedSeconds(), is(modifiedProposedWorkout.getMaxAllowedSeconds()));
    }

    @Test
    public void test_delete_proposed_workout() throws Exception {
        ResultActions result = createForTimeProposedWorkout();

        String proposedWorkoutId = getResponseId(result);
        mockMvc.perform(delete("/proposedWorkouts/{id}", proposedWorkoutId))
              .andExpect(status().isNoContent());

        verifyThatProposedWorkoutWasDeleted(proposedWorkoutId);
    }

    private void verifyThatProposedWorkoutWasDeleted (String proposedWorkoutId) throws Exception {
        mockMvc.perform(get("/proposedWorkouts/{id}", proposedWorkoutId))
              .andExpect(status().isNotFound());
    }

    @Test
    public void test_non_existent_id_on_delete_proposed_workout() throws Exception {
        mockMvc.perform(delete("/proposedWorkouts/{id}", "nonexistent_id"))
              .andExpect(status().isNotFound());
    }

    private ResultActions createForTimeProposedWorkout () throws Exception {
        return mockMvc.perform(
                  post("/proposedWorkouts")
                        .content(createValidForTimeRequest())
                        .contentType(jsonContentType)
            );
    }

    private String convertToJson (ProposedWorkoutDTO modifiedProposedWorkout) throws JsonProcessingException {
        return objectMapper.writeValueAsString(modifiedProposedWorkout);
    }

    private ProposedWorkoutDTO modifyMaxAllowed (ResultActions result) throws IOException {
        ProposedWorkoutDTO modifiedProposedWorkout = convertResponseToProposedWorkoutDto(result);
        modifiedProposedWorkout.setMaxAllowedSeconds(modifiedProposedWorkout.getMaxAllowedSeconds() + 10);
        return modifiedProposedWorkout;
    }

    private ProposedWorkoutDTO deleteMaxAllowed (ResultActions result) throws IOException {
        ProposedWorkoutDTO modifiedProposedWorkout = convertResponseToProposedWorkoutDto(result);
        modifiedProposedWorkout.setMaxAllowedSeconds(null);
        return modifiedProposedWorkout;
    }

    private String getResponseId (ResultActions result) throws IOException {
        //I'm sure there's a better way than this one, but couldn't find it.
        ProposedWorkoutDTO proposedWorkoutDTO = convertResponseToProposedWorkoutDto(result);
        return proposedWorkoutDTO.getId();
    }

    private ProposedWorkoutDTO convertResponseToProposedWorkoutDto (ResultActions result) throws IOException {
        String jsonResponse = result.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(jsonResponse, ProposedWorkoutDTO.class);
    }
}
