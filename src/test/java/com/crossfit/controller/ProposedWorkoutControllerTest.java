package com.crossfit.controller;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import com.crossfit.util.TestHelper;
import com.crossfit.util.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith (SpringJUnit4ClassRunner.class)
public class ProposedWorkoutControllerTest extends AbstractControllerTest {

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
        MediaType xmlContentType = createXmlContentType();

        ResultActions result = mockMvc.perform(
              post("/proposedWorkouts")
                    .content("{\"invalid\": \"json\"}")
                    .contentType(xmlContentType)
        );
        result.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());
    }

    private MediaType createXmlContentType () {
        return new MediaType(APPLICATION_XML.getType(), APPLICATION_XML.getSubtype(), Charset.forName("utf8"));
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
        String proposedWorkoutId = getResponseIdFromProposedDto(result);
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
        ProposedWorkoutDTO proposedWorkoutLatestVersion = convertResponseToDtoClass(result, ProposedWorkoutDTO.class);
        assertThat(proposedWorkoutLatestVersion.getMaxAllowedSeconds(), is(modifiedProposedWorkout.getMaxAllowedSeconds()));
    }

    @Test
    public void test_fail_update_proposed_workout_with_different_ids () throws Exception {
        ResultActions result = createForTimeProposedWorkout();

        ProposedWorkoutDTO modifiedProposedWorkout = convertResponseToDtoClass(result, ProposedWorkoutDTO.class);
        String originalId = modifiedProposedWorkout.getId();
        modifiedProposedWorkout.setId("another-id");

        mockMvc.perform(put("/proposedWorkouts/{id}", originalId)
              .content(convertToJson(modifiedProposedWorkout))
              .contentType(jsonContentType)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void test_delete_proposed_workout() throws Exception {
        ResultActions result = createForTimeProposedWorkout();

        String proposedWorkoutId = getResponseIdFromProposedDto(result);
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

    private ProposedWorkoutDTO modifyMaxAllowed (ResultActions result) throws IOException {
        ProposedWorkoutDTO modifiedProposedWorkout = convertResponseToDtoClass(result, ProposedWorkoutDTO.class);
        modifiedProposedWorkout.setMaxAllowedSeconds(modifiedProposedWorkout.getMaxAllowedSeconds() + 10);
        return modifiedProposedWorkout;
    }

    private ProposedWorkoutDTO deleteMaxAllowed (ResultActions result) throws IOException {
        ProposedWorkoutDTO modifiedProposedWorkout = convertResponseToDtoClass(result, ProposedWorkoutDTO.class);
        modifiedProposedWorkout.setMaxAllowedSeconds(null);
        return modifiedProposedWorkout;
    }


    @Test
    public void test_cannot_delete_proposed_workout_being_used() throws Exception {
        ResultActions result = createForTimeProposedWorkout();
        String proposedWorkoutId = getResponseIdFromProposedDto(result);
        createResultWorkout(proposedWorkoutId);

        mockMvc.perform(delete("/proposedWorkouts/{id}", proposedWorkoutId))
              .andExpect(status().isBadRequest());
    }

    private void createResultWorkout(String proposedWorkoutId) throws Exception {
        mockMvc.perform(
              post("/resultWorkouts")
                    .content(createValidResultWorkout(proposedWorkoutId))
                    .contentType(jsonContentType)
        ).andExpect(status().isCreated());
    }

    private String createValidResultWorkout(String proposedWorkoutId) {
        return TestHelper.createRequestResultWorkoutWith(proposedWorkoutId, "true", "true", "100", "", "2015-03-03");
    }

    @Test
    public void test_get_all_proposed_workouts() throws Exception {
        ResultActions mvcResult = mockMvc.perform(get("/proposedWorkouts/")).andExpect(status().isOk());
        List<ProposedWorkoutDTO> savedWorkouts = convertResponseToListDtoClass(mvcResult, ProposedWorkoutDTO.class);

        createForTimeProposedWorkout();
        createForTimeProposedWorkout();

        mockMvc.perform(get("/proposedWorkouts"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(savedWorkouts.size()+2)));
    }
}
