package com.crossfit.controller;

import static com.crossfit.util.RequestErrorCodes.RESULT_WORKOUT_NOT_FOUND;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.crossfit.exceptions.CannotChangeFieldException;
import com.crossfit.exceptions.ProposedWorkoutNotFoundException;
import com.crossfit.exceptions.ResultWorkoutNotFoundException;
import com.crossfit.util.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringJUnit4ClassRunner.class)
public class ResultWorkoutControllerTest extends AbstractControllerTest {

    @Test
    public void test_successful_new_result_workout() throws Exception {
        ResultActions result = createForTimeProposedWorkout();
        String proposedWorkoutId = getResponseIdFromProposedDto(result);

        mockMvc.perform(
              post("/resultWorkouts")
                    .content(createValidResultWorkout(proposedWorkoutId))
                    .contentType(jsonContentType)
        )
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id", is(not(""))))
              .andExpect(jsonPath("$.id", is(notNullValue())));
    }

    private String createValidResultWorkout(String proposedWorkoutId) {
        return TestHelper.createRequestResultWorkoutWith("user-id", proposedWorkoutId, "true", "true", "100", "", "2015-03-03");
    }

    @Test
    public void test_error_on_non_existent_result_workout_dto() throws Exception {
        MvcResult result = mockMvc.perform(
              post("/resultWorkouts")
                    .content(createValidResultWorkout("non-existent-id"))
                    .contentType(jsonContentType)
        )
              .andDo(print())
              .andExpect(status().isBadRequest())
              .andReturn();

        verifyExceptionThrown(result, ProposedWorkoutNotFoundException.class);
    }

    private <T> T verifyExceptionThrown(MvcResult result, Class<T> expectedExceptionClass) {
        Exception receivedException = result.getResolvedException();
        assertTrue(receivedException.getClass().equals(expectedExceptionClass));
        return (T) receivedException;
    }

    @Test
    public void test_successful_get_result_workout () throws Exception {
        String resultWorkoutId = createResultWorkout().getId();

        mockMvc.perform(
              get("/resultWorkouts/{id}", resultWorkoutId)
                    .contentType(jsonContentType))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id", is(resultWorkoutId)));
    }

    private ResultWorkoutDTO createResultWorkout() throws Exception {
        String proposedWorkoutId = getResponseIdFromProposedDto(createForTimeProposedWorkout());

        ResultActions result = mockMvc.perform(
              post("/resultWorkouts")
                    .content(createValidResultWorkout(proposedWorkoutId))
                    .contentType(jsonContentType)
        )
              .andExpect(status().isCreated());
        return convertResponseToDtoClass(result, ResultWorkoutDTO.class);
    }

    @Test
    public void test_nonexistent_id_for_get_result_workout () throws Exception {
        MvcResult result = mockMvc.perform(
              get("/resultWorkouts/{id}", "nonexistent_id")
                    .contentType(jsonContentType))
              .andExpect(status().isNotFound())
              .andReturn();

        ResultWorkoutNotFoundException receivedException = verifyExceptionThrown(result, ResultWorkoutNotFoundException.class);
        assertThat(receivedException.getCode(), is(RESULT_WORKOUT_NOT_FOUND));
    }

    @Test
    public void test_successful_delete_result_workout () throws Exception {
        String resultWorkoutId = createResultWorkout().getId();

        mockMvc.perform(
              delete("/resultWorkouts/{id}", resultWorkoutId)
                    .contentType(jsonContentType))
              .andExpect(status().isNoContent());

        mockMvc.perform(
              get("/resultWorkouts/{id}", resultWorkoutId)
                    .contentType(jsonContentType))
              .andExpect(status().isNotFound());
    }

    @Test
    public void test_successful_update_result_workout () throws Exception {
        ResultWorkoutDTO resultWorkout = createResultWorkout();

        resultWorkout.setFinishTimeInSeconds(resultWorkout.getFinishTimeInSeconds()+100);

        mockMvc.perform(put("/resultWorkouts/{id}", resultWorkout.getId())
              .content(convertToJson(resultWorkout))
              .contentType(jsonContentType)
        )
              .andExpect(status().isNoContent());

        verifyThatResultWorkoutWasModified(resultWorkout);
    }

    private void verifyThatResultWorkoutWasModified(ResultWorkoutDTO modifiedResultWorkout) throws Exception {
        ResultActions result = mockMvc.perform(
              get("/resultWorkouts/{id}", modifiedResultWorkout.getId())
              .contentType(jsonContentType)
        );
        ResultWorkoutDTO resultWorkoutLatestVersion = convertResponseToDtoClass(result, ResultWorkoutDTO.class);
        assertThat(resultWorkoutLatestVersion.getFinishTimeInSeconds(), is(modifiedResultWorkout.getFinishTimeInSeconds()));
    }

    @Test
    public void test_fail_update_result_workout_with_different_ids () throws Exception {
        ResultWorkoutDTO resultWorkout = createResultWorkout();
        String originalResultWorkoutId = resultWorkout.getId();
        resultWorkout.setId("another-id");

        MvcResult mvcResult = mockMvc.perform(put("/resultWorkouts/{id}", originalResultWorkoutId)
              .content(convertToJson(resultWorkout))
              .contentType(jsonContentType)
        )
              .andExpect(status().isBadRequest())
              .andReturn();

        verifyExceptionThrown(mvcResult, CannotChangeFieldException.class);
    }

    @Test
    public void test_non_existent_id_on_update_result_workout() throws Exception {
        ResultWorkoutDTO resultWorkout = createResultWorkout();

        MvcResult mvcResult = mockMvc.perform(put("/resultWorkouts/{id}", "fake-id")
              .content(convertToJson(resultWorkout))
              .contentType(jsonContentType)
        )
              .andExpect(status().isNotFound())
              .andReturn();

        verifyExceptionThrown(mvcResult, ResultWorkoutNotFoundException.class);

    }

}
