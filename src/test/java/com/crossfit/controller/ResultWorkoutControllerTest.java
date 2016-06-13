package com.crossfit.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.crossfit.util.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringJUnit4ClassRunner.class)
public class ResultWorkoutControllerTest extends AbstractControllerTest {

    @Test
    public void test_successful_new_result_workout() throws Exception {
        ResultActions result = createForTimeProposedWorkout();

        String proposedWorkoutId = getResponseId(result);

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

}
