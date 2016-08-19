package com.crossfit.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.crossfit.AtlasApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith (Parameterized.class)
@SpringApplicationConfiguration (classes = {AtlasApplication.class})
@WebAppConfiguration
@ActiveProfiles("test")
public class ResultWorkoutValidationControllerTest {
    @Autowired
    protected WebApplicationContext wac;
    private MockMvc mockMvc;
    private final String invalidRequest;
    private final String expectedErrorMessage;

    public ResultWorkoutValidationControllerTest(ParametrizedTestData parametrizedTestData) {
        this.invalidRequest = parametrizedTestData.getInvalidRequest();
        this.expectedErrorMessage = parametrizedTestData.getExpectedErrorMessage();
    }

    @Before
    public void setUp() throws Exception {
        //As the runWith can't have two parameters, the context loading is done with this
        new TestContextManager(getClass()).prepareTestInstance(this);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Parameterized.Parameters
    public static List<ParametrizedTestData> data() {
        return ResultWorkoutValidationControllerTestProvider.data();
    }

    @Test
    public void givenInvalidRequestValuesWhenPostIsSentThenAnErrorIsGiven() throws Exception {
        MvcResult result = mockMvc.perform(
              post("/resultWorkouts")
                    .contentType("application/json")
                    .content(invalidRequest)
        )
              .andExpect(status().isBadRequest())
              .andReturn();

        assertTrue(result.getResolvedException().getMessage().contains(expectedErrorMessage));
    }
}
